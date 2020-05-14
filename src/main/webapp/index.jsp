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

<form action="addAccount" method="post">
    姓名：<input type="text" name="accName"/><br/>
    密码：<input type="password" name="userPswd"/><br/>
    邮箱：<input type="text" name="email"/><br/>
    <input type="submit" value="保存"/><br/>
</form>

<h2>图片上传</h2>
<form action="editProfilePic" method="post" enctype="multipart/form-data">
    图片:<input type="file"  name="upload"/><br/>
    <input type="submit" value="提交"/>
</form>

<img src="images/50abc705-fb49-40bc-a94c-b2190050a0e8MGSV.png"/>

</body>
</html>
