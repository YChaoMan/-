package com.service.Impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.CourseDao;
import com.dao.GradeDao;
import com.dao.UserAddDao;
import com.dao.UserDao;
import com.entity.Course;
import com.entity.Grade;
import com.entity.User;
import com.entity.UserAdd;
import com.service.GradeService;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAddDao userAddDao;

    public List<Grade> queryAll() {
        List<Grade> gradeList = this.gradeDao.queryAll();
        Iterator<Grade> gradeIter = this.gradeDao.queryByGroup().iterator();
        while (gradeIter.hasNext()) {
            int count = this.courseDao.queryAll().size();
            List<Grade> gradesList = this.gradeDao.queryById(gradeIter.next().getUserId());
            Iterator<Grade> gradesIter = gradesList.iterator();
            while (gradesIter.hasNext()) {
                Grade grade = gradesIter.next();
                if (grade.getScore() == 0.0) {
                    count--;
                }
                if (count <= 0) {
                    this.userAddDao.updateByUserAddId2(grade.getUserId());
                }
            }
        }
        return gradeList;
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
     * 鏍规嵁浼犺繘鐨勫璞¤繘琛岃窡鏂版搷浣溿��
     */
    public int updateById(Grade grade, boolean del) {
        if (grade == null) {
            return 0;
        }
        // 鍙栧嚭瀵硅薄鐨勫睘鎬у��
        Integer userId = grade.getUserId();
        String courseNumber = grade.getCourseNumber();
        Double score = grade.getScore();
        if (userId == null || "".equals(userId)) {
            return 0;
        }
        if (courseNumber == null || "".equals(courseNumber)) {
            return 0;
        }
        if (!del) { // 濡傛灉涓嶆槸鍒犻櫎鎿嶄綔锛屽垯涓嶅垽鏂垎鏁扮殑鍙栧��
            if (score == null || "".equals(score)) {
                return 0;
            }
        }
        System.out.println("......................");
        Course courses = new Course();
        courses.setCourseNumber(courseNumber);
        Course course = courseDao.queryByCondition(courses);    // 鏌ヨ璇ヨ绋嬫槸鍚︿篃瀛樺湪璇剧▼琛ㄤ腑
        if (course == null) { // 濡傛灉鍦ㄨ绋嬭〃涓壘涓嶅埌璇ヨ绋�
            return 0;
        }
        UserAdd userAdd = userAddDao.selectByUserId(grade.getUserId());
        if (userAdd != null) {
            userAddDao.updateByUserAddId(grade.getUserId());
        }
        Grade grades = new Grade(); // 鍒涘缓涓�涓弬鏁拌浇浣�
        grades.setUserId(userId);
        grades.setCourseNumber(courseNumber);
        Grade grade2 = queryByIdAndNumber(grades);  // 鏍规嵁鍙栧嚭鐨勫睘鎬у�兼煡璇慨鏀瑰墠鐨勫璞�
        System.out.println("....................." + grade2);
        if (grade2 == null) {   // 濡傛灉鎵句笉鍒板綋鍓嶅鐢熺殑褰撳墠璇剧▼
            return 0;
        }
        if (del) {  // 鍒犻櫎鎴愮哗鏃禿el涓簍rue
            grade2.setScore(0.0);
        } else {
            grade2.setScore(score); // 杩涜淇敼
        }
        return this.gradeDao.updateById(grade2);
    }

    public List<Grade> queryById(String userId) {
        if (userId == null || "".equals(userId)) {
            System.out.println("闈炴硶杈撳叆!");
            return null;
        }
        return this.gradeDao.queryById(Integer.valueOf(userId));
    }

    public Grade queryByIdAndNumber(Grade grade) {
        Integer userId = grade.getUserId();    //銆�鍙栧嚭瀵硅薄涓殑灞炴�у��
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
     * 杩欓噷鏂板鏈川涓婃槸淇敼鎴愮哗.
     * 鏍规嵁鐢ㄦ埛鐨勭紪鍙峰拰璇剧▼鍚嶇О杩涜鏌ユ壘
     */
    public int saveScoreByIdAndCourseNumber(Grade grade) {
        int count = 0;
        Grade grade2 = queryByIdAndNumber(grade);
        if (grade2 == null) {
            return 0;
        }
        if (grade2.getScore() != 0.0) { // 褰撹鐢ㄦ埛鐨勮繖闂ㄨ绋嬪垎鏁板凡缁忓瓨鍦紝閭ｄ箞涓嶈兘鏂板
            return 0;
        }
        User user = userDao.queryById(grade2.getUserId());
        if (user != null && !user.isIdentity()) {   // 濡傛灉璇ュ鐢熷瓨鍦ㄥ苟涓旇韩浠芥槸瀛︾敓
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
