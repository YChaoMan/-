package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.entity.User;

/**
 * �û������ݿ�����ӿ�
 */
public interface UserDao {

    // �����γ�
    int insert(User user);
    
    // ������ݲ�ѯ�����û�,��ȫ����ѯ�� org.apache.ibatis.annotations.Param
    List<User> queryAll(@Param("queryMap") Map<String, Object> queryMap);
    
    // ģ����ѯ
    List<User> queryByLike(@Param("queryMap") Map<String, Object> queryMap);
    
    // ��ѯ�ܼ�¼
    List<User> totalRow();
     
    // �������Ʋ����û�
    User queryByName(String userName);
    
    // ɾ���û��������û���Ž���ɾ����
    int delById(User user);
    
    // �޸��û������޸��������Ա𣬳������ڣ����˽��ܡ�
    int updateById(User user);
    
    // ������ѯ���������û�id���в��ҡ�
    User queryById(Integer userId);
    
    // ������ѯ�������ݱ�ź��û����ƽ��в��ҡ�
    User queryByIdAndName(User user);
    
    // �����ѯ
    List<User> queryByUserInnerGradeInnerCourse(Map<String, Object> map);
}
