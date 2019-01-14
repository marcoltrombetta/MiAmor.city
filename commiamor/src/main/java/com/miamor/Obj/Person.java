package com.miamor.Obj;

import java.sql.Date;

public class Person {
    private int Id ;
    private String FirstName ;
    private String LastName ;
    private String Email ;
    private boolean Gender ;
    private String Phone ;
    private String Mobile ;
    private String DateOfBirth ;
    private String ImgPath ;
    private Date MarriageDate ;
    
    //  public virtual List<PersonAddress> PersonAddress { get; set; }

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public boolean isGender() {
		return Gender;
	}
	public void setGender(boolean gender) {
		Gender = gender;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getMobile() {
		return Mobile;
	}
	public void setMobile(String mobile) {
		Mobile = mobile;
	}
	public String getDateOfBirth() {
		return DateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public String getImgPath() {
		return ImgPath;
	}
	public void setImgPath(String imgPath) {
		ImgPath = imgPath;
	}
	public Date getMarriageDate() {
		return MarriageDate;
	}
	public void setMarriageDate(Date marriageDate) {
		MarriageDate = marriageDate;
	}

}
