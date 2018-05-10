package com.dao;

import java.util.List;

import com.entity.UserAdd;

public interface UserAddDao {

    List<UserAdd> selectAll();
    
    int insertByUserId(Integer userId);
    
    int delByUserAddId(Integer userId);
    
    int updateByUserAddId(Integer userId);
    
    int updateByUserAddId2(Integer userId);
    
    UserAdd selectByUserId(Integer userId);
}
