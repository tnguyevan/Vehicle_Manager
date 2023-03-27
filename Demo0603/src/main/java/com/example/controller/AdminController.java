package com.example.controller;


import com.example.dto.ChangeSystemInstallationDTO;
import com.example.dto.SigningAndSignup.UpdatePasswordForAdmin;
import com.example.form.AdminUpdateUserForm;
import com.example.form.UpdateAdminForm;
import com.example.service.IAdminService;
import com.example.service.ISystemInstallationService;
import com.example.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/admin")
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private ISystemInstallationService iSystemInstallationService;

    // dành cho admin
    @GetMapping(value = "/get-list-user")
    public ResponseEntity<?> getListUser(Pageable pageable) {
        return ResponseUtil.ok(iAdminService.getListUser(pageable));
    }

    @GetMapping(value = "/get-userId")
    public ResponseEntity<?> getUserById(@Parameter(name = "userId") int userId) {
        return ResponseUtil.ok(iAdminService.getUserById(userId));
    }

    @PutMapping(value = "/admin-update-user")
    public ResponseEntity<?> adminUpdateUser(@Parameter(name = "userId") int userId, @RequestBody AdminUpdateUserForm form) {
        return ResponseUtil.ok(iAdminService.adminUpdateUser(userId, form), "Thông báo sửa thông tin thành công");
    }

    @GetMapping(value = "/get-list-vehicle")
    public ResponseEntity<?> getListVehicle() {
        return ResponseUtil.ok(iAdminService.getListVehicle());
    }

    @PutMapping("/installation")
    public ResponseEntity<?> changeSystemInstallation(@Parameter(name = "id") int id, @RequestBody ChangeSystemInstallationDTO dto) {
        return ResponseUtil.ok(iSystemInstallationService.changeSystemInstallation(id, dto), "CÀI ĐẶT BANNER, LOGO Successfully!");
    }

    @PutMapping(value = "/update-admin")
    public ResponseEntity<?> updateAdmin(@RequestBody UpdateAdminForm form) {
        return ResponseUtil.ok(iAdminService.updateAdmin(form), "Thông báo sửa thông tin thành công");

    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordForAdmin updatePasswordForAdmin) {
        return ResponseUtil.ok(iAdminService.updatePassword(updatePasswordForAdmin), "Update Password success!");

    }
}
