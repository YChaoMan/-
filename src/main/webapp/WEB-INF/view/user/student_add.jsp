<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/student/student_add.css">
<title>在线新增</title>
</head>
<body>
    <div class="box"> 
      <div class="header">
        <p>在线新增</p>
      </div>
      <div class="section">
        <form id="form">
          <div>
            <span>&lowast;</span><label>用户名: </label>
            <input type="text" name="userName" value=""/>
          </div>
          <div><span>&lowast;</span><label>性别: </label>
            <input type="radio" name="sex" value="1" id="1" /><label for="1"> 男</label>&nbsp;&nbsp;
            <input type="radio" name="sex" value="0" id="0" /><label for="0"> 女</label></div>
          <div><span>&lowast;</span><label>身份: </label>
            <input type="radio" name="identity" value="1" id="01" /><label for="01"> 职工</label>&nbsp;&nbsp;
            <input type="radio" name="identity" value="0" id="00" /><label for="00"> 学生</label></div>
          <div><span>&lowast;</span><label>出生日期: </label>
            <input type="date" name="birthday" />
          </div>
          <div><label>个人介绍: </label>
            <input type="text" name="introduction" placeholder="来自英雄的简介"/>
          </div>
        </form>
        <div class="nav">
          <button class="btn btn-success" onclick="insert()" >新增</button>
          <button class="btn btn-warning" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/user-student_query'">返回</button>
        </div>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      function insert() {
    	  var data = $("#form").serialize();
    	  $.ajax ({
    		 url : "<%= basePath %>/UserController/saveUser",
    		 data: data,
    		 type : "post",
    		 dataType: "json",
    		 success : function (result) {
    			 alert("result >> " + result)
    			 if (result.count == 1) {
    				 alert("新增用户<" + result.userName + ">成功~`");
    				 window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/user-student_query";
    			 } else {
    				 alert("新增用户<" + result.userName + ">失败");
    				 return false;
    			 }
    			 
    		 },
    		 error : function (error) {
    			 alert("新增用户失败~`");
    			 return false;
    		 }
    	  });
      }
    </script>
</body>
</html>