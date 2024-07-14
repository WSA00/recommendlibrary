package com.library.response;

import lombok.Data;

@Data
public class ChangePassword {
    private String originalPassword;
    private String password;
    private String passwordConfirmed;
}
