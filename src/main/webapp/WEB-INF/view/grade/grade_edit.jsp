<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/grade/grade_edit.css">
<title>成绩修改</title>
</head>
<body>
    <div class="box">
      <div class="header">
        <p>成绩修改</p>
      </div>
      <div class="section">
        <form id="form">
          <input type="hidden" name="userId" value="${user.userId }" />
          <div><span class="glyphicon glyphicon-play"></span><label>用户名: </label><input type="text" name="userName" readonly="readonly" value="${user.userName }" /></div>
          <div><span class="glyphicon glyphicon-play"></span><label>课程名称: </label>
          <label><select name="courseNumber">
            <c:forEach var="courseList" items="${courseList }">
              <c:forEach var="gradeList" items="${gradeList }">
                <c:if test="${gradeList.courseNumber == courseList.courseNumber}">
                  <option value="${courseList.courseNumber}">${courseList.courseName }</option>
                </c:if>
              </c:forEach>
            </c:forEach>
          </select></label></div>
          <div><span class="glyphicon glyphicon-play"></span><label>分数值: </label><input type="text" name="score" /></div>
          <div><span class="glyphicon glyphicon-play"></span><label for="edit"><input type="radio" name="manager" value="" onclick="mouserClick(0)" id="edit" />修 改</label>
          <label for="del" style="margin-left: 20px;"><input type="radio" name="manager" value="" onclick="mouserClick(1)" id="del" />删 除</label></div>
        </form>
      </div>
      <div class="nav">
        <button class="btn btn-success" onclick="ensure()" >确 定</button>
        <button class="btn btn-warning" onclick="window.location.href = '<%= basePath %>/RedirectController/jspSendRedirect/manager'">返 回</button>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      // 确认是否选择了修改选项
      function mouserClick(value) {
    	  $("input[name='manager']").val(value);
      }
      
      // 修改方式的跳转，0为修改，1为删除
      function ensure() {
    	  var type = $("input[name='manager']").val();
    	  switch (type) {
    	  case "0":
    		  editGrade();
    		  break;
    	  case "1":
    		  delGrade();
    		  break;
    	  default:
    		  alert("是否缺少修改选项~`");
              window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/manager";
    		  break;
    	  }
      }
      
      // 修改成绩
      function editGrade() {
    	  var data = $("#form").serialize();
    	  $.ajax({
    		  data : data,
    		  type : "post",
    		  dataType : "json",
    		  url : "<%= basePath %>/GradeController/updateById",
    		  success : function (result) {
    			  if (result.count == 1) {
    				  alert("修改< " + result.courseName + " > 成绩成功~`");
    				  window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/manager";
    			  } else {
    				  alert("修改成绩失败~`");
    				  window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/manager";
    			  }
    		  },
    		  erro : function (error) {
    			  alert("修改成绩失败~`");
    			  window.location.href = "/StudentManager/RedirectController/jspSendRedirect/manager/manager";
    			  return false;
    		  }
    	  })
      }
      
      // 删除成绩
      function delGrade() {
    	  var data = $("#form").serialize();
          $.ajax({
              data : data,
              type : "post",
              dataType: "json",
              url : "<%= basePath %>/GradeController/delByIdAndCourseNumber",
              success : function (result) {
                  if (result.count == 1) {
                      alert("删除< " + result.courseName + " > 成绩成功~`");
                  } else {
                      alert("删除成绩失败~`");
                  }
                  window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/manager";
              },
              erro : function (error) {
                  alert("删除成绩失败~`");
                  window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/manager";
              }
          })
      }
    </script>
</body>
</html>