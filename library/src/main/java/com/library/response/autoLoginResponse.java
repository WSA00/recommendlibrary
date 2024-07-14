package com.library.response;

import com.library.pojo.User;
import lombok.Data;

@Data
public class autoLoginResponse {

    private String username;
    private String phone;
    private Object role;

    public autoLoginResponse() {
    }

    public autoLoginResponse(User user) {
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }

}
