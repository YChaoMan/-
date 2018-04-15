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
        if (queryByCondition(courses) == null) {    // ����Ҳ��ÿγ̱����ô������
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
                    gradeDao.insert(maps);  // ����γ̵�ʱ��Ҫ�Գɼ������һ��
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
        courses.setCourseNumber(courseNumber);  // ��Ϊ�γ̱���ǲ��ɸı�����Ը��ݿγ̱��������֮ǰ����Ϣ
        Course courseBefore = queryByCondition(courses);    // ��ѯ��֮ǰ����Ϣ
        if (courseBefore != null) {
            if (courseName != null && !"".equals(courseName)) {
                courses.setCourseNumber(null);    // ��Ϊ�γ̱���ǲ����޸ģ�����ֻ��Ҫ��һ��������ȥ����
                courses.setCourseName(courseName);
                if (queryByCondition(courses) == null) {    // ����ÿγ����Ʋ�����
                    courseBefore.setCourseNumber(courseNumber);
                    courseBefore.setCourseName(courseName);
                    count = this.courseDao.updateByNumber(courseBefore);
                }
            }
        }
        return count;
    }

    /*
     * �÷�����ѯֻ�ܴ�һ���������γ̱�� | �γ�����
     */
    public Course queryByCondition(Course course) {
        String courseNumber = course.getCourseNumber();
        String courseName = course.getCourseName();
        // �������һ����Ϊ����ô��Ϊ��
        boolean courseNoNull = (courseNumber != null || courseName != null);
        boolean courseNotEmpty = (!"".equals(courseNumber) || !"".equals(courseNumber));
        if (!courseNoNull || !courseNotEmpty) { // ������������������
            return null;
        }
        return this.courseDao.queryByCondition(course);
    }

}
