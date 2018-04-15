<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/course/course_edit.css">
<title>课程修改</title>
</head>
<body>
    <div class="box">
      <div class="header">
        <p>课程修改</p>
      </div>
      <div class="section">
        <form id="form">
          <div><label>课程编号: </label><br /><input type="text" name="courseNumber" value="${course.courseNumber }" readonly="readonly" /></div>
          <div><label>课程名称: </label><br /><input type="text" name="courseName" value="${course.courseName }" />&nbsp;&nbsp;<span class="glyphicon glyphicon-edit"></span>此处填写</div>
        </form>
      </div>
      <div class="nav">
        <button class="btn btn-success" onclick="editCourse()" >确 定</button>
        <button class="btn btn-warning" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/course-course'">返 回</button>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      function editCourse() {
    	  var data = $("#form").serialize();
    	  $.ajax({
              data : data,
              type : "post",
              dataType: "json",
              url : "<%= basePath %>/CourseController/updateByCourseNumber",
              success : function (result) {
            	  if (result.count == 1) {
                      alert("更新课程<" + result.courseName + ">成功")
                      window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
            	  } else {
            		  alert("修改失败~`");
                      window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
            	  }
              },
              error : function (error) {
            	  alert("修改失败~`");
            	  window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
              }
    	  })
      }
    </script>
</body>
</html>