<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/course/course.css">
<title>课程管理</title>
</head>
<body>
    <div class="header">
      <h2>课程管理</h2>
      <hr />
    </div>
    <div class="box">
      <div class="nav">
        <button onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/course-course_add'" class="btn btn-success">新 增</button>
        <button onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/manager'" class="btn btn-warning">返 回</button>
      </div>
      <div class="section">
        <table cellpadding="0" cellspacing="0" >
          <thead style="background: #C1CDCD;">
            <tr>
              <td>课程编号</td>
              <td>课程名称</td>
              <td>可行/操作</td>
            </tr>
          </thead>
          <tbody id="tbody"></tbody>
        </table>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      $(function ()  {
    	  queryAllCourse();
      })
      
      function queryAllCourse() {
    	  var strHtml = "";
    	  $.ajax({
    		  type : "post",
    		  dataType : "json",
    		  url : "<%= basePath %>/CourseController/selectAll",
    		  success : function (result) {
    			  $.each(result.courseList, function (ic, c) {
    				  strHtml = "<tr><td>" + c.courseNumber + "</td><td>" + c.courseName + "</td>"
    				      + "<td><button onclick=\"window.location.href='<%= basePath %>/CourseController/selectByCourseNumber?courseNumber=" + c.courseNumber + "'\"><span class=\"glyphicon glyphicon-cog\"></span>"
    				      + "</button> / <button onclick='delCourse(\""+ c.courseNumber +"\")'><span class=\"glyphicon glyphicon-trash\"></span></button>"
    				  $("#tbody").append(strHtml);
    			  });
    		  },
    		  error : function (error) {
    			  alert("数据走丢~`");
    		  }
    	  })
      }
      
      // 删除课程
      function delCourse(courseNumber) {
    	  var del = confirm("确定删除该课程吗?");
    	  var data = ({
    		  courseNumber : courseNumber
    	  })
          if (del) {
              $.ajax({
                  data : data,
                  url : "<%= basePath %>/CourseController/delByNumber",
                  type : "post",
                  dataType: "json",
                  success : function (result) {
                	  console.log(result);
                	  if (result.count == 1) {
                		  alert("删除课程< " + result.courseName + " >成功~`");
                          window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
                      } else {
                          alert("修改失败~`");
                          window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
                      }
                      
                  },
                  error : function (error) {
                      alert("删除课程失败~`");
                      window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/course-course";
                  }
              })
          }
      }
    </script>
    
</body>
</html>