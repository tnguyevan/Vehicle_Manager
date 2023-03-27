package com.example.service;

import com.example.dto.SigningAndSignup.UpdatePasswordForUser;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.entity.token.RegistrationUserToken;
import com.example.entity.token.ResetPasswordToken;
import com.example.form.UserFormForUpdating;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IUserService extends UserDetailsService {

    UserDTO getUserByUsername();

    UserDTO updateUser(String username, UserFormForUpdating form);

    UserDTO updatePassword(String username, UpdatePasswordForUser updatePasswordForUser);

    boolean existsUserByPhoneNumber(String phoneNumber);

    boolean existsByPassword(String password);

    RegistrationUserToken createNewRegistrationUserToken(User user);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    ResetPasswordToken resetPasswordViaPhoneNumber(String phoneNumber);

    void resetPassword(int token, String newPassword);

    ResetPasswordToken createNewResetPasswordToken(User user);


}
