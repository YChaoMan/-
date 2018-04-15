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
import com.dao.UserDao;
import com.entity.Course;
import com.entity.Page;
import com.entity.User;
import com.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GradeDao gradeDao;
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
//                if ("男".equals(condition)) {
//                    condition = "1";
//                }
//                if ("女".equals(condition)) {
//                    condition = "0";
//                }
//                if ("老师".indexOf(condition) != -1) {
//                    condition = "1";
//                }
//                if ("学生".indexOf(condition) != -1) {
//                    condition = "0";
//                }
//                queryMap.put("condition", condition);
//            }
            userList = this.userDao.queryByLike(queryMap);
        }
        return userList;
    }

    // 获取总记录数
    public int getTotalRow() {
        List<User> userList = this.userDao.totalRow();
        return userList.size();
    }
    
    public int insert(User user) {
        int count = 0;
        String introduction = user.getIntroduction();
        if (introduction == null) { // 如果个人介绍为空的话
            user.setIntroduction("");
        }
        user.setCreateTime(new Timestamp(new Date().getTime()));    // 设置创建时间
        User userByGrade = queryByName(user.getUserName());
        if (userByGrade == null) {  // 是否不存在该名字
            this.userDao.insert(user);
            List<Course> courseList = this.courseDao.queryAll();
            if (!user.isIdentity()) {   // 如果新增的身份不是老师，那么就直接新增该用户的成绩
                user = queryByName(user.getUserName()); // 这里根据姓名查询是为了拿出所有的属性，包括userId
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("userId", user.getUserId());
                map2.put("courseList", courseList);
                this.gradeDao.insert(map2); // 关于该成绩的成绩新增
                count++;    //　如果成绩也修改成功，那么次数才会进行加一
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
        if (gradeDao.queryById(userId) != null) {   // 如果成绩表中存在该学生信息
            this.gradeDao.delById(user.getUserId());
        }
        System.out.println("del user success !");
        return this.userDao.delById(user);
    }

    public int updateById(User user, boolean manager) {
        boolean studentBoo = true;    // 标记是否是学生
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
        // 查询之前的对象
        User userBefore = queryById(String.valueOf(user.getUserId()));
        if (userBefore == null) {
            return 0;
        }
        // 获得更改之前的身份
        studentBoo = userBefore.isIdentity();
        if (manager) {  // 如果是管理端登录则可以登录多个属性
            userBefore.setIdentity(user.isIdentity());
            userBefore.setUserName(user.getUserName());
            userBefore.setSex(user.getSex());
            userBefore.setBirthday(user.getBirthday());
            userBefore.setIntroduction(user.getIntroduction());
        } else {
            userBefore.setIntroduction(user.getIntroduction()); // 如果是用户端登录则只修改个人介绍
        }
        int count = this.userDao.updateById(userBefore);
        if (!userBefore.isIdentity()) { // 如果不是老师
            if (userBefore.isIdentity() != studentBoo) {    // 如果前后修改的身份不同
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", userId);
                map.put("courseList", courseList);
                this.gradeDao.insert(map);
            }
        } else {    // 修改修改后的成绩是老师，则删除之前的成绩信息
            this.gradeDao.delById(userBefore.getUserId());
        }
        return count;
    }

    public User queryById(String userId) {
        if (userId == null || "".equals(userId)) {
            System.out.println("非法输入!");
            return null;
        }
        return this.userDao.queryById(Integer.valueOf(userId));
    }

    public User queryByIdAndName(String userName, String password) {
        boolean usernameExists = (userName == null || "".equals(userName)); 
        boolean passwordExists = (password == null || "".equals(password));
        if (usernameExists || passwordExists) { // 如果这两个值有一个为空
            return null;
        }
        User user = new User();
        user.setUserId(Integer.valueOf(password));
        user.setUserName(userName);
        return this.userDao.queryByIdAndName(user);
    }

    public List<User> queryByUserInnerGradeInnerCourse(String type, String courseNumber) {
        if (type == null || "".equals(type)) {
            type = tempType;    // 保存升降序的状态
        } else {
            tempType = type;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("typeInt", Integer.valueOf(type));
        map.put("courseNumber", courseNumber);
        return this.userDao.queryByUserInnerGradeInnerCourse(map);
    }

}
