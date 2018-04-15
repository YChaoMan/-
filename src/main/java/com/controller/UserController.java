package com.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entity.Course;
import com.entity.Grade;
import com.entity.Page;
import com.entity.User;
import com.service.CourseService;
import com.service.GradeService;
import com.service.UserService;

@Controller
@RequestMapping("/UserController")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private GradeService gradeService;
    
    /*
     * ȫ����ѯ
     * type������ķ�ʽ��identity���û���ݣ�courseNumber�ǿγ̱��
     */
    @ResponseBody
    @RequestMapping(value="/selectAll", method = RequestMethod.POST)
    public Map<String, Object> selectAll(Page page, String type, String identity, String courseNumber, String condition) {
        int pageSize = 10;
        int totalRow = 0;
        page.setPageSize(pageSize); // ������ʾ��¼��
        
        if (identity == null || "".equals(identity) || "undefined".equals(identity)) {
            identity = "3"; // ���ֲ�ѯ��״̬��Ĭ��Ϊ3�����մ���ʱ���������
        }
        int currentPage = page.getCurrentPage();
        if (currentPage > page.getTotalPage()) {    // �����һҳ������ҳ��
            page.setCurrentPage(page.getTotalPage());
        } else if (currentPage <= 0) { // �����ǰҳ��С�ڵ���0�����Ϊ1
            page.setCurrentPage(1); // ���õ�ǰҳ��
        }
        page.setStartRow((page.getCurrentPage() - 1) * page.getPageSize());   // ���õ�ǰ��¼��
        
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("page", page);
        queryMap.put("identity", identity);
        queryMap.put("condition", condition);
        List<User> userList = userService.queryAll(queryMap);
        if ("3".equals(identity)) { // �����Ĭ�ϵ��������
            totalRow = userService.getTotalRow(); // ��ȡ�ܼ�¼��
        } else {    // ���ѡ���������ѧ��/��ʦ
            totalRow = userList.size(); // ��ȡ�ܼ�¼��
        }
        page.setTotalRow(totalRow); // �����ܼ�¼��
        
        int totalPage = (totalRow % page.getPageSize() == 0)? 1 : totalRow / page.getPageSize() + 1;
        page.setTotalPage(totalPage);   // ������ҳ��
        
        List<Grade> gradeList = gradeService.queryAll();
        List<Course> courseList = courseService.queryAll();
        List<User> definedList = userService.queryByUserInnerGradeInnerCourse(type, courseNumber);

        Map<String, Object> map = new HashMap<String, Object>();
        if (userList != null && userList.size() > 0) {
            map.put("userList", userList);
        }
        if (gradeList != null && gradeList.size() > 0) {
            map.put("gradeList", gradeList);
        }
        if (courseList != null && courseList.size() > 0) {
            map.put("courseList", courseList);
        }
        if (definedList != null && definedList.size() > 0) {
            map.put("definedList", definedList);
        }
        if (page != null) {
            map.put("page", page);
        }
        
        return map;
    }
    
    /*
     * ���ݱ�Ų��ҵ�ǰ�û�
     */
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    public String queryById(HttpServletRequest request, String userId) {
        User user = userService.queryById(userId);
        if (user != null) {
            request.setAttribute("user", user);
            return "user/student_edit";
        }
        return null;
    }
    
    /*
     * �����û��ı�Ž����޸ĸ�����Ϣ
     */
    @ResponseBody
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public Map<String, Object> updateById(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        User users = userService.queryById(String.valueOf(user.getUserId()));
        map.put("userName", users.getUserName());
        int count = userService.updateById(user, true);
        map.put("count", count);
        return map;
    }
    
    /*
     * �����û��ı�Ž���ɾ���û�
     */
    @ResponseBody
    @RequestMapping(value = "/delById", method = RequestMethod.POST)
    public Map<String, Object> delById(String userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.queryById(userId);
        if (user != null) {
            map.put("userName", user.getUserName());
        }
        int count = userService.delById(Integer.valueOf(userId));
        map.put("count", count);
        return map;
    }
    
    /*
     * �����û�
     */
    @ResponseBody
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public Map<String, Object> saveUser(User user) {
//        int count = 0;
//        String introduction = user.getIntroduction();
//        if (introduction == null) { // ������˽���Ϊ�յĻ�
//            user.setIntroduction("");
//        }
//        user.setCreateTime(new Timestamp(new Date().getTime()));
//        User userByGrade = userService.queryByName(user.getUserName()); // �Ƿ���ڸ�������
//        if (userByGrade == null) {
//            userService.insert(user);
//            List<Course> courseList = courseService.queryAll();
//            if (!user.isIdentity()) {   // �����������ݲ�����ʦ����ô��ֱ���������û��ĳɼ�
//                user = userService.queryByName(user.getUserName());
//                Map<String, Object> map2 = new HashMap<String, Object>();
//                map2.put("userId", user.getUserId());
//                map2.put("courseList", courseList);
//                gradeService.insert(map2);
//                count++;
//            }
//        }
        int count = userService.insert(user);
        Map<String, Object> map = new HashMap<String, Object>();
        if (user.getUserName() != null && !"".equals(user.getUserName())) {
            map.put("userName", user.getUserName());
        }
        map.put("count", count);
        return map;
    }
}
