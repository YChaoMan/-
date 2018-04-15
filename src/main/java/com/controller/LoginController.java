package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.entity.User;
import com.service.UserService;

@RequestMapping(value="/LoginController")
@Controller
public class LoginController {
    
    @Autowired
    private UserService userService;

    /*
     * 一个登录Controller，根据登录时的用户名和编号来进行判断。
     * 如果是老师就直接跳到manager.jsp
     * 如果是学生直接跳到StudentController
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, String userName, String password) {
        Map<String, Object> mapStr = new HashMap<String, Object>();
        String url = null;
        String jsonString = null;
        int count = 0;
//        User user = null;
//        
//        boolean usernameExists = (userName != null && !"".equals(userName)); 
//        boolean passwordExists = (password != null && !"".equals(password));
//        if (usernameExists && passwordExists) {
//            User users = new User();
//            users.setUserId(Integer.valueOf(password));
//            users.setUserName(userName);
//            user = userService.queryByIdAndName(users);
//        }
        User user = userService.queryByIdAndName(userName, password);
        if (user != null) {
            if (user.isIdentity()) {   // 如果是老师
                url = request.getContextPath() + "/RedirectController/jspSendRedirect";
                count = 1;
            } else {
                url = request.getContextPath() + "/StudentController/studentLogin";
            }
            mapStr.put("count", count);
            mapStr.put("url", url);
            mapStr.put("user", user);
            request.getSession().setAttribute("userName", user.getUserName());
            jsonString = JSON.toJSONString(mapStr);
        }
        return jsonString;
    }
    
    
    
}
