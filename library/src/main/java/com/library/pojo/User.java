package com.library.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @TableName user
 */
@Data
@TableName("user")
public class User implements Serializable, UserDetails {

    @TableId
    private Integer id;

    private String username;

    private String phone;

    private String password;

    private String utimes;//总借阅，阅读量

    private String max_num;//最大借阅数

    private String max_times;//一本书最大续借数

    private String role;

    private String avatar;

    private LocalDateTime joined_date;

    private static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //将权限告知SpringSecurity，通过lambda表达式将String转成Collection<GrantedAuthority>
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singleton(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}