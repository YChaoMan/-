<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<% String basePath = request.getContextPath(); %>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>欢迎登陆</title>
  <link href="<%=basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
  <link type="text/css" rel="stylesheet" href="<%=basePath %>/static/css/index.css">
</head>
<body>
  <div class="section">
      <div class="panel panel-info">
        <div class="panel-heading">
        <h2 class="panel-title">用户登录</h2>
        </div>
        <form id="form" class="panel-body">
          <div class="name"><span class="glyphicon glyphicon-user"></span><input type="text" name="userName" placeholder="用户名"  /></div>
          <div class="word" onfocus="outline:blue;"><span class="glyphicon glyphicon-lock"></span><input type="password" name=password placeholder="编号" /></div>
          <p class="button">
            <input type="button" class="button1 btn btn-info" onclick="divide()" value="确   定" />&nbsp;&nbsp;
            <input type="reset" class="button2 btn btn-warning" value="重    置" />
          </p>
        </form>
      </div>
    </div>
    <script type="text/javascript" src="<%=basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function () {
        $("input[name='username']").focus(function(){
            $(".name").css("border","1px solid #48D1CC");
          });
        $("input[name='username']").blur(function(){
            $(".name").css("border","1px solid #CCC");
          });
        $("input[name='password']").focus(function(){
            $(".word").css("border","1px solid #48D1CC");
          });
        $("input[name='password']").blur(function(){
            $(".word").css("border","1px solid #CCC");
          }); 
    })
    </script>
    <script>
        function divide() {
            var data = $("#form").serialize();
            console.log(data);
            $.ajax({
                url : "<%=basePath %>/LoginController/login",
                data : data,
                type : "post",
                dataType: "json",
                success : function (result) {
                	console.log(result);
                    alert("登录成功，用户：< " + result.user.userName + " >\n\n\t即将进入...");
                	if (result.count == 1) {
                		window.location.href = result.url + "/manager";
                	} else {
                        window.location.href = result.url + "?userId=" + result.user.userId + "&userName=" + result.user.userName;
                	}
                		
                },
                error : function (){
                    alert("验证失败，即将返回...");
                    window.location.href = "<%=basePath %>";
                }
            }) 
        }
    </script>
</body>
</html>
