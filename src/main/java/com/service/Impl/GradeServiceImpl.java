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
     * ���ݴ����Ķ�����и��²�����
     */
    public int updateById(Grade grade, boolean del) {
        if (grade == null) {
            return 0;
        }
        // ȡ�����������ֵ
        Integer userId = grade.getUserId();
        String courseNumber = grade.getCourseNumber();
        Double score = grade.getScore();
        if (userId == null || "".equals(userId)) {
            return 0;
        }
        if (courseNumber == null || "".equals(courseNumber)) {
            return 0;
        }
        if (!del) { // �������ɾ�����������жϷ�����ȡֵ
            if (score == null || "".equals(score)) {
                return 0;
            }
        }
        System.out.println("......................");
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);
        Course course = courseDao.queryByCondition(courses);    // ��ѯ�ÿγ��Ƿ�Ҳ���ڿγ̱���
        if (course == null) { // ����ڿγ̱����Ҳ����ÿγ�
            return 0;
        }
        Grade grades = new Grade(); // ����һ����������
        grades.setUserId(userId);
        grades.setCourseNumber(courseNumber);
        Grade grade2 = queryByIdAndNumber(grades);  // ����ȡ��������ֵ��ѯ�޸�ǰ�Ķ���
        System.out.println("....................." + grade2);
        if (grade2 == null) {   // ����Ҳ�����ǰѧ���ĵ�ǰ�γ�
            return 0;
        }
        if (del) {  // ɾ���ɼ�ʱdelΪtrue
            grade2.setScore(0.0);
        } else {
            grade2.setScore(score); // �����޸�
        }
        return this.gradeDao.updateById(grade2);
    }

    public List<Grade> queryById(String userId) {
        if (userId == null || "".equals(userId)) {
            System.out.println("�Ƿ�����!");
            return null;
        }
        return this.gradeDao.queryById(Integer.valueOf(userId));
    }

    public Grade queryByIdAndNumber(Grade grade) {
        Integer userId = grade.getUserId();    //��ȡ�������е�����ֵ
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
     * �����������������޸ĳɼ�.
     * �����û��ı�źͿγ����ƽ��в���
     */
    public int saveScoreByIdAndCourseNumber(Grade grade) {
        int count = 0;
        Grade grade2 = queryByIdAndNumber(grade);
        if (grade2 == null) {
            return 0;
        }
        if (grade2.getScore() != 0.0) { // �����û������ſγ̷����Ѿ����ڣ���ô��������
            return 0;
        }
        User user = userDao.queryById(grade2.getUserId());
        if (user != null && !user.isIdentity()) {   // �����ѧ�����ڲ��������ѧ��
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
