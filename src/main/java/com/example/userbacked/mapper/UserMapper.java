package com.example.userbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.userbacked.model.domain.User;
import org.springframework.stereotype.Service;

/**
* @author 82898
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2023-09-18 20:20:54
* @Entity com.example.userbacked.model.domain.User.User
*/
public interface UserMapper extends BaseMapper<User> {

}




