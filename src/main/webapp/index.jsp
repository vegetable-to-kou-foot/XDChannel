<%--
  Created by IntelliJ IDEA.
  User: BIGBOSS
  Date: 2020/4/9
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<a href="account/findAll">测试</a>

<h3>测试保存</h3>

<form action="/addAccount" method="post">
    姓名：<input type="text" name="accName"/><br/>
    密码：<input type="password" name="userPswd"/><br/>
    邮箱：<input type="text" name="email"/><br/>
    <input type="submit" value="保存"/><br/>
</form>

</body>
</html>
