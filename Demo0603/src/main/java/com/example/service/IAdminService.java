package com.example.service;

import com.example.dto.ListUserDTO;
import com.example.dto.ListVehicleDTO;
import com.example.dto.SigningAndSignup.UpdatePasswordForAdmin;
import com.example.dto.SigningAndSignup.UpdatePasswordForUser;
import com.example.dto.UserDTO;
import com.example.form.AdminUpdateUserForm;
import com.example.form.UpdateAdminForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAdminService {

    Page<ListUserDTO> getListUser(Pageable pageable);

    ListUserDTO getUserById(int userId);

    UserDTO adminUpdateUser(int userId, AdminUpdateUserForm form);

    List<ListVehicleDTO> getListVehicle();

    ListUserDTO updateAdmin(UpdateAdminForm form) ;

    UserDTO updatePassword(UpdatePasswordForAdmin updatePasswordForAdmin) ;


}
