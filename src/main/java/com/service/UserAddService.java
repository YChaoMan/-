package com.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dao.UserAddDao;
import com.entity.UserAdd;

@Service
public interface UserAddService {
    
    List<UserAdd> selectAll();
    
    int insertByUserId(Integer userId);
    
    int delByUserAddId();
    
    int updateByUserAddId();
    
    UserAdd selectByUserId();

}
