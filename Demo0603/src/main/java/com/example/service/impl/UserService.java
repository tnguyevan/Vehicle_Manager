package com.example.service.impl;

import com.example.dto.SigningAndSignup.UpdatePasswordForUser;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.entity.token.RegistrationUserToken;
import com.example.entity.token.ResetPasswordToken;
import com.example.form.UserFormForUpdating;
import com.example.repository.IUserRepository;
import com.example.repository.RegistrationUserTokenRepository;
import com.example.repository.ResetPasswordTokenRepository;
import com.example.service.IUserService;
import com.example.util.UserDetailsUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private RegistrationUserTokenRepository registrationUserTokenRepository;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getUserByUsername() {
        User user = userRepository.findByUsername(UserDetailsUtils.UserDetails().getUsername());
        if (user.getName() == null) {
            throw new RuntimeException("Bạn chưa khởi tạo tên.");
        } else if (user.getAddress() == null) {
            throw new RuntimeException("Bạn chưa khởi tạo địa chỉ.");
        }
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }


    @Override
    public UserDTO updateUser(String username, UserFormForUpdating form) {

        User user = userRepository.findByUsername(username);

        if (user.getName() == null || !user.getName().equals(form.getName())) {
            user.setName(form.getName());
        } else {
            throw new RuntimeException("Tên bị trùng");
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
    public UserDTO updatePassword(String username, UpdatePasswordForUser updatePasswordForUser) {
        User user = userRepository.findByUsername(username);
        if (passwordEncoder.matches(updatePasswordForUser.getOldPassword(), user.getPassword())) {
            if (!updatePasswordForUser.getNewPassword1().equals(updatePasswordForUser.getNewPassword2())) {
                throw new RuntimeException("Mật khẩu thứ 2 không giống mật khẩu thứ 1!");
            } else {
                user.setPassword(passwordEncoder.encode(updatePasswordForUser.getNewPassword1()));
                userRepository.save(user);
            }
        } else {
            throw new RuntimeException("Mật khẩu cũ không đúng.");
        }
        User user1 = userRepository.findByUsername(username);
        UserDTO userDTO = modelMapper.map(user1, UserDTO.class);
        return userDTO;
    }


    @Override
    public boolean existsUserByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByPassword(String password) {
        return userRepository.existsByPassword(password);
    }

    @Override
    public RegistrationUserToken createNewRegistrationUserToken(User user) {
        // create new token for confirm Registration
        final int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        RegistrationUserToken token = new RegistrationUserToken(code, user);

        registrationUserTokenRepository.save(token);
        return token;

    }


    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        return UserDetail.build(user);
    }

    @Override
    public ResetPasswordToken resetPasswordViaPhoneNumber(String phoneNumber) {


        // find user by phoneNumber
        User user = userRepository.findUserByPhoneNumber(phoneNumber);

        // remove token token if exists
        resetPasswordTokenRepository.deleteByUserId(user.getId());

        // create new reset password token
        ResetPasswordToken token = createNewResetPasswordToken(user);
        return token;
    }

    @Override
    public void resetPassword(int token, String newPassword) {
        // get token
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);

        // change password
        User user = resetPasswordToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // remove Reset Password
        resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
    }

    public ResetPasswordToken createNewResetPasswordToken(User user) {

        // create new token for Reset password
        final int newToken = (int) Math.floor(((Math.random() * 899999) + 100000));
        ResetPasswordToken token = new ResetPasswordToken(newToken, user);

        resetPasswordTokenRepository.save(token);

        return token;

    }
}
