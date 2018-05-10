package com.service;

import java.util.List;
import java.util.Map;

import com.entity.User;

/**
 * 用户表服务接口
 */
public interface UserService {

    // 新增课程
    int insert(User user);
    
    // 根据身份查询查找用户,【全部查询】
    List<User> queryAll(Map<String, Object> queryMap);
    
    // 获取总记录
    int getTotalRow();
    
    // 根据名称查找用户
    User queryByName(String userName);
    
    // 删除用户【根据用户编号进行删除】
    int delById(Integer userId);
    
    // 修改用户，【修改姓名，性别，出生日期，个人介绍】，manager是判断是否是管理员登录
    int updateById(User user, boolean manager);
    
    // 条件查询，【根据用户id进行查找】
    User queryById(String userId);
    
    // 条件查询，【根据编号和用户名称进行查找】
    User queryByIdAndName(String userName, String password);
    
    // 连表查询
    List<User> queryByUserInnerGradeInnerCourse(String type, String courseNumber);
}
