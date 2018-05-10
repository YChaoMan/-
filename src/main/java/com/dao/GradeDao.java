package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Grade;

/**
 * �ɼ������ݿ�����ӿ�
 */
public interface GradeDao {

    // ��ѯȫ���ɼ���Ϣ
    List<Grade> queryAll();
    
    List<Grade> queryByGroup();
    
    // �����ɼ������γ̱�ţ��ɼ���
    void insert(Map<String, Object> map);
    
    // ɾ���ɼ���Ϣ�����ݿγ̱�Ž���ɾ����
    int delByCourseNumber(String courseNumber);
    
    // ɾ���ɼ���Ϣ������ѧ����Ž���ɾ����
    int delById(Integer userId);
    
    // �ɼ������޸Ŀγ̳ɼ��������û���źͿγ̱�Ž���ʶ��
    int updateById(Grade grade);
    
    // ������ѯ���������û���Ž��в��ҡ�
    List<Grade> queryById(Integer userId);
    
    // ���ݱ�ź�ѧ����Ž��в�ѯ
    Grade queryByIdAndNumber(Grade grade);
}
