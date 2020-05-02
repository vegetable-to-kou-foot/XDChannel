<%--
  Created by IntelliJ IDEA.
  User: BIGBOSS
  Date: 2020/4/9
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>查询了所有的账户信息</h3>

<c:forEach items="${key1}" var="account">
    ${account.name}
</c:forEach>

</body>
</html>
