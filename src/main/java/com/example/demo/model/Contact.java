// package com.example.demo.model;

// import org.springframework.data.annotation.Id;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @AllArgsConstructor
// @NoArgsConstructor
// @Data
// @Entity
// @Table(name = "contacts")
// public class Contact {

//   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
//   @Column(name = "id")
//   private Long id;

//     @Column(name = "name")
//   private String name;

//     @Column(name = "email")
//   private String email;

//     @Column(name = "phone")
//   private String phone;
// }