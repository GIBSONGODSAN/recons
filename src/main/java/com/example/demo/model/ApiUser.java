package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;

@Entity
@Table(name = "api_user")
public class ApiUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String FirstName;
    @Column(nullable = false)
    private String LastName;
    @Column(nullable = false)
    private String Email;
    @Column(nullable = false)
    private String Password;
    @Column(nullable = false)
    private String Address;
    @Column(nullable = false)
    private String City;
    @Column(nullable = false)
    private Integer ZipCode;
    @Column(nullable = false)
    private Integer PhoneNumber;
    @Column(nullable = false)
    private String CompanyName;
    @Column(nullable = false)
    private String PlanType;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private Integer noOfRequests;
    
    public Integer getNoOfRequests() {
        return noOfRequests;
    }
    public void setNoOfRequests(Integer noOfRequests) {
        this.noOfRequests = noOfRequests;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getCity() {
        return City;
    }
    public void setCity(String city) {
        City = city;
    }
    public Integer getZipCode() {
        return ZipCode;
    }
    public void setZipCode(Integer zipCode) {
        ZipCode = zipCode;
    }
    public Integer getPhoneNumber() {
        return PhoneNumber;
    }
    public void setPhoneNumber(Integer phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    public String getCompanyName() {
        return CompanyName;
    }
    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
    public String getPlanType() {
        return PlanType;
    }
    public void setPlanType(String planType) {
        PlanType = planType;
    }
    public ApiUser(Integer id, String firstName, String lastName, String email, String password, String address,
            String city, Integer zipCode, Integer phoneNumber, String companyName, String planType, String token, Integer noOfRequests) {
        this.id = id;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.Password = password;
        this.Address = address;
        this.City = city;
        this.ZipCode = zipCode;
        this.PhoneNumber = phoneNumber;
        this.CompanyName = companyName;
        this.PlanType = planType;
        this.token = token;
        this.noOfRequests = noOfRequests;
    }    

}
