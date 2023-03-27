package com.example.controller;


import com.example.dto.SigningAndSignup.CreatePassword;
import com.example.dto.SigningAndSignup.SigningRequest;
import com.example.dto.SigningAndSignup.SignupRequest;
import com.example.entity.Role;
import com.example.entity.User;
import com.example.entity.UserStatus;
import com.example.entity.token.RegistrationUserToken;
import com.example.entity.token.ResetPasswordToken;
import com.example.repository.IUserRepository;
import com.example.repository.RegistrationUserTokenRepository;
import com.example.repository.ResetPasswordTokenRepository;
import com.example.response.MessageResponse;
import com.example.response.UserInfoResponse;
import com.example.security.jwt.JwtUtils;
import com.example.service.impl.UserDetail;
import com.example.service.IUserService;
import com.example.util.RandomUtils;
import com.example.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Validated
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegistrationUserTokenRepository registrationUserTokenRepository;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigningRequest signingRequest) throws Exception {
        UserDetail userDetails;
        Authentication authentication;
        if (!userService.existsUserByPhoneNumber(signingRequest.getPhoneNumber())) {
            return ResponseUtil.badRequest("Error: Phone Number is not exists!");
        }
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signingRequest.getPhoneNumber(), signingRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userDetails = (UserDetail) authentication.getPrincipal();
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(
                        userDetails.getStatus().equals(UserStatus.ACTIVE) ? jwt : null
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = userRepository.findUserByPhoneNumber(signUpRequest.getPhoneNumber());
        if (user == null) {
            // Tạo mới
            User user1 = new User(signUpRequest.getPhoneNumber());
            userRepository.save(user1);
            // create new user registration token
            RegistrationUserToken token = userService.createNewRegistrationUserToken(user1);
            return ResponseUtil.ok("Mã của bạn là: " + token.getToken());

        } else if (user.getRole() == null) {
            // Xóa cũ
            userRepository.delete(user);
            registrationUserTokenRepository.deleteByUserId(user.getId());

            // Tạo mới
            User user1 = new User(signUpRequest.getPhoneNumber());
            userRepository.save(user1);
            // create new user registration token
            RegistrationUserToken token = userService.createNewRegistrationUserToken(user1);
            return ResponseUtil.ok("Mã của bạn là: " + token.getToken());
        } else {
            return ResponseUtil.badRequest("Error: Phone Number is already taken!");
        }
    }

    @GetMapping("/existsToken")
    public ResponseEntity<?> existsToken(@RequestParam(name = "token") int token) {
        if (!registrationUserTokenRepository.existsByToken(token)) {
            return ResponseUtil.badRequest("Error: Token is not exists!");
        }
        RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);
        // active user
        User user = registrationUserToken.getUser();

        Role role = new Role();
        role.setId((short) 2);
        role.setERole(Role.ERole.ENDUSER);
        user.setRole(role);

        userRepository.save(user);

        // remove Registration User Token
        registrationUserTokenRepository.deleteById(registrationUserToken.getId());
        return ResponseUtil.ok("Mời bạn tạo mật khẩu.");
    }

    @PostMapping("/createPassword")
    public ResponseEntity<?> createPassword(@RequestParam(name = "phoneNumber") String phoneNumber, @RequestBody CreatePassword password) {

        User user = userRepository.findUserByPhoneNumber(phoneNumber);

        if (!userService.existsUserByPhoneNumber(phoneNumber)) {
            return ResponseUtil.badRequest("Error: Phone Number is not exists!");
        } else if (userService.existsUserByPhoneNumber(phoneNumber) && user.getPassword() != null) {
            return ResponseUtil.badRequest("Error: Mật khẩu số điện thoại này đã tồn tại.");
        }
        if (user.getRole() == null) {
            return ResponseUtil.badRequest("Bạn phải xác thực mã trước.");
        }
        // Create new password
        user.setUsername(1900 + RandomUtils.randomAlphaNumeric(5));
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(encoder.encode(password.getPassword()));
        userRepository.save(user);
        return ResponseUtil.ok("Bạn đã tạo mật khẩu thành công!");
    }

    @GetMapping("/resetPasswordRequest")
    public ResponseEntity<?> sendResetPasswordViaPhoneNumber(@RequestParam(name = "phoneNumber") String phoneNumber) {
        if (!userService.existsUserByPhoneNumber(phoneNumber)) {
            return ResponseUtil.badRequest("Error: Phone Number is not exists!");
        }
        ResetPasswordToken token = userService.resetPasswordViaPhoneNumber(phoneNumber);
        return ResponseUtil.ok("Mã của bạn là: " + token.getToken());
    }

    @GetMapping("/resetPassword")
    public ResponseEntity<?> resetPasswordViaPhoneNumber(@RequestParam(name = "token") int token, @RequestParam(name = "newPassword") String newPassword) {
        if (!resetPasswordTokenRepository.existsByToken(token)) {
            return ResponseUtil.badRequest("Error: Token is not exists!");
        }
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);

        //  Create new username
        User user = resetPasswordToken.getUser();
        if (user.getUsername() == null) {
            user.setUsername(1900 + RandomUtils.randomAlphaNumeric(5));
        }
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);

        // reset password
        userService.resetPassword(token, newPassword);
        return ResponseUtil.ok("Reset Password success!");
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.clearJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

}
