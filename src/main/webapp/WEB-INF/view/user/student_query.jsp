<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String basePath = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%= basePath %>/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<link type="text/css" rel="stylesheet" href="<%= basePath %>/static/css/student/student_query.css">
<title>用户管理</title>
</head>
<body>
  <h2>用户管理</h2>
  <hr />
  <div class="box">
    <div class="header">
      <span class="glyphicon glyphicon-bookmark">&nbsp;身份筛选: >> </span>
      <input type="text" name="condition" size="50" />
      <button onclick="queryAll()">查询</button>
      <input type="radio" name="identity" onclick="queryAll(1, 1)" id="1"/> <label for="1">职 工</label> / 
      <input type="radio" name="identity" onclick="queryAll(0, 1)" id="0"/> <label for="0">学 生</label> /
      <input type="radio" name="identity" checked="checked" onclick="queryAll(3, 1)" id="3"/> <label for="3">默 认</label>
      <button class="btn btn-default" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/user-student_add'">新增用户</button>
      <button class="btn btn-default" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/grade-grade_add'">新增成绩</button>
      <button class="btn btn-default" onclick="window.location.href='<%= basePath %>/RedirectController/jspSendRedirect/manager'">返  回</button>
    </div>
    <div class="section">
      <table cellpadding="0" cellspacing="0">
        <thead>
          <tr>
            <td>编号</td>
            <td>用户编号</td>
            <td>姓  名</td>
            <td>性别</td>
            <td>身份</td>
            <td>出生日期</td>
            <td>个人介绍</td>
            <td>创建日期</td>
            <td>可行/操作</td>
          </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
      </table>
      
      <form id="page" style="text-align:center;margin: 10px 0 0 20px;">
      </form>
    </div>
  </div>
  <script type="text/javascript" src="<%= basePath %>/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
      $(function () {
          queryAll();
      })
      
      function queryAll(identity, currentPage) {
          var strHtml = "";
          var pageHtml = "";
          var condition = $("input[name='condition']").val();
          var totalPage = $("input[name='totalPage'][type='hidden']").val();
          $("#tbody").html('');
          $("#page").html('');
          var data = {
        	  identity: identity,
        	  currentPage: currentPage,
        	  totalPage: totalPage,
        	  condition: condition
          };
          console.log(data);
          $.ajax({
              url :  "<%= basePath %>/UserController/selectAll",
              type : "post",
              data: data,
              dataType : "json",
              success : function (result) {
                  $.each(result.userList, function (iu, u) {
                	  console.log(u);
                      strHtml = "<tr><td>" + (++result.page.startRow) + "</td><td>" + u.userId + "</td><td>" + u.userName + "</td><td>" + (u.sex == 1 ? "男" : "女")
                        + "</td><td>" + (u.identity == 1 ? "职工" : "学生") + "</td><td>" + new Date(u.birthday).Format("yyyy-MM-dd") + "</td><td>"
                        + u.introduction + "</td><td>" + new Date(u.createTime).Format("yyyy-MM-dd HH:mm:ss") + "</td><td><button onclick=\"jump(" + u.userId + ")\"><span class=\"glyphicon glyphicon-cog\" title=\"编辑\">"
                        + "</span></button> / <button onclick=\"delUser(" + u.userId + ")\"><span class=\"glyphicon glyphicon-trash\"></span></button></td></tr>";
                      $("#tbody").append(strHtml);
                  })
                  pageHtml += "<input type=\"hidden\" name=\"totalPage\" value=\"" + result.page.totalPage + "\" />"
                      + "<label>第&nbsp;" + result.page.currentPage + "&nbsp;/&nbsp;" + result.page.totalPage + "&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共" + result.page.totalRow + "条</label>&nbsp;&nbsp;&nbsp;&nbsp;"
                      + "<a href=\"javascript:queryAll()\">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                      + "<a href=\"javascript:queryAll('','" + (result.page.currentPage - 1) + "')\" onclick=\"checkBack('" + (result.page.currentPage - 1) + "')\">上一页" + "</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                      + "<a href=\"javascript:queryAll('','" + (result.page.currentPage + 1)+ "')\" onclick=\"checkNext('" + (result.page.currentPage + 1) + "','" + result.page.totalPage + "')\">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                      + "<a href=\"javascript:queryAll('','" + result.page.totalPage + "')\"\">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;"
                      + "<label>跳转到<input type=\"text\" name=\"jumpPage\" size=\"" + (result.page.totalPage / 10 + 1)
                      + "\"/>页</label>&nbsp;&nbsp;&nbsp;&nbsp;<button onclick=\"jumpToPage('" + result.page.currentPage + "','" + result.page.totalPage + "')\">跳转</button>";
                  $("#page").append(pageHtml); 
              },
              error : function (error) {
                  alert("数据走丢~`");
                  window.location.href="<%= basePath %>/RedirectController/jspSendRedirect/manager";
              }
          })
      }
      
      // 删除用户
      function delUser(userId) {
          var del = confirm("是否删除该用户~!") ;
          var data = ({
              userId : userId
          });
          if (del) {
              $.ajax({
                  data : data,
                  type : "post",
                  dataType: "json",
                  url : "<%= basePath %>/UserController/delById",
                  success : function (result) {
                	  if (result.count == 1) {
                          alert("删除用户< " + result.userName + " >成功~`");
                          window.location.href = "<%= basePath %>/RedirectController/jspSendRedirect/user-student_query";
                	  } else {
                          alert("删除用户< " + result + " >失败~`");
                          return false;
                	  }
                  },
                  error : function (error) {
                      alert("删除用户失败~`");
                  }
              })
          }
      }
   
      // 跳转
      function jump(userId) {
          window.location.href="<%= basePath %>/UserController/queryById?userId=" + userId;
      }

      // 判断是否有下一页
      function checkNext(currentPage, totalPage) {
    	  if (currentPage > totalPage) {
    		  alert("没有下页了喔!`");
    		  return ;
    	  }
      }
      
      // 判断是否有上一页
      function checkBack(currentPage) {
    	  if (currentPage == 0) {
    		  alert("没有上页了喔!`");
    	  }
      }
      
      // 跳转页, currentPage,当前页。totalPage，总页数
      function jumpToPage(currentPage, totalPage) {
    	  var jump = $("input[name='jumpPage']").val();    // 获取跳转文本框中的内容
    	  if (jump > totalPage || jump == null || jump == undefined || jump == 0) {
    		  alert("找不到该页!`");
              queryAll('', currentPage);
    	  } else {
    		  queryAll('', jump);
    	  }
      }
      
      
      $(document).ready(function () {
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
      });
    </script>
</body>
</html>