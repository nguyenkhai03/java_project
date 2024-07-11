package com.nnh.controller.view;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nnh.dal.reposiroty.RoleRepository;
import com.nnh.dal.service.IUserService;
import com.nnh.model.dto.UserDTO;
import com.nnh.model.entity.RoleEntity;
import com.nnh.model.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class Login {

    private static final Logger logger = LoggerFactory.getLogger(Login.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private RoleRepository roleRep;

    @GetMapping("/login")
    public String getView(Model model, HttpSession session) {
        if (session.getAttribute("USERMODEL") != null) {
            return "redirect:/home";
        }
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);

        return "web/login";
    }

    @PostMapping("/login")
    public String webLogin(@ModelAttribute("user") UserDTO userDTO, HttpSession session, Model model) {
        logger.info("Attempting login for user: {}", userDTO.getUsername());

        UserEntity userEntity = userService.findOne(userDTO);
        if (userEntity != null) {
            RoleEntity roleEntity = roleRep.findById(userEntity.getRoles().get(0).getId()).orElse(null);
            if (roleEntity != null) {
                logger.info("User role: {}", roleEntity.getCode());
                session.setAttribute("USERMODEL", userEntity);
                return "redirect:/home";
            } else {
                logger.error("Role not found for user: {}", userDTO.getUsername());
            }
        } else {
            logger.error("User not found or incorrect credentials for user: {}", userDTO.getUsername());
        }
        model.addAttribute("message", "Sai thông tin đăng nhập.");
        return "web/login";
    }

    @GetMapping("/logout")
    public String webLogout(HttpSession session) {
        session.removeAttribute("USERMODEL");
        session.invalidate();
        return "redirect:/login";
    }
}
