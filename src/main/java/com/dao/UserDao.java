package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.entity.User;

/**
 * 用户表数据库操作接口
 */
public interface UserDao {

    // 新增课程
    int insert(User user);
    
    // 根据身份查询查找用户,【全部查询】 org.apache.ibatis.annotations.Param
    List<User> queryAll(@Param("queryMap") Map<String, Object> queryMap);
    
    // 模糊查询
    List<User> queryByLike(@Param("queryMap") Map<String, Object> queryMap);
    
    // 查询总记录
    List<User> totalRow();
     
    // 根据名称查找用户
    User queryByName(String userName);
    
    // 删除用户【根据用户编号进行删除】
    int delById(User user);
    
    // 修改用户，【修改姓名，性别，出生日期，个人介绍】
    int updateById(User user);
    
    // 条件查询，【根据用户id进行查找】
    User queryById(Integer userId);
    
    // 条件查询，【根据编号和用户名称进行查找】
    User queryByIdAndName(User user);
    
    // 连表查询
    List<User> queryByUserInnerGradeInnerCourse(Map<String, Object> map);
}
