package com.example.demo.controller;

import com.example.demo.dto.ResultDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("user")
    public ResultDTO login(@RequestBody User user, HttpServletRequest request){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size()==0){
            return new ResultDTO(3,"账号不存在");
        }else if(!user.getPassword().equals(users.get(0).getPassword())){
            return new ResultDTO(2,"密码错误");
        }else{
            request.getSession().setAttribute("user",users.get(0).getUsername());
            return new ResultDTO(1,"登录成功");
        }

    }

}
