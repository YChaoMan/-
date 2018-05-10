package com.service;

import java.util.List;

import com.entity.Course;

/**
 * 课程表服务接口
 */
public interface CourseService {

    // 新增课程
    int insert(Course course);
    
    // 查询所有的课程信息
    List<Course> queryAll();
    
    // 删除课程，根据课程编号
    int delByNumber(String courseNumber);
    
    // 更新课程信息，根据课程编号
    int updateByNumber(Course course);

    // 条件查询
    Course queryByCondition(Course course);
}
