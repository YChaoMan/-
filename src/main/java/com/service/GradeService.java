package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Grade;

/**
 * �ɼ������ӿ�
 */
public interface GradeService {

    // ��ѯȫ���ɼ���Ϣ
    List<Grade> queryAll();
    
    // �����ɼ������γ̱�ţ��ɼ���
    void insert(Map<String, Object> map);
    
    // ɾ���ɼ���Ϣ�����ݿγ̱�Ž���ɾ����
    int delByCourseNumber(String courseNumber);
    
    // ɾ���ɼ���Ϣ������ѧ����Ž���ɾ����
    int delById(Integer userId);
    
    // �ɼ������޸Ŀγ̳ɼ��������û���źͿγ̱�Ž���ʶ��
    int updateById(Grade grade, boolean del);
    
    // ������ѯ���������û���Ž��в��ҡ�
    List<Grade> queryById(String userId);
    
    // ���ݱ�ź�ѧ����Ž��в�ѯ
    Grade queryByIdAndNumber(Grade grade);
    
    /*
     * �����������������޸ĳɼ�.
     * �����û��ı�źͿγ����ƽ��в���
     */
    int saveScoreByIdAndCourseNumber(Grade grade);
}
