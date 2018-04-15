<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/student/student_edit.css">
<title>用户修改</title>
</head>
<body>
    <div class="box">
      <div class="header">
        <p>用户修改</p>
      </div>
      <div class="section">
        <form id="form">
          <div><label>编号: </label>
            <input type="text" name="userId" value="${user.userId }" readonly="readonly"/>
          </div>
          <div><label>用户名: </label>
            <input type="text" name="userName" value="${user.userName }" />
          </div>
          <div><label><span>&lowast;</span>性别: </label>
            <input type="radio" name="sex" value="1" id="1" /><label for="1">&nbsp;男</label>
            <input type="radio" name="sex" value="0" id="0" /><label for="0">&nbsp;女</label>
            </div>
          <div><label><span>&lowast;</span>身份: </label>
            <input type="radio" name="identity" value="1" id="01" /><label for="01">&nbsp;职 工</label>
            <input type="radio" name="identity" value="0" id="00" /><label for="00">&nbsp;学 生</label>
            </div>
          <div><label>出生日期: </label>
            <input type="hidden" name="bir" value="${user.birthday }">
            <input type="date" name="birthday" value="" />
          </div>
          <div><label>个人介绍: </label>
            <input type="text" name="introduction" value="${user.introduction }" placeholder="来自英雄的简介"/>
          </div>
        </form>
      </div>
      <div class="nav">
        <button class="btn btn-success" onclick="editUser()">修 改</button>
        <button class="btn btn-warning" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/user-student_query'">返 回</button>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      function editUser() {
    	  var data = $("#form").serialize();
    	  console.log(data);
    	  $.ajax ({
    		  data: data,
    		  type : "post",
    		  dataType: "json",
    		  url : "<%= basePath %>/UserController/updateById",
    		  success : function (result) {
    			  if (result.count == 1) {
    				  alert("修改用户< " + result.userName + " >成功~`");
    				  window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/user-student_query";
    			  } else {
    				  alert("修改用户< " + result.userName + " >失败~`");
    				  return false;
    			  }
              },
              error : function (error) {
            	  alert("是否缺少必填选项~`");
            	  return false;
              }
    	  })
      }
    </script>
    
    <script type="text/javascript">
      $(function () {
      var date = $("input[name='bir']").val();
      $("input[name='birthday']").val(new Date(date).Format("yyyy-MM-dd"));
    })
    </script>
    
    <script type="text/javascript">
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