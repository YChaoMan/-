package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Grade;

/**
 * 成绩表数据库操作接口
 */
public interface GradeDao {

    // 查询全部成绩信息
    List<Grade> queryAll();
    
    // 新增成绩，【课程编号，成绩】
    void insert(Map<String, Object> map);
    
    // 删除成绩信息【根据课程编号进行删除】
    int delByCourseNumber(String courseNumber);
    
    // 删除成绩信息【根据学生编号进行删除】
    int delById(Integer userId);
    
    // 成绩，【修改课程成绩，根据用户编号和课程编号进行识别】
    int updateById(Grade grade);
    
    // 条件查询，【根据用户编号进行查找】
    List<Grade> queryById(Integer userId);
    
    // 根据编号和学生编号进行查询
    Grade queryByIdAndNumber(Grade grade);
}
