package com.nnh.controller.view;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nnh.dal.reposiroty.CityRepository;
import com.nnh.dal.service.IBookingService;
import com.nnh.dal.service.ICityService;
import com.nnh.dal.service.ICommentService;
import com.nnh.dal.service.IDepartmentService;
import com.nnh.dal.service.IUserService;
import com.nnh.model.convert.UserConvert;
import com.nnh.model.dto.BookingDTO;
import com.nnh.model.dto.CommentDTO;
import com.nnh.model.dto.DepartmentDTO;
import com.nnh.model.dto.UserDTO;
import com.nnh.model.entity.CityEntity;
import com.nnh.model.entity.UserEntity;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindingResult;

@Controller
public class Web {
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private ICityService cityService;

    @Autowired
    private CityRepository cityRep;

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(Web.class);

    @GetMapping("/home")
    public String getView(Model model) {
        model.addAttribute("department", new DepartmentDTO());
        model.addAttribute("departmentsVietnam", departmentService.findAllInVietNam());
        model.addAttribute("departmentsForeign", departmentService.findAllInForeign());
        return "web/home";
    }

    @PostMapping("/tim-kiem")
    public String getSearch(Model model, @ModelAttribute("department") DepartmentDTO departmentDTO) {
        model.addAttribute("booking", new BookingDTO());
        List<DepartmentDTO> departments = departmentService.findAllByCityName(departmentDTO.getCityName());
        model.addAttribute("departments", departments);

        return "web/searchPage";
    }

    @GetMapping("/can-ho")
    public String getRoomPage(Model model, @RequestParam("id") Long id) {
        model.addAttribute("booking", new BookingDTO());
        model.addAttribute("comment", new CommentDTO());
        model.addAttribute("comments", commentService.findAllByDepartment(id));
        model.addAttribute("commentCount", commentService.findAllByDepartment(id).size());
        DepartmentDTO departmentDTO = departmentService.findOneById(id);
        CityEntity cities = cityRep.findOneByName(departmentDTO.getCityName());
        model.addAttribute("department", departmentDTO);
        model.addAttribute("country", cityService.findCountryByCity(cities));
        model.addAttribute("categoriesCount", departmentService.getUniqueCategories());
        return "web/roomPage";
    }

    @PostMapping("/booking")
    public String createBooking(@Valid @ModelAttribute("booking") BookingDTO bookingDTO, BindingResult result, HttpSession session, @RequestParam("id") Long id, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("department", departmentService.findOneById(id));
            model.addAttribute("errorMessage", "Xin hãy kiểm tra lại thông tin đã nhập.");
            return "web/roomPage";  // Trở lại trang với thông tin lỗi
        }

        UserEntity user = (UserEntity) session.getAttribute("USERMODEL");
        if (user == null) {
            return "redirect:/login";
        }

        bookingDTO.setUser(user);
        bookingDTO.setDepartmentId(id);
        bookingService.save(bookingDTO);
        return "redirect:/home";
    }

    @PostMapping("/comment")
    private String createComment(@ModelAttribute("comment") CommentDTO commentDTO, HttpSession session, @RequestParam("id") Long id) {
        if (session.getAttribute("USERMODEL") != null) {
            commentDTO.setUsername(((UserEntity) session.getAttribute("USERMODEL")).getUsername());
            commentDTO.setDepartmentId(id);
            commentService.save(commentDTO);
            return "redirect:/can-ho?id=" + id;
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/deleteComment")
    private String deleteComment(@RequestParam("commentId") Long commentId, @RequestParam("id") Long id) {
        commentService.delete(commentId);
        return "redirect:/can-ho?id=" + id;
    }

    @GetMapping("/user")
    public String getUserPage(HttpSession session, Model model) {
        UserEntity userEntity = (UserEntity) session.getAttribute("USERMODEL");
        if (userEntity == null) {
            return "redirect:/login";
        }
        UserDTO user = UserConvert.toDto(userEntity);
        model.addAttribute("user", user);
        model.addAttribute("bookings", bookingService.findByUser(userService.findOne(user)));

        return "web/userPage";
    }

    @GetMapping("/deleteBooking")
    public String deleteBooking(@RequestParam(value = "id", required = true) Long id, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("USERMODEL");
        if (user == null) {
            return "redirect:/login";
        }

        List<BookingDTO> bookings = bookingService.findByUser(user);
        boolean isBookingBelongsToUser = bookings.stream().anyMatch(booking -> booking.getId().equals(id));

        if (isBookingBelongsToUser) {
            logger.info("Delete booking with id: " + id);
            try {
                bookingService.delete(id);
            } catch (EmptyResultDataAccessException e) {
                logger.error("No BookingEntity entity with id " + id + " exists!");
            }
        } else {
            logger.error("User does not have permission to delete booking with id " + id);
        }

        return "redirect:/user";
    }
    @GetMapping("/roomlist")
    public String test(Model model) {
        List<DepartmentDTO> departments = departmentService.findAllDepartments();
        model.addAttribute("findAllDepartments", departments);
        return "web/roomlist";
    }
    /*
    @GetMapping("/roomlist")
    public String filterRooms(@RequestParam(value = "city", required = false) String city,
                              @RequestParam(value = "type", required = false) String type,
                              @RequestParam(value = "price", required = false) Integer price,
                              Model model) {
        List<DepartmentDTO> departments = departmentService.findAllDepartments();

        // Lấy danh sách các thành phố và loại phòng
        List<String> cities = departmentService.findAllCities();
        List<String> roomTypes = departmentService.findAllRoomTypes();

        if (city != null && !city.isEmpty()) {
            departments = departments.stream().filter(d -> city.equals(d.getCityName())).collect(Collectors.toList());
        }

        if (type != null && !type.isEmpty()) {
            departments = departments.stream().filter(d -> type.equals(d.getRoomType())).collect(Collectors.toList());
        }

        if (price != null) {
            departments = departments.stream().filter(d -> d.getPrice() <= price).collect(Collectors.toList());
        }

        model.addAttribute("findAllDepartments", departments);
        model.addAttribute("cities", cities);
        model.addAttribute("roomTypes", roomTypes);

        return "web/roomlist";
    }

    */

}
