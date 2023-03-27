package com.example.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class AdminUpdateUserForm {

    private String name;

    @Length(min = 9, max = 12, message = "The Phone Number length is min 9 and max 12 characters")

    private String phoneNumber;

    private String address;
}
