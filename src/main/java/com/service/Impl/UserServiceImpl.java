package com.service.Impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CourseDao;
import com.dao.GradeDao;
import com.dao.UserAddDao;
import com.dao.UserDao;
import com.entity.Course;
import com.entity.User;
import com.entity.UserAdd;
import com.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private UserAddDao userAddDao;
    
    private String tempType = null;
    
    public List<User> queryAll(Map<String, Object> queryMap) {
        Set<Entry<String, Object>> entry = queryMap.entrySet();
        Iterator<Entry<String, Object>> it = entry.iterator();
        while (it.hasNext()) {
            Entry<String, Object> en = it.next();
            System.out.println(en.getValue() + " / " + en.getKey());
        }
        
        String condition = (String) queryMap.get("condition");
        List<User> userList = null; 
        if (condition == null || "".equals(condition)) {
            userList = this.userDao.queryAll(queryMap);
        } else {
//            if (!condition.matches("[0-9]")) {
//                queryMap.remove("condition");
//                if ("��".equals(condition)) {
//                    condition = "1";
//                }
//                if ("Ů".equals(condition)) {
//                    condition = "0";
//                }
//                if ("��ʦ".indexOf(condition) != -1) {
//                    condition = "1";
//                }
//                if ("ѧ��".indexOf(condition) != -1) {
//                    condition = "0";
//                }
//                queryMap.put("condition", condition);
//            }
            userList = this.userDao.queryByLike(queryMap);
        }
        return userList;
    }

    // ��ȡ�ܼ�¼��
    public int getTotalRow() {
        List<User> userList = this.userDao.totalRow();
        return userList.size();
    }
    
    public int insert(User user) {
        int count = 0;
        String introduction = user.getIntroduction();
        if (introduction == null) { // ������˽���Ϊ�յĻ�
            user.setIntroduction("");
        }
        user.setCreateTime(new Timestamp(new Date().getTime()));    // ���ô���ʱ��
        User userByGrade = queryByName(user.getUserName());
        if (userByGrade == null) {  // �Ƿ񲻴��ڸ�����
            this.userDao.insert(user);
            List<Course> courseList = this.courseDao.queryAll();
            if (!user.isIdentity()) {   // �����������ݲ�����ʦ����ô��ֱ���������û��ĳɼ�
                System.out.println(userAddDao);
                System.out.println("...." + queryByName(user.getUserName()).getUserId());
                userAddDao.insertByUserId(queryByName(user.getUserName()).getUserId());
                user = queryByName(user.getUserName()); // �������������ѯ��Ϊ���ó����е����ԣ�����userId
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("userId", user.getUserId());
                map2.put("courseList", courseList);
                this.gradeDao.insert(map2); // ���ڸóɼ��ĳɼ�����
                count++;    //������ɼ�Ҳ�޸ĳɹ�����ô�����Ż���м�һ
            }
        }
        return count;
    }

    public User queryByName(String userName) {
        if (userName == null && "".equals(userName)) {
            return null;
        }
        return this.userDao.queryByName(userName);
    }

    public int delById(Integer userId) {
        User user = queryById(String.valueOf(userId));
        if (user == null) {
            return 0;
        }
        if (gradeDao.queryById(userId) != null) {   // ����ɼ����д��ڸ�ѧ����Ϣ
            this.gradeDao.delById(user.getUserId());
            this.userAddDao.delByUserAddId(user.getUserId());
        }
        System.out.println("del user success !");
        return this.userDao.delById(user);
    }

    public int updateById(User user, boolean manager) {
        boolean studentBoo = true;    // ����Ƿ���ѧ��
        if (user == null) {
            return 0;
        }
        Integer userId = user.getUserId();
        if (userId == null || "".equals(userId)) {
            return 0;
        }
        List<Course> courseList = this.courseDao.queryAll();
        if (courseList == null || courseList.size() <= 0) {
            return  0;
        }
        // ��ѯ֮ǰ�Ķ���
        User userBefore = queryById(String.valueOf(user.getUserId()));
        if (userBefore == null) {
            return 0;
        }
        // ��ø���֮ǰ�����
        studentBoo = userBefore.isIdentity();
        if (manager) {  // ����ǹ���˵�¼����Ե�¼�������
            userBefore.setIdentity(user.isIdentity());
            userBefore.setUserName(user.getUserName());
            userBefore.setSex(user.getSex());
            userBefore.setBirthday(user.getBirthday());
            userBefore.setIntroduction(user.getIntroduction());
        } else {
            userBefore.setIntroduction(user.getIntroduction()); // ������û��˵�¼��ֻ�޸ĸ��˽���
        }
        int count = this.userDao.updateById(userBefore);
        if (!userBefore.isIdentity()) { // ���������ʦ
            if (userBefore.isIdentity() != studentBoo) {    // ���ǰ���޸ĵ���ݲ�ͬ
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", userId);
                map.put("courseList", courseList);
                this.userAddDao.insertByUserId(userId);
                this.gradeDao.insert(map);
            }
        } else {    // �޸��޸ĺ�ĳɼ�����ʦ����ɾ��֮ǰ�ĳɼ���Ϣ
            this.gradeDao.delById(userBefore.getUserId());
            this.userAddDao.delByUserAddId(userBefore.getUserId());
        }
        return count;
    }

    public User queryById(String userId) {
        if (userId == null || "".equals(userId)) {
            System.out.println("�Ƿ�����!");
            return null;
        }
        return this.userDao.queryById(Integer.valueOf(userId));
    }

    public User queryByIdAndName(String userName, String password) {
        boolean usernameExists = (userName == null || "".equals(userName)); 
        boolean passwordExists = (password == null || "".equals(password));
        if (usernameExists || passwordExists) { // ���������ֵ��һ��Ϊ��
            return null;
        }
        User user = new User();
        user.setUserId(Integer.valueOf(password));
        user.setUserName(userName);
        return this.userDao.queryByIdAndName(user);
    }

    public List<User> queryByUserInnerGradeInnerCourse(String type, String courseNumber) {
        if (type == null || "".equals(type)) {
            type = tempType;    // �����������״̬
        } else {
            tempType = type;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("typeInt", Integer.valueOf(type));
        map.put("courseNumber", courseNumber);
        return this.userDao.queryByUserInnerGradeInnerCourse(map);
    }

}
