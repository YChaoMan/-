package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * ��Controller����ҳ�����ת
 */
@Controller
@RequestMapping("/RedirectController")
public class RedirectController {

    @RequestMapping("/jspSendRedirect/{type}")
    public String jspSendRedirect(@PathVariable("type") String type) {
        if (type == null || "".equals(type)) {  // ���û�з��ص�ҳ��ʱһ������ô��ֱ�ӷ���
            return type;
        }
        String[] types = type.split("-");   // ������ڷָ���-����ָ��ȡ���¼�
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
