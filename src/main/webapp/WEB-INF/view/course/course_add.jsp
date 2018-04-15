<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/course/course_add.css">
<title>课程新增</title>
</head>
<body>
    <div class="box">
      <div class="header">
        <p>课程新增</p>
      </div>
      <div class="section">
        <form id="form">
          <div><label>课程编号: </label><br /><input type="text" name="courseNumber" placeholder="这里输入课程编号"/></div>
          <div><label>课程名称: </label><br /><input type="text" name="courseName" placeholder="这里输入课程名称"/></div>
        </form>
      </div>
      <div class="nav">
        <button onclick="addCourse()" class="btn btn-success">确 定</button>
        <button onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/course-course'" class="btn btn-warning">返 回</button>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      function addCourse() {
    	  var data = $("#form").serialize();
          $.ajax({
              url : "<%= basePath %>/CourseController/saveCourse",
              data : data,
              type : "post",
              dataType: "json",
              success : function (result) {
            	  if (result.count > 0) {
            		  alert("新增课程< " + result.courseName + " >成功~`");
                      window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
            	  } else {
            		  alert("新增课程失败~`");
                      window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
            	  }
              },
              error : function (error) {
              }
          })
      }
    </script>
</body>
</html>