package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.alibaba.fastjson.JSON;
import com.entity.Course;
import com.entity.Grade;
import com.entity.User;
import com.service.CourseService;
import com.service.GradeService;
import com.service.UserService;

@Controller
@RequestMapping("/StudentController")
public class StudentController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GradeService gradeService;

    /*
     * 学生登录成功的Controller中的方法,返回查询到的个人信息、成绩、课程
     */
    @RequestMapping(value = "/studentLogin", method = RequestMethod.GET)
    public String studentLogin(HttpServletRequest request, String userName, String userId) {
        User user = userService.queryByIdAndName(userName, userId);
        List<Course> courseList = courseService.queryAll();
        List<Grade> gradeList = gradeService.queryById(userId);
        
        if (user != null) {
            request.setAttribute("user", user);
        }
        if (gradeList != null && gradeList.size() > 0) {
            request.setAttribute("gradeList", gradeList);
        }
        if (courseList != null && courseList.size() > 0) {
            request.setAttribute("courseList", courseList);
        }
        return "student/user";
    }
    
    /*
     * 根据编号来查询学生的信息
     */
    @RequestMapping(value = "/selectStudentById", method = RequestMethod.GET)
    public String selectStudentById(HttpServletRequest request, String userId) {
        User user = userService.queryById(userId);
        if (user != null) {
            request.setAttribute("user", user);
        }
        return "student/user_edit";
    }
    
    /*
     * 根据学生对象来进行更新
     * 此处获取一个对象报400，可能存在传输参数不匹配原因
     */
    @ResponseBody
    @RequestMapping(value = "/studentUpdateById", method = RequestMethod.POST)
    public Map<String, Object> studentUpdateById(User user) {
//    public Map<String, Object> studentUpdateById(String userId, String userName, String introduction) {
//        User user = new User();
//        user.setIntroduction(introduction);
//        User user = new User();
//        user.setUserName(userName);
//        user.setUserId(Integer.valueOf(userId));
//        User user = userService.queryById(userId);
//        if (user == null) {
//            System.out.println("用户不存在!`");
//            return null;
//        }
//        if (introduction != null && !"".equals(introduction)) {
//            user.setIntroduction(introduction);
//        }
//        User user = new User();
//        user.setUserId(Integer.valueOf(userId));
//        user.setIntroduction(introduction);
        int count = userService.updateById(user, false);
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
//        map.put("userId", user.getUserId());
//        map.put("userName", user.getUserName());
        map.put("userId", user.getUserId());
        map.put("userName", user.getUserName());
        return map;
//        String jsonString = JSON.toJSONString(map);
//        return jsonString;
    }
}
