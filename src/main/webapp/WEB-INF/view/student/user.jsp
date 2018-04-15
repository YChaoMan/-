<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/user/user.css">
<title>信息预览</title>
</head>
<body>
    <h1>用户信息预览</h1>
    <div class="line"></div>
    <div class="box panel panel-default">
      <div class="panel-heading">
        <label>用户名:<span>${user.userName }</span></label>&nbsp;&nbsp;
        <label>编号:<span>${user.userId}</span></label> &nbsp;&nbsp;
        <div class="message">
          <label>性别:<span>${user.sex == 1 ? "男" : "女"}</span></label> &nbsp;&nbsp;
          <input type="hidden" name="bir" value="${user.birthday}" />
          <label>出生年月:<span><input type="text" name="birthday" readonly="readonly" value=""></span></label>&nbsp;&nbsp;
          <label>个人介绍:<span>${user.introduction }</span></label>
        </div>&nbsp;
        <input type="button" name="more" value="更  多" />
      </div>
    <table cellpadding="0" cellspacing="0" class="panel-body">
      <thead>
        <tr>
          <td class="tdStyle">课程名称</td>
         <c:forEach var="gradeList" items="${gradeList }">
          <td style="background:#F5FFFA;">
            <c:forEach var="courseList" items="${courseList }">
              <c:if test="${gradeList.courseNumber eq courseList.courseNumber}">
                ${courseList.courseName }
              </c:if>
            </c:forEach>
          </td>
         </c:forEach>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td class="tdStyle">所得分数</td>
          <c:forEach var="gradeList" items="${gradeList }">
          <td style="font-weight:bold;background:#F5FFFA;font-size:17px;">
            <c:forEach var="courseList" items="${courseList }">
              <c:if test="${gradeList.courseNumber eq courseList.courseNumber}">
                ${gradeList.score }
              </c:if>
            </c:forEach>
          </td>
          </c:forEach>
        </tr>
      </tbody>
    </table>
    <div class="footnav">
      <label><input class="btn btn-primary btn-lg active" type="button" onclick="window.location.href='<%= basePath %>/StudentController/selectStudentById?userId=${user.userId}'" value="修改" /></label>&nbsp;&nbsp;
      <label><button class="btn btn-default btn-lg active" onclick="window.location.href='<%= basePath %>'">退出</button></label>
    </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
    var show = false;   // false隐藏
    $(document).ready(function () {
    	$(function () {
            $(".message").hide();
        })
        $("input[name='more']").click(function () {
            if (!show) {
                $(".message").show();
                $("input[name='more']").val("隐藏");
                show = true;
            } else {
                $(".message").hide();
                show = false;
                $("input[name='more']").val("更多");
            }
        });
    });
    </script>
    
    <script type="text/javascript">
    $(function () {
      var date = $("input[name='bir']").val();
      $("input[name='birthday']").val(new Date(date).Format("yyyy-MM-dd"));
    })
    </script>
    
    <script type="text/javascript">
        // 时间格式处理
        Date.prototype.Format = function (fmt) {
            var o = {   
                    "M+" : this.getMonth() + 1,                 //月份   
                    "d+" : this.getDate(),                    //日   
                    "H+" : this.getHours(),                   //小时   
                    "m+" : this.getMinutes(),                 //分   
                    "s+" : this.getSeconds()                 //秒   
                  };   
                  if(/(y+)/.test(fmt))
                    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
                  for(var k in o)
                    if(new RegExp("("+ k +")").test(fmt)) 
                  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
                  return fmt;
        }
    </script>
</body>
</html>