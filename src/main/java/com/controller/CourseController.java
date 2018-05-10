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

import com.entity.Course;
import com.service.CourseService;
//import com.service.GradeService;
//import com.service.UserService;

@Controller
@RequestMapping("/CourseController")
public class CourseController {
    
//    @Autowired
//    private UserService userService;
    @Autowired
    private CourseService courseService;
//    @Autowired
//    private GradeService gradeService;
    
    /*
     * 查询全部的课程信息
     */
    @ResponseBody
    @RequestMapping(value = "/selectAll", method = RequestMethod.POST)
    public Map<String, Object> selectAll() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Course> courseList = courseService.queryAll();
        if (courseList != null && courseList.size() > 0) {
            map.put("courseList", courseList);
        }
        return map;
    }
    
    /*
     * 根据课程编号查询,课程信息
     * courseNumber,课程编号
     */
    @RequestMapping(value = "/selectByCourseNumber", method = RequestMethod.GET)
    public String selectByCourseNumber(HttpServletRequest request, String courseNumber) {
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);
        Course course = courseService.queryByCondition(courses);
        request.setAttribute("course", course);
        return "course/course_edit";
        
    }
    
    /*
     * 删除课程，根据课程编号
     */
    @ResponseBody
    @RequestMapping(value = "/delByNumber", method = RequestMethod.POST)
    public Map<String, Object> delByNumber(String courseNumber) {
//        int count = 0;
//        if (courseNumber == null || "".equals(courseNumber)) {
//            return null;
//        }
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);
        Map<String, Object> map = new HashMap<String, Object>();
        Course course = courseService.queryByCondition(courses);    // 为了查出课程名称
        int count = courseService.delByNumber(courseNumber);
//        if (course != null) {
//            int count = gradeService.delByCourseNumber(courseNumber);
            
//        }
        if (count == 1) {
            map.put("courseName", course.getCourseName());
        }
        map.put("count", count);
        return map;
    }
    
    /*
     * 添加课程信息
     */
    @ResponseBody
    @RequestMapping(value = "/saveCourse", method = RequestMethod.POST)
    public Map<String, Object> saveCourse(Course course) {
//        String courseNumber = course.getCourseNumber();
//        String courseName = course.getCourseName();
//        int count = 0;
//        if (courseNumber == null || "".equals(courseNumber)) {
//            return null;
//        }
//        if (courseName == null || "".equals(courseName)) {
//            return null;
//        }
//        Course courses = new Course();
//        courses.setCourseNumber(courseNumber);
//        if (courseService.queryByCondition(courses) == null) {    // 如果找不该课程编号那么就新增
//            courseService.insert(course);
//            count++;
//            List<Course> courseList = new ArrayList<Course>();
//            courseList.add(courses);
//            List<User> userList = userService.queryAll(0);
//            if (userList != null && userList.size() > 0) {
//                for (User user : userList) {
//                    Map<String, Object> maps = new HashMap<String, Object>();
//                    maps.put("userId", user.getUserId());
//                    maps.put("courseList", courseList);
//                    gradeService.insert(maps);  // 插入课程的时候要对成绩表更新一次
//                }
//            }
//        }
        int count = courseService.insert(course);
        Map<String, Object> map = new HashMap<String, Object>();
        if (count == 1) {
            map.put("courseName", course.getCourseName());
        }
        map.put("count", count);
        return map;
    }
    
    
    /*
     * 修改课程信息
     */
    @ResponseBody
    @RequestMapping(value = "/updateByCourseNumber", method = RequestMethod.POST)
    public Map<String, Object> updateByCourseNumber(Course course) {
//        int count = 0;
//        String courseName = course.getCourseName();
//        String courseNumber = course.getCourseNumber();
//        if (courseNumber == null || "".equals(courseNumber)) {
//            return null;
//        }
//        Course courses = new Course();
//        courses.setCourseNumber(courseNumber);
//        courses.setCourseName(courseName);
//        Course courseBefore = courseService.queryByCondition(courses);
//        if (courseBefore != null) {
//            if (courseName != null && !"".equals(courseName)) {
//                courses.setCourseNumber("");
//                if (courseService.queryByCondition(courses) == null) {    // 如果该课程名称不存在
//                    courseBefore.setCourseNumber(courseNumber);
//                    courseBefore.setCourseName(courseName);
//                    count = courseService.updateByNumber(courseBefore);
//                }
//            }
//        }
        int count = courseService.updateByNumber(course);
        Map<String, Object> map = new HashMap<String, Object>();
        if (count == 1) {
            map.put("courseName", course.getCourseName());
        }
        map.put("count", count);
        return map;
    }
    
}
