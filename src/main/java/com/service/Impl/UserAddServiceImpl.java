package com.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.UserAddDao;
import com.entity.UserAdd;
import com.service.UserAddService;

public class UserAddServiceImpl implements UserAddService {
    
    @Autowired
    private UserAddDao userAddDao;

    @Override
    public List<UserAdd> selectAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int insertByUserId(Integer userId) {
        return userAddDao.insertByUserId(userId);
    }

    @Override
    public int delByUserAddId() {
        return 0;
    }

    @Override
    public int updateByUserAddId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public UserAdd selectByUserId() {
        // TODO Auto-generated method stub
        return null;
    }

}
