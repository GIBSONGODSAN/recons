// package com.example.demo.repository;

// import com.example.demo.model.Contact;

// import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// @Repository
// public class ContactRepository {

//     private final JpaRepository<Contact, Long> jpaRepository;

//     public ContactRepository(JpaRepository<Contact, Long> jpaRepository) {
//         this.jpaRepository = jpaRepository;
//     }

//     // Add a method to retrieve values from the database
//     public List<Contact> findAll() {
//         return jpaRepository.findAll();
//     }

//     // Add a method to retrieve a single value from the database
//     public Contact findById(Long id) {
//         return jpaRepository.findById(id).orElse(null);
//     }

//     // Add a method to save values to the database
//     public Contact save(Contact contact) {
//         return jpaRepository.save(contact);
//     }

//     // Add a method to delete values from the database
//     public void delete(Contact contact) {
//         jpaRepository.delete(contact);
//     }

//     // Add a method to update values in the database
//     public Contact update(Contact contact) {
//         return jpaRepository.save(contact);
//     }
// }
