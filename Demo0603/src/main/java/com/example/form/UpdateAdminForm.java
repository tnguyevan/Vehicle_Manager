package com.example.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@NoArgsConstructor
public class UpdateAdminForm {

    private String name;

    @Email(message = "{User.createUser.form.email.Email}")
    @Length(min = 6, max = 50, message = "{User.createUser.form.Length6and50}")
    private String email;

    @Length(min = 9, max = 12, message = "The Phone Number length is min 9 and max 12 characters")
    private String phoneNumber;


}
