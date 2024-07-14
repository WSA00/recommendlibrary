package com.library.response;

import com.library.pojo.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class registerResponse {
    private Integer id;

    private String username;

    private String password;

    private String phone;

    private String role;

    private String address;

    private String avatar;

    private LocalDateTime joined_date;


    public registerResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.role = user.getRole();
        //this.address = user.getAddress();
        this.avatar = user.getAvatar();
        this.joined_date = user.getJoined_date();
    }
}
