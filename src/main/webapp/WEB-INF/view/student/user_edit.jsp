<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/user/user_edit.css">
<title>在线修改</title>
</head>
<body>
    <h1>信息修改</h1>
    <hr />
    <div class="box">
      <form id="form">
        <label>用&nbsp; 户&nbsp; 名:<input type="text" name="userName" value="${user.userName }" readonly="readonly" size="40"></label><br />
        <label>编&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; 号:<input type="text" name="userId" value="${user.userId}" readonly="readonly" size="40"></label><br />
        <input type="hidden" name="sex" value="${user.sex}" />
        <label>性&nbsp;&nbsp;&nbsp; &nbsp; &nbsp; 别:<input type="text" name="" value="${user.sex == 1 ? '男' : '女'}" readonly="readonly" size="40"></label><br />
        <input type="hidden" name="bir" value="${user.birthday}" />
        <label>出生年月:<input type="text" name="birthday" value="" readonly="readonly" size="40"></label><br />
        <label id="introduction">个人介绍:<input type="text" name="introduction" value="${user.introduction }" size="40"><span><span class="glyphicon glyphicon-pencil"></span>此处填写</span></label><br />
      </form>
      <div class="footnav">
        <button class="btn btn-default" onclick="userEdit()">修改</button>
        <input class="btn btn-default" type="reset" value="重置"/>
      </div>
    </div>
    <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">$("input[readonly='readonly']").css("background", "#E3E3E3");</script>
    <script type="text/javascript">
      function userEdit() {
    	  var data = $("#form").serialize();
    	  $.ajax ({
    	    url : "<%= basePath%>/StudentController/studentUpdateById",
    	    data : data,
    		type : "post",
    	    dataType : "json",
    		success: function (result) {
                 alert("修改用户 " + result.userName + " 成功~`");
    			 window.location.href = "<%= basePath %>/StudentController/studentLogin?userName=" + result.userName + "&userId=" + result.userId;
    		},
    		error : function (error) {
    			alert("修改用户失败~`");
    		},
    	  });
      }
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