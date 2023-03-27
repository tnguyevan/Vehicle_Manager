package com.example.dto.SigningAndSignup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePassword {

    @NotEmpty(message = "The Password mustn't be null value")
    @Length(min = 6, max = 50, message = "The Password length is min 6 and max 50 characters")
    private String password;

}
