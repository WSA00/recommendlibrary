package com.library.controller;

import com.library.pojo.User;
import com.library.response.ChangePassword;
import com.library.service.UserService;
import com.library.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}/history")
    public Result userHistory(@PathVariable Integer id){
        return userService.userHistory(id);
    }

    @GetMapping("")
    public Result userPageSelect(Integer page, Integer pageSize){
        System.out.println("page = " + page);
        System.out.println("pageSize = " + pageSize);
        if (page == null && pageSize == null) {
            return userService.getAllUser();
        }
        else {
            return userService.userPageSelect(page, pageSize);
        }
    }

    @GetMapping("{id}")
    public Result selectUserById(@PathVariable Integer id){
        return userService.selectUserById(id);
    }

    @PatchMapping("{id}")
    public Result updateUserById(@PathVariable Integer id, @RequestBody User user){
        return userService.updateUserById(id,user);
    }

    @PatchMapping("{id}/avatar")
    public Result updateAvatar(@PathVariable Integer id, User user){
        return userService.updateAvatar(id,user);
    }

    @PreAuthorize("hasAnyAuthority('ROOT')")
    @DeleteMapping("{id}")
    public Result deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }

    @GetMapping("phone/{phone}")
    public Result checkPhone(@PathVariable String phone){
        return userService.checkPhone(phone);
    }

    @PatchMapping("{id}/password")
    public Result updatePassword(@PathVariable String id, @RequestBody ChangePassword changePassword){
        return userService.updatePassword(id,changePassword);
    }

}
