<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/grade/grade_add.css">
<title>成绩新增</title>
</head>
<body>
    <div class="box">
     <div class="header">
        <p>成绩新增</p>
      </div>
     <div class="section">
      <form id="form">
        <div>
          <label>用户编号: </label>
          <input type="text" name="userId" placeholder="这里输入用户编号" />
        </div>
        <div>
          <label>课程名称: </label>
          <select id="selectCourseName" name="courseNumber">
          </select>
        </div>
        <div>
          <label>成绩: </label>
          <input type="text" name="score" placeholder="这里输入成绩" />
        </div>
      </form>
      <div class="nav">
          <button class="btn btn-success" onclick="addGrade()">新 增</button>
          <button class="btn btn-warning" onclick="window.location.href = '<%= basePath %>/RedirectController/jspSendRedirect/manager'">返 回</button>
      </div>
     </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      $(function() {
    	  queryGrade();
      })
      
      function queryGrade() {
    	  var strHtml = "";
          $.ajax({
              url : "<%= basePath %>/CourseController/selectAll",
              type: "post",
              dataType: "json",
              success: function (result) {
            	  console.log(result);
                  $.each(result.courseList, function (ic, c) {
                	  strHtml += "<option value=\"" + c.courseNumber + "\">" + c.courseName + "</option>";
                  })
                  $("#selectCourseName").append(strHtml);
              },
              error : function (error) {
                  alert("数据走丢了~`");
                  window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/manager";
              }
          })
      }
      
      function addGrade() {
    	  var data = $("#form").serialize();
    	  console.log(data);
    	  $.ajax({
    		  data : data,
    		  type : "post",
    		  dataType: "json",
    		  url : "<%= basePath %>/GradeController/saveById",
    		  success : function (result) {
    			  if (result.count == 1) {
    				  alert(result.userName + "的<" + result.courseName + ">成绩新增成功~`");
                      window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/manager";
    			  } else {
    				  alert("新增失败，请核对信息再进行输入~`");
                      window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/user-student_query";
    			  }
    		  },
    		  error : function (error) {
    			  alert("新增失败，请核对信息再进行输入~");
    			  window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/user-student_query";
    		  }
    		  
    	  })
      }
    </script>
</body>
</html>