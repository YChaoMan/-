package com.dao;

import java.util.List;

import com.entity.Course;

/**
 * 课程表数据库操作接口
 */
public interface CourseDao {

    // 新增课程
    void insert(Course course);
    
    // 查询所有的课程信息
    List<Course> queryAll();
    
    // 删除课程，根据课程编号
    int delByNumber(Course course);
    
    // 更新课程信息，根据课程编号
    int updateByNumber(Course course);

    // 条件查询
    Course queryByCondition(Course course);
}
