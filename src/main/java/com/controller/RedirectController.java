package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 该Controller用于页面的中转
 */
@Controller
@RequestMapping("/RedirectController")
public class RedirectController {

    @RequestMapping("/jspSendRedirect/{type}")
    public String jspSendRedirect(@PathVariable("type") String type) {
        if (type == null || "".equals(type)) {  // 如果没有返回的页面时一级，那么就直接返回
            return type;
        }
        String[] types = type.split("-");   // 如果存在分隔符-，则分割，获取上下级
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < types.length; i++) {
            if (i != 0) {
                sb.append("/");
            }
            sb.append(types[i]);
        }
        return sb.toString();
    }
}
