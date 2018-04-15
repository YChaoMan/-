package com.service;

import java.util.List;
import java.util.Map;

import com.entity.User;

/**
 * �û������ӿ�
 */
public interface UserService {

    // �����γ�
    int insert(User user);
    
    // ������ݲ�ѯ�����û�,��ȫ����ѯ��
    List<User> queryAll(Map<String, Object> queryMap);
    
    // ��ȡ�ܼ�¼
    int getTotalRow();
    
    // �������Ʋ����û�
    User queryByName(String userName);
    
    // ɾ���û��������û���Ž���ɾ����
    int delById(Integer userId);
    
    // �޸��û������޸��������Ա𣬳������ڣ����˽��ܡ���manager���ж��Ƿ��ǹ���Ա��¼
    int updateById(User user, boolean manager);
    
    // ������ѯ���������û�id���в��ҡ�
    User queryById(String userId);
    
    // ������ѯ�������ݱ�ź��û����ƽ��в��ҡ�
    User queryByIdAndName(String userName, String password);
    
    // �����ѯ
    List<User> queryByUserInnerGradeInnerCourse(String type, String courseNumber);
}
