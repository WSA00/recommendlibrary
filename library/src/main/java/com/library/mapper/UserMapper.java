package com.library.mapper;
import java.util.List;

import com.library.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @description 针对表【user】的数据库操作Mapper
*/
public interface UserMapper extends BaseMapper<User> {


    User selectByPhone(String userPhone);

    User findByPhone(String phoneNumber);

    User checkPhone(String phone);

    List<User> getAllUser();

    String selectUserPhoneById(@Param("userId") Integer userId);

    String selectUserNameById(@Param("userId")Integer userId);

    String selectUserPasswordById(@Param("userId") Integer userId);

    void updatePasswordByUserId(@Param("userId")Integer userId, @Param("password")String password);

    Integer selectNumById(Integer uid);

    Integer selectTimesById(Integer uid);

    Integer selectUtimesById(Integer uid);

    void updateAvatar(Integer id, String avatar);
}




