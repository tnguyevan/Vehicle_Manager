package com.example.service.impl;

import com.example.dto.ListUserDTO;
import com.example.dto.ListVehicleDTO;
import com.example.dto.SigningAndSignup.UpdatePasswordForAdmin;
import com.example.dto.UserDTO;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.entity.Vehicle;
import com.example.form.AdminUpdateUserForm;
import com.example.form.UpdateAdminForm;
import com.example.repository.IUserRepository;
import com.example.repository.IVehicleRepository;
import com.example.service.IAdminService;
import com.example.util.UserDetailsUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class AdminService implements IAdminService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IVehicleRepository iVehicleRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ListUserDTO getUserById(int userId) {
        User user = userRepository.findById(userId).get();
        ListUserDTO userDTO = modelMapper.map(user, ListUserDTO.class);
        return userDTO;
    }


    @Override
    public Page<ListUserDTO> getListUser(Pageable pageable) {
        Page<User> users = userRepository.getAllByRole(pageable, String.valueOf(Role.ERole.ENDUSER));

        // convert entities --> dtos
        List<ListUserDTO> detailDTOS = modelMapper.map(users.getContent(), new TypeToken<List<ListUserDTO>>() {
        }.getType());

        Page<ListUserDTO> dtoPages = new PageImpl<>(detailDTOS, pageable, users.getTotalElements());

        return dtoPages;
    }


    @Override
    public UserDTO adminUpdateUser(int userId, AdminUpdateUserForm form) {

        User user = userRepository.findById(userId).get();

        if (user.getName() == null || !user.getName().equals(form.getName())) {
            user.setName(form.getName());
        } else {
            throw new RuntimeException("Tên bị trùng");
        }
        if (user.getPhoneNumber() == null || !user.getPhoneNumber().equals(form.getPhoneNumber())) {
            user.setPhoneNumber(form.getPhoneNumber());
        } else {
            throw new RuntimeException("Số điện thoại bị trùng");
        }
        if (user.getAddress() == null || !user.getAddress().equals(form.getAddress())) {
            user.setAddress(form.getAddress());
        } else {
            throw new RuntimeException("Địa chỉ bị trùng");
        }
        User user1 = userRepository.save(user);
        UserDTO userDTO = modelMapper.map(user1, UserDTO.class);

        return userDTO;
    }

    @Override
    public List<ListVehicleDTO> getListVehicle() {
        List<Vehicle> vehicles = iVehicleRepository.findAll();

        // convert entities --> dtos
        List<ListVehicleDTO> detailDTOS = modelMapper.map(vehicles, new TypeToken<List<ListVehicleDTO>>() {
        }.getType());
        return detailDTOS;
    }


    @Override
    public ListUserDTO updateAdmin(UpdateAdminForm form) {

        User user = userRepository.findByUsername(UserDetailsUtils.UserDetails().getUsername());

        if (!user.getName().equals(form.getName())) {
            user.setName(form.getName());
        } else {
            throw new RuntimeException("Tên bị trùng");
        }
        if (!user.getEmail().equals(form.getEmail())) {
            user.setEmail(form.getEmail());
        } else {
            throw new RuntimeException("Số điện thoại bị trùng");
        }
        if (!user.getPhoneNumber().equals(form.getPhoneNumber())) {
            user.setPhoneNumber(form.getPhoneNumber());
        } else {
            throw new RuntimeException("Địa chỉ bị trùng");
        }
        User user1 = userRepository.save(user);
        ListUserDTO map = modelMapper.map(user1, ListUserDTO.class);

        return map;
    }

    @Override
    public UserDTO updatePassword(UpdatePasswordForAdmin updatePasswordForAdmin) {

        User user = userRepository.findByUsername(UserDetailsUtils.UserDetails().getUsername());
        if (passwordEncoder.matches(updatePasswordForAdmin.getOldPassword(), user.getPassword())) {
            if (!updatePasswordForAdmin.getNewPassword1().equals(updatePasswordForAdmin.getNewPassword2())) {
                throw new RuntimeException("Mật khẩu thứ 2 không giống mật khẩu thứ 1!");
            } else {
                user.setPassword(passwordEncoder.encode(updatePasswordForAdmin.getNewPassword1()));
                User user1 = userRepository.save(user);
                UserDTO userDTO = modelMapper.map(user1, UserDTO.class);
                return userDTO;
            }
        } else {

            throw new RuntimeException("Mật khẩu cũ không đúng.");
        }
    }

}
