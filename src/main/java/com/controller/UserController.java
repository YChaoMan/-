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
     * 全部查询
     * type是排序的方式，identity是用户身份，courseNumber是课程编号
     */
    @ResponseBody
    @RequestMapping(value="/selectAll", method = RequestMethod.POST)
    public Map<String, Object> selectAll(Page page, String type, String identity, String courseNumber, String condition) {
        int pageSize = 10;
        int totalRow = 0;
        page.setPageSize(pageSize); // 设置显示记录数
        
        if (identity == null || "".equals(identity) || "undefined".equals(identity)) {
            identity = "3"; // 保持查询的状态，默认为3。按照创建时间进行排序
        }
        int currentPage = page.getCurrentPage();
        if (currentPage > page.getTotalPage()) {    // 如果下一页大于总页数
            page.setCurrentPage(page.getTotalPage());
        } else if (currentPage <= 0) { // 如果当前页低小于等于0，如果为1
            page.setCurrentPage(1); // 设置当前页数
        }
        page.setStartRow((page.getCurrentPage() - 1) * page.getPageSize());   // 设置当前记录数
        
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("page", page);
        queryMap.put("identity", identity);
        queryMap.put("condition", condition);
        List<User> userList = userService.queryAll(queryMap);
        if ("3".equals(identity)) { // 如果是默认的身份排序
            totalRow = userService.getTotalRow(); // 获取总记录数
        } else {    // 如果选择了身份是学生/老师
            totalRow = userList.size(); // 获取总记录数
        }
        page.setTotalRow(totalRow); // 设置总记录数
        
        int totalPage = (totalRow % page.getPageSize() == 0)? 1 : totalRow / page.getPageSize() + 1;
        page.setTotalPage(totalPage);   // 设置总页数
        
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
     * 根据编号查找当前用户
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
     * 根据用户的编号进行修改个人信息
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
     * 根据用户的编号进行删除用户
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
     * 新增用户
     */
    @ResponseBody
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public Map<String, Object> saveUser(User user) {
        int count = userService.insert(user);
        Map<String, Object> map = new HashMap<String, Object>();
        if (user.getUserName() != null && !"".equals(user.getUserName())) {
            map.put("userName", user.getUserName());
        }
        map.put("count", count);
        return map;
    }
}
