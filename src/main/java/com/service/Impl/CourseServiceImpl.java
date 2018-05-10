package com.service.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CourseDao;
import com.dao.GradeDao;
import com.dao.UserDao;
import com.entity.Course;
import com.entity.User;
import com.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
    
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private UserDao userDao;

    public int insert(Course course) {
        String courseNumber = course.getCourseNumber();
        String courseName = course.getCourseName();
        int count = 0;
        if (courseNumber == null || "".equals(courseNumber)) {
            return 0;
        }
        if (courseName == null || "".equals(courseName)) {
            return 0;
        }
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);
        if (queryByCondition(courses) == null) {    // 如果找不该课程编号那么就新增
            this.courseDao.insert(course);
            count++;
            List<Course> courseList = new ArrayList<Course>();
            courseList.add(courses);
            Map<String, Object> queryMap = new HashMap<>();
            List<User> userList = userDao.queryAll(queryMap);
            if (userList != null && userList.size() > 0) {
                for (User user : userList) {
                    Map<String, Object> maps = new HashMap<String, Object>();
                    maps.put("userId", user.getUserId());
                    maps.put("courseList", courseList);
                    gradeDao.insert(maps);  // 插入课程的时候要对成绩表更新一次
                }
            }
        }
        return count;
    }

    public List<Course> queryAll() {
        return this.courseDao.queryAll();
    }

    public int delByNumber(String courseNumber) {
        int count = 0;
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);
        Course course = queryByCondition(courses);
        if (course != null) {
            gradeDao.delByCourseNumber(courseNumber);
            count = this.courseDao.delByNumber(course);
        }
        return count;
    }

    public int updateByNumber(Course course) {
        int count = 0;
        String courseName = course.getCourseName();
        String courseNumber = course.getCourseNumber();
        if (courseNumber == null || "".equals(courseNumber)) {
            return 0;
        }
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);  // 因为课程编号是不可改变的所以根据课程编号来查找之前的信息
        Course courseBefore = queryByCondition(courses);    // 查询出之前的信息
        if (courseBefore != null) {
            if (courseName != null && !"".equals(courseName)) {
                courses.setCourseNumber(null);    // 因为课程编号是不能修改，所以只需要穿一个参数过去就行
                courses.setCourseName(courseName);
                if (queryByCondition(courses) == null) {    // 如果该课程名称不存在
                    courseBefore.setCourseNumber(courseNumber);
                    courseBefore.setCourseName(courseName);
                    count = this.courseDao.updateByNumber(courseBefore);
                }
            }
        }
        return count;
    }

    /*
     * 该方法查询只能传一个条件，课程编号 | 课程名称
     */
    public Course queryByCondition(Course course) {
        String courseNumber = course.getCourseNumber();
        String courseName = course.getCourseName();
        // 如果其中一个不为空那么就为真
        boolean courseNoNull = (courseNumber != null || courseName != null);
        boolean courseNotEmpty = (!"".equals(courseNumber) || !"".equals(courseNumber));
        if (!courseNoNull || !courseNotEmpty) { // 如果上面的条件不满足
            return null;
        }
        return this.courseDao.queryByCondition(course);
    }

}
