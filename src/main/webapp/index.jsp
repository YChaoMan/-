<% String basePath = request.getContextPath(); %>
<html>
<body>
<h2>Hello World!</h2>
  <% response.sendRedirect(basePath + "/RedirectController/jspSendRedirect/index" ); %>
</body>
</html>
