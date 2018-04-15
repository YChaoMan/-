package com.dao;

import java.util.List;

import com.entity.Course;

/**
 * �γ̱����ݿ�����ӿ�
 */
public interface CourseDao {

    // �����γ�
    void insert(Course course);
    
    // ��ѯ���еĿγ���Ϣ
    List<Course> queryAll();
    
    // ɾ���γ̣����ݿγ̱��
    int delByNumber(Course course);
    
    // ���¿γ���Ϣ�����ݿγ̱��
    int updateByNumber(Course course);

    // ������ѯ
    Course queryByCondition(Course course);
}
