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
import com.entity.Grade;
import com.entity.User;
import com.service.CourseService;
import com.service.GradeService;
import com.service.UserService;

@Controller
@RequestMapping("/GradeController")
public class GradeController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GradeService gradeService;

    /*
     * 根据用户的编号来进行查询成绩
     */
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    public String queryById(HttpServletRequest request, String userId) {
        User user = userService.queryById(userId);
        List<Grade> gradeList = gradeService.queryById(userId);
        List<Course> courseList = courseService.queryAll();
        
        if (courseList != null && courseList.size() > 0) {
            request.setAttribute("courseList", courseList);
        }
        if (gradeList != null && gradeList.size() > 0) {
            request.setAttribute("gradeList", gradeList);
        }
        if (user != null) {
            request.setAttribute("user", user);
        }
        return "grade/grade_edit";
    }
    
    /*
     * 根据用户的编号来进行更新成绩
     * userId,用户编号
     * courseNumber,课程编号
     * score,成绩
     */
    @ResponseBody
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public Map<String, Object> updateById(Grade grade) {
        Map<String, Object> map = new HashMap<String, Object>();    
        
//        if (userId == null || "".equals(userId)) {
//            return null;
//        }
//        if (courseNumber == null || "".equals(courseNumber)) {
//            return null;
//        }
//        if (score == null || "".equals(score)) {
//            return null;
//        }
//        Course course2 = new Course();
//        course2.setCourseNumber(courseNumber);
//        Course course = courseService.queryByCondition(course2);
//        
//        if (course == null) {   // 如果在课程表中找不到该课程
//            return null;
//        }
//        Grade grades = new Grade();
//        grades.setUserId(Integer.valueOf(userId));
//        grades.setCourseNumber(courseNumber);
//        Grade grade = gradeService.queryByIdAndNumber(grades);
//        if (grade == null) {
//            return null;
//        }
//        Double scoreDouble = Double.valueOf(score);
//        grade.setScore(scoreDouble);
        Course course = new Course();
        course.setCourseNumber(grade.getCourseNumber());
        course = courseService.queryByCondition(course);
        int count = gradeService.updateById(grade, false);
        if (count != 0) {
            map.put("courseName", course.getCourseName());
        }
        map.put("count", count);
        
        return map;
    }
    
    /*
     * 根据用户的编发、课程名称来进行删除成绩
     * userId,用户编号
     * courseNumber,课程编号
     */
    @ResponseBody
    @RequestMapping(value = "/delByIdAndCourseNumber", method = RequestMethod.POST)
    public Map<String, Object> delByIdAndCourseNumber(Grade grade) {
//        int count = 0;
//        if (userId == null || "".equals(userId)) {
//            return null;
//        }
//        if (courseNumber == null || "".equals(courseNumber)) {
//            return null;
//        }
//        Grade grades = new Grade();
//        grades.setUserId(Integer.valueOf(userId));
//        grades.setCourseNumber(courseNumber);
//        Grade grade = gradeService.queryByIdAndNumber(grades);
//        if (grade != null) {
//            grade.setScore(0.0);
//            count = gradeService.updateById(grade);
//        }
        System.out.println("......................");
        int count = gradeService.updateById(grade, true);
        Map<String, Object> map = new HashMap<String, Object>();
        Course courses = new Course();
        courses.setCourseNumber(grade.getCourseNumber());
        Course course = courseService.queryByCondition(courses);
        map.put("count", count);
        map.put("courseName", course.getCourseName());
        map.put("courseNumber", course.getCourseNumber());
        return map;
    }
    
    /*
     * 这里新增本质上是修改成绩.
     * 根据用户的编号和课程名称进行查找
     * userId,用户的编号
     * score,输入的成绩
     * courseNumber,用户新增的课程编号
     */
    @ResponseBody
    @RequestMapping(value = "/saveById", method = RequestMethod.POST)
    public Map<String, Object> saveScoreByIdAndCourseNumber(Grade grade) {
//        if (userId == null || "".equals(userId)) {
//            return null;
//        }
//        if (courseNumber == null || "".equals(courseNumber)) {
//            return null;
//        }
//        Grade grades = new Grade();
//        grades.setUserId(Integer.valueOf(userId));
//        grades.setCourseNumber(courseNumber);
//        Grade grade = gradeService.queryByIdAndNumber(grades);
//        if (grade == null) {
//            return null;
//        }
//        if (grade.getScore() == null) { // 当该用户的这门课程分数不存在时
//            grade.setScore(0.0);
//        }
        Map<String, Object> map = new HashMap<String, Object>();
        Course course = new Course();
        course.setCourseNumber(grade.getCourseNumber());
        course = courseService.queryByCondition(course);
        int count = gradeService.saveScoreByIdAndCourseNumber(grade);
        User user = userService.queryById(String.valueOf(grade.getUserId()));
//        User user = userService.queryById(Integer.valueOf(userId));
//        if (user != null && !user.isIdentity()) {   // 如果该学生存在并且身份是学生
//            if (grade.getScore() == 0.0) {
//                grade.setUserId(Integer.valueOf(userId));
//                grade.setCourseNumber(courseNumber);
//                grade.setScore(Double.valueOf(score));
//                count = gradeService.updateById(grade);
//            }
//        }
//        Course course2 = new Course();
//        course2.setCourseNumber(courseNumber);
//        Course course = courseService.queryByCondition(course2);
        if (count != 0) {
            map.put("userName", user.getUserName());
            map.put("courseName", course.getCourseName());
        }
        map.put("count", count);
        return map;
    }
    
}
