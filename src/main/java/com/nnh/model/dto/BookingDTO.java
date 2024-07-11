package com.nnh.model.dto;

import java.util.Date;

import com.nnh.model.entity.UserEntity;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
public class BookingDTO extends AbstractDTO<BookingDTO>{
        private Long id;
        @NotNull(message = "Ngày check-in không được để trống")
        @Future(message = "Ngày check-in phải là một ngày trong tương lai")
        private Date checkinDate;

        @NotNull(message = "Ngày check-out không được để trống")
        @Future(message = "Ngày check-out phải là một ngày trong tương lai")
        private Date checkoutDate;

        private Date bookingDate;

        @NotNull(message = "Số lượng trẻ em không được để trống")
        @Min(value = 0, message = "Số lượng trẻ em không thể âm")
        private Integer childAmount;

        @NotNull(message = "Số lượng người lớn không được để trống")
        @Min(value = 1, message = "Phải có ít nhất một người lớn")
        private Integer adultAmount;

        private UserEntity user;
        private Long departmentId;
        private String departmentName;
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
	
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Date getCheckinDate() {
		return checkinDate;
	}
	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}
	public Date getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Integer getChildAmount() {
		return childAmount;
	}
	public void setChildAmount(Integer childAmount) {
		this.childAmount = childAmount;
	}
	public Integer getAdultAmount() {
		return adultAmount;
	}
	public void setAdultAmount(Integer adultAmount) {
		this.adultAmount = adultAmount;
	}
	
	
}
