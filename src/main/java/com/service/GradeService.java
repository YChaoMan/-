package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Grade;

/**
 * 成绩表服务接口
 */
public interface GradeService {

    // 查询全部成绩信息
    List<Grade> queryAll();
    
    // 新增成绩，【课程编号，成绩】
    void insert(Map<String, Object> map);
    
    // 删除成绩信息【根据课程编号进行删除】
    int delByCourseNumber(String courseNumber);
    
    // 删除成绩信息【根据学生编号进行删除】
    int delById(Integer userId);
    
    // 成绩，【修改课程成绩，根据用户编号和课程编号进行识别】
    int updateById(Grade grade, boolean del);
    
    // 条件查询，【根据用户编号进行查找】
    List<Grade> queryById(String userId);
    
    // 根据编号和学生编号进行查询
    Grade queryByIdAndNumber(Grade grade);
    
    /*
     * 这里新增本质上是修改成绩.
     * 根据用户的编号和课程名称进行查找
     */
    int saveScoreByIdAndCourseNumber(Grade grade);
}
