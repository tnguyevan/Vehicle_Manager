package com.example.dto.SigningAndSignup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "The Phone Number mustn't be null value")
    @Length(min = 9, max = 12, message = "The Phone Number length is min 9 and max 12 characters")
    private String phoneNumber;

}
