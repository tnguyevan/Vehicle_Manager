package com.example.controller;


import com.example.dto.SigningAndSignup.UpdatePasswordForUser;
import com.example.form.UserFormForUpdating;
import com.example.service.IUserService;
import com.example.util.ResponseUtil;
import com.example.util.UserDetailsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {


    @Autowired
    private IUserService iUserService;


    // dành cho user
    @GetMapping(value = "/get-info")
    public ResponseEntity<?> getUser() {
        return ResponseUtil.ok(iUserService.getUserByUsername());
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody UserFormForUpdating form) {
        return ResponseUtil.ok(iUserService.updateUser(UserDetailsUtils.UserDetails().getUsername(), form));
    }

    @PutMapping(value = "/update-password-user")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordForUser updatePasswordForUser) {
        return ResponseUtil.ok(iUserService.updatePassword(UserDetailsUtils.UserDetails().getUsername(), updatePasswordForUser), "Update Password success!");
    }


}
