package com.sparky.controller;
import com.sparky.Domain.Restriction;
import com.sparky.Domain.User;
import com.sparky.Domain.UserLimit;
import com.sparky.repository.UserRepository;
import com.sparky.service.RestrictionService;
import com.sparky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private RestrictionService restrictionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/restrictions")
    public ResponseEntity<Restriction> createRestriction(@RequestBody Restriction r) {
        return ResponseEntity.ok(restrictionService.createRestriction(r));
    }

    @GetMapping("/restrictions")
    public ResponseEntity<List<Restriction>> listRestrictions(@RequestParam Long companyId) {
        return ResponseEntity.ok(restrictionService.getAllRestrictionsByCompany(companyId));
    }

    @PutMapping("/restrictions/{id}")
    public ResponseEntity<Restriction> updateRestriction(@PathVariable Long id, @RequestBody Restriction r) {
        return ResponseEntity.ok(restrictionService.updateRestriction(id, r));
    }

    @DeleteMapping("/restrictions/{id}")
    public ResponseEntity<Void> deleteRestriction(@PathVariable Long id) {
        restrictionService.deleteRestriction(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User u) {
        return ResponseEntity.ok(userService.createUser(u));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> listUsers(@RequestParam Long companyId) {
//        return ResponseEntity.ok(userService.getUsersByCompany(companyId));
//    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User u) {
        return ResponseEntity.ok(userService.updateUser(id, u));
    }

    @PostMapping("/users/{id}/limits")
    public ResponseEntity<UserLimit> assignLimit(@PathVariable Long id, @RequestBody UserLimit l) {
        return ResponseEntity.ok(userService.assignUserLimit(id, l));
    }

    @GetMapping("/users/{id}/consumption")
    public ResponseEntity<List<UserLimit>> getConsumption(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserLimits(id));
    }
}
