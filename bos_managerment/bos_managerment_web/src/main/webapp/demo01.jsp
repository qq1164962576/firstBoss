<%--
  Created by IntelliJ IDEA.
  User: Frank
  Date: 2017/11/16
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<html>
<head>
    <title>权限测试</title>
</head>
<body>
<shiro:authenticated>

    你通过了认真
</shiro:authenticated>

<hr>

<shiro:hasPermission name="courier_PageQuery">

    你拥有 pageQuery权限
</shiro:hasPermission>

<shiro:hasPermission name="courierAction_save">

    你拥有 courierAction_save权限
</shiro:hasPermission>

<shiro:hasRole name="j">
    你的角色是j
</shiro:hasRole>
</body>
</html>
