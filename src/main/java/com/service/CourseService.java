package com.service;

import java.util.List;

import com.entity.Course;

/**
 * �γ̱����ӿ�
 */
public interface CourseService {

    // �����γ�
    int insert(Course course);
    
    // ��ѯ���еĿγ���Ϣ
    List<Course> queryAll();
    
    // ɾ���γ̣����ݿγ̱��
    int delByNumber(String courseNumber);
    
    // ���¿γ���Ϣ�����ݿγ̱��
    int updateByNumber(Course course);

    // ������ѯ
    Course queryByCondition(Course course);
}
