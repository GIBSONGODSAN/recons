// package com.example.demo.controller;

// import com.example.demo.handler.ResponseHandler;
// import com.example.demo.model.Contact;
// import com.example.demo.repository.ContactRepository;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = "http://localhost:3000")
// @RestController
// @RequestMapping({"/contacts"})
// public class ContactController {

//     @Autowired
//     ContactRepository contactRepository;
        
//     @GetMapping("/all")
//     public ResponseEntity<List<Contact>> getAllContacts() {
//         List<Contact> contacts = contactRepository.findAll();
//         if (contacts.isEmpty()) {
//             return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         } else {
//             return new ResponseEntity<>(contacts, HttpStatus.OK);
//         }
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
//         Contact contact = contactRepository.findById(id);
//         if (contact == null) {
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         } else {
//             return new ResponseEntity<>(contact, HttpStatus.OK);
//         }
//     }
    
//     @PostMapping("/add")
//     public ResponseEntity<Contact> addContact(@RequestBody Contact contact) {
//         Contact newContact = contactRepository.save(contact);
//         return new ResponseEntity<>(newContact, HttpStatus.CREATED);
//     }

//     @PutMapping("/update")
//     public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
//         Contact updatedContact = contactRepository.save(contact);
//         return new ResponseEntity<>(updatedContact, HttpStatus.OK);
//     }

//     @DeleteMapping("/delete")
//     public ResponseEntity<Contact> deleteContact(@RequestParam Long id) {
//         Contact contact = contactRepository.findById(id);
//         if (contact == null) {
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         } else {
//             contactRepository.delete(contact);
//             return new ResponseEntity<>(contact, HttpStatus.OK);
//         }
//     }

//     @DeleteMapping("/delete/{id}")
//     public ResponseEntity<Contact> deleteContactById(@PathVariable Long id) {
//         Contact contact = contactRepository.findById(id);
//         if (contact == null) {
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         } else {
//             contactRepository.delete(contact);
//             return new ResponseEntity<>(contact, HttpStatus.OK);
//         }
//     }

//     @PutMapping("/update/{id}")
//     public ResponseEntity<Contact> updateContactById(@PathVariable Long id, @RequestBody Contact contact) {
//         Contact existingContact = contactRepository.findById(id);
//         if (existingContact == null) {
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//         } else {
//             existingContact.setName(contact.getName());
//             existingContact.setEmail(contact.getEmail());
//             existingContact.setPhone(contact.getPhone());
//             Contact updatedContact = contactRepository.save(existingContact);
//             return new ResponseEntity<>(updatedContact, HttpStatus.OK);
//         }
//     }

//     @GetMapping("/search")
//     public ResponseEntity<Object> searchContacts(@RequestParam String name) {
//         return ResponseHandler.generateResponse("working", HttpStatus.OK, name);

//     }
// }