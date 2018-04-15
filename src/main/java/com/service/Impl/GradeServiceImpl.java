package com.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CourseDao;
import com.dao.GradeDao;
import com.dao.UserDao;
import com.entity.Course;
import com.entity.Grade;
import com.entity.User;
import com.service.GradeService;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;

    public List<Grade> queryAll() {
        return this.gradeDao.queryAll();
    }

    public void insert(Map<String, Object> map) {
        this.gradeDao.insert(map);
    }

    public int delByCourseNumber(String courseNumber) {
        if (courseNumber == null || "".equals(courseNumber)) {
            return 0;
        }
        return this.gradeDao.delByCourseNumber(courseNumber);
    }

    public int delById(Integer userId) {
        return this.gradeDao.delById(userId);
    }

    /*
     * 根据传进的对象进行跟新操作。
     */
    public int updateById(Grade grade, boolean del) {
        if (grade == null) {
            return 0;
        }
        // 取出对象的属性值
        Integer userId = grade.getUserId();
        String courseNumber = grade.getCourseNumber();
        Double score = grade.getScore();
        if (userId == null || "".equals(userId)) {
            return 0;
        }
        if (courseNumber == null || "".equals(courseNumber)) {
            return 0;
        }
        if (!del) { // 如果不是删除操作，则不判断分数的取值
            if (score == null || "".equals(score)) {
                return 0;
            }
        }
        System.out.println("......................");
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);
        Course course = courseDao.queryByCondition(courses);    // 查询该课程是否也存在课程表中
        if (course == null) { // 如果在课程表中找不到该课程
            return 0;
        }
        Grade grades = new Grade(); // 创建一个参数载体
        grades.setUserId(userId);
        grades.setCourseNumber(courseNumber);
        Grade grade2 = queryByIdAndNumber(grades);  // 根据取出的属性值查询修改前的对象
        System.out.println("....................." + grade2);
        if (grade2 == null) {   // 如果找不到当前学生的当前课程
            return 0;
        }
        if (del) {  // 删除成绩时del为true
            grade2.setScore(0.0);
        } else {
            grade2.setScore(score); // 进行修改
        }
        return this.gradeDao.updateById(grade2);
    }

    public List<Grade> queryById(String userId) {
        if (userId == null || "".equals(userId)) {
            System.out.println("非法输入!");
            return null;
        }
        return this.gradeDao.queryById(Integer.valueOf(userId));
    }

    public Grade queryByIdAndNumber(Grade grade) {
        Integer userId = grade.getUserId();    //　取出对象中的属性值
        String courseNumber = grade.getCourseNumber();
        if (userId == null || "".equals(userId)) {
            return null;
        }
        if (courseNumber == null || "".equals(courseNumber)) {
            return null;
        }
        return this.gradeDao.queryByIdAndNumber(grade);
    }
    
    /*
     * 这里新增本质上是修改成绩.
     * 根据用户的编号和课程名称进行查找
     */
    public int saveScoreByIdAndCourseNumber(Grade grade) {
        int count = 0;
        Grade grade2 = queryByIdAndNumber(grade);
        if (grade2 == null) {
            return 0;
        }
        if (grade2.getScore() != 0.0) { // 当该用户的这门课程分数已经存在，那么不能新增
            return 0;
        }
        User user = userDao.queryById(grade2.getUserId());
        if (user != null && !user.isIdentity()) {   // 如果该学生存在并且身份是学生
            if (grade2.getScore() == 0.0) {
                grade2.setUserId(grade2.getUserId());
                grade2.setCourseNumber(grade2.getCourseNumber());
                grade2.setScore(grade.getScore());
                count = updateById(grade2, false);
            }
        }
        return count;
    }

}
