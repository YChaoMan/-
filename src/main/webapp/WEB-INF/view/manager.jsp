<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/manager.css">
<title>管理用户</title>
</head>
<body>
    <h2><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;<%= session.getAttribute("userName")%></h2>
    <hr />
    <div class="box">
      <div class="aside">
        <button class="btn btn-default" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/user-student_query'">用户管理</button>
        <button class="btn btn-success" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/course-course'">课程管理</button>
        <button class="btn btn-primary" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/grade-grade_add'">新增成绩</button>
        <button class="btn btn-warning"  onclick="window.location.href='<%= basePath %>'">退&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出</button>
      </div>
      <div class="section">
        <div class="order">
          <label>成 绩 排 序 >> </label>
          <input type="radio" name="order" value="0" id="0" onclick="temp('0')"><label for="0">降序(从高到低)</label>
          <input type="radio" name="order" value="1" id="1" onclick="temp('1')"><label for="1">升序(从低到高)</label>
          <input type="radio" name="order" value="3" id="3" onclick="temp('3')" checked="checked"><label for="3">默认</label>
        </div>
        <table cellpadding="0" cellspacing="0" >
          <thead>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
    $(document).ready(
        $(function () {
            queryAll("", "3");
        })
    );
//     function () {
//         function jumpWindow(count) {
//             if (count == 1) {
//                 alert(count);
//                 queryAll(count);
//             } else if (count == 2){
//                 queryAll(count);
//             }
//          }
      
      // 保存升降序的状态
      function temp(temp) {
    	  if ("0" === temp) {
    		  queryAll("KC00","0");
    	  } else if ("3" === temp){
    		  queryAll("","3");
    	  } else {
    		  queryAll("KC00","1");
    	  }
      }
      
      // 查询全部有成绩的学生
      function queryAll(courseNumber, type) {
    	  var sum = 0; // 求总成绩
    	  var tbodyHtml = "";  // 拼接在tbody的字符串
    	  var theadHtml = "";  // 拼接在thead的字符串
    	  var count = 0;
    	  if (type == undefined) {
    		  type = null;
    	  }
    	  $("thead").html('');
          $("tbody").html('');
    	  $.ajax({
    		  url : "<%=basePath %>/UserController/selectAll?courseNumber=" + courseNumber + "&type=" + type,
    	      type : "post",
    		  dataType: "json",
    		  success: function (result) {
//     			  console.log(result);
    			  console.log(result.definedList)
    			  theadHtml += "<tr><td>编号</td><td>用户编号</td><td>姓名</td>";
    			  $.each(result.courseList, function (ic, c) {   // 拼接thead
    				  if (c.courseName != null) {
    					  theadHtml += "<td onclick=\"queryAll('"+ c.courseNumber + "','" + type + "')\"> " + c.courseName + "</td>";
    				  } 
    			  })
    			  theadHtml += "<td onclick=\"queryAll('sum','" + type + "')\">总成绩</td><td>操作</td>";
    			  theadHtml += "</tr>";
    			  $("thead").append(theadHtml);
    			  
    			  $.each(result.definedList, function (iu, u) {  // 拼接tbody
    				  console.log(result);
    				  tbodyHtml += "<tr><td>" + (++count) + "</td><td>" + u.userId + "</td><td>" + u.userName + "</td>";
    				  $.each(result.courseList, function (ic, c) {
    					  $.each(result.gradeList, function (ig, g) {
    						  if (g.userId == u.userId) {
    							  if (c.courseNumber == g.courseNumber) {
    								  if (g.score == 0) {
    									  tbodyHtml += "<td> - </td>";
    								  } else {
    									  tbodyHtml += "<td>" + g.score + "</td>";
                                          sum += g.score;
    								  }
                                  }
                              }
                          })
    				  })
    				  tbodyHtml += "<td>" + sum + "</td><td><button onclick=\'jump(\"" + u.userId + "\")'>成绩管理</button></td></tr>";
                      sum = 0;
    			  })
    			  $("tbody").append(tbodyHtml);
    			  
    		  },
    		  error : function (error) {
    			  alert("数据走丢了~`");
    			  window.location.href="<%= basePath %>";
    		  }
    	  })
      }
      
      // 成绩管理跳转
      function jump (userId) {
    	  window.location.href = "<%= basePath %>/GradeController/queryById?userId=" + userId;
      }
    </script>
</body>
</html>