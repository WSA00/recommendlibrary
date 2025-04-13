package com.library.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.mapper.BookMapper;
import com.library.mapper.HistoryMapper;
import com.library.mapper.WarehouseMapper;
import com.library.pojo.History;
import com.library.pojo.User;
import com.library.response.*;
import com.library.service.UserService;
import com.library.mapper.UserMapper;
import com.library.utils.JwtHelper;
import com.library.utils.Result;
import com.library.utils.ResultCodeEnum;
import com.qiniu.api.auth.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.PutPolicy;


/**
* @author ASUS
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-04-09 14:59:47
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public Result userUpload() throws AuthException, JSONException {

        String ACCESS_KEY = "SObw8EKoebhu1fGvweX_m8-i5Y3llZ456Jyv0Raf";
        String SECRET_KEY = "6Qsk9dR7pb2Y2GwlvHl_Q1DTZ3IMb8vluoF41w2M";
        Mac mac = new Mac(ACCESS_KEY, SECRET_KEY);
        // 请确保该bucket已经存在
        String bucketName = "wsa00";
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uploadtoken = putPolicy.token(mac);

        // 构造返回的数据
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("tip", "成功获取七牛云上传凭据");
        data.put("uploadToken", uploadtoken);

        // 返回 Result 对象
        return Result.ok(data);
    }

    @Override
    public Result userHistory(Integer id) {
        //指定用户总借阅量
        Integer count = userMapper.selectUtimesById(id);

        //指定用户本月借阅量
        Integer monthcount = historyMapper.selectMonthCountById(id);

        //指定用户借阅信息
        List<History> historyList = historyMapper.selectHistoryByUserId(id);

        // 创建用于存放转换后数据的列表
        List<historyResponse> userhistoryResponseList = new ArrayList<>();

        // 遍历历史记录列表，转换并封装到historyResponseList中
        for (History history : historyList) {

            historyResponse historyResponse = new historyResponse();
            historyResponse.setId(history.getId());
            historyResponse.setBname(bookMapper.selectBnameById(history.getBid()));
            historyResponse.setAuthor(bookMapper.selectBookAuthorById(history.getBid()));
            historyResponse.setPress(bookMapper.selectBookPressById(history.getBid()));
            historyResponse.setLocation(warehouseMapper.selectLocationById(history.getWid()));
            historyResponse.setBegin_time(history.getBegin_time());
            historyResponse.setEnd_time(history.getEnd_time());
            historyResponse.setTimes(history.getTimes());
            historyResponse.setStatus(history.getStatus());
            // 将封装好的historyResponse对象添加到列表中
            userhistoryResponseList.add(historyResponse);
        }



        Map data = new LinkedHashMap();
        data.put("count",count);
        data.put("monthcount",monthcount);
        data.put("source",userhistoryResponseList);

        return Result.ok(data);

    }


    @Override
    public Result register(User user) {

        int rows = userMapper.insert(user);
        System.out.println("rows = " + rows);
        User dbUser = userMapper.selectById(user.getId());

        Map data = new HashMap();
        data.put("tip","注册成功");
        data.put("user",dbUser);

        return Result.ok(data);
    }

    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        //根据手机号，在数据库找出对应的账号
        User dbuser = userMapper.selectOne(queryWrapper);

        //数据库中没有这个手机号----没注册过
        if (dbuser == null) {
            Map data = new LinkedHashMap();
            data.put("tip","登陆失败");
            return Result.build(data,ResultCodeEnum.no_Resource);
        }

        if (!StringUtils.isEmpty(user.getPassword()) && user.getPassword().equals(dbuser.getPassword())) {
            String token = jwtHelper.createToken(Long.valueOf(dbuser.getId()));

            Map data = new LinkedHashMap();
            data.put("tip","登陆成功");
            data.put("user",dbuser);
            data.put("token",token);

            return Result.ok(data);
        }


        Map data = new LinkedHashMap();
        data.put("tip","登陆失败");
        return Result.build(data,ResultCodeEnum.Request_failed);
    }

    @Override
    public Result AutoLogin(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        Long userId = jwtHelper.getUserId(token);
        User user = userMapper.selectById(userId);

        Map data = new HashMap();
        data.put("tip","自动登录成功");
        data.put("user",user);

        return Result.ok(data);
    }

    @Override
    public Result userPageSelect(Integer page, Integer pageSize) {

        Page<Map<String, Object>> mapPage = new Page<>(page, pageSize);
        //分页
        IPage<Map<String, Object>> result = userMapper.selectMapsPage(mapPage,null);
        List<Map<String, Object>> records = result.getRecords(); // 查询结果列表
        //System.out.println("records = " + records);
        //count User 不导入queryWrapper
        //LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        Long count = userMapper.selectCount(null);

        List list = new ArrayList<>();
        for (Map<String, Object> record : records) {
            User user = new User();
            user.setId((Integer) record.get("id"));
            user.setAvatar((String) record.get("avatar"));
            user.setUsername((String) record.get("username"));
            user.setPhone((String) record.get("phone"));
            user.setJoined_date((LocalDateTime) record.get("joined_date"));
            user.setRole((String) record.get("role"));
            list.add(user);
        }

        Map data = new LinkedHashMap();
        data.put("tip","成功获取第"+page+"页共"+pageSize+"条数据");
        data.put("page",page);
        data.put("count",pageSize);
        data.put("pageTotal",(int)Math.ceil(count/pageSize));
        data.put("userTotal",count);
        data.put("userList", list);

        return Result.ok(data);
    }

    @Override
    public Result selectUserById(Integer id){

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        User user = userMapper.selectOne(queryWrapper);

        Map data = new HashMap<>();
        data.put("tip","成功获取指定用户");
        data.put("user",user);

        return Result.ok(data);
    }

    @Override
    public Result updateUserById(Integer id, User user) {

        user.setId(id);
        userMapper.updateById(user);

        //在数据库找出这个用户
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getId,user.getId());
//        User selectOne = userMapper.selectOne(queryWrapper);

        Map data = new HashMap<>();
        data.put("tip","成功更新用户信息");
//        data.put("user",new loginResponse(selectOne));

        return Result.ok(data);
    }

    @Override
    public Result updateAvatar(Integer id,String avatar) {

        if (avatar != null && avatar.length() > 1 && avatar.startsWith("\"") && avatar.endsWith("\"")) {
            avatar = avatar.substring(1, avatar.length() - 1);
        }

        User user =new User();
        user.setId(id);
        user.setAvatar(avatar);
        userMapper.updateAvatar(id,avatar);
        Map data = new HashMap();
        data.put("tip","成功修改用户头像");
        data.put("avatar",user.getAvatar());
        return Result.ok(data);
    }

    @Override
    public Result deleteUser(Integer id) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId,id);
        int i = userMapper.delete(queryWrapper);

        Map data = new LinkedHashMap();

        if(i==0){
            data.put("tip","删除用户失败");
        }
        if (i>0){
            System.out.println("删除用户"+id+"成功");
            data.put("tip","成功删除用户");
        }
        return Result.ok(data);
    }

    @Override
    public Result checkPhone(String phone) {
        User user = userMapper.checkPhone(phone);

        if(user==null){
            return Result.ok(false);
        }
        return Result.ok(true);
    }

    @Override
    public Result getAllUser() {
        List<User> users = userMapper.getAllUser();

        return Result.ok(users);
    }

    @Override
    public Result updatePassword(Integer id, ChangePassword changePassword) {
        Map data = new HashMap();

        String dbPassword = userMapper.selectUserPasswordById(id);
        if(!changePassword.getOriginalPassword().equals(dbPassword)){
            data.put("success",false);
            data.put("message","原密码错误");
        }else {
            userMapper.updatePasswordByUserId(id,changePassword.getPasswordConfirmed());
            data.put("success",true);
            data.put("tip","成功修改密码");
        }
        return Result.ok(data);
    }
}




