<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 16.10.16
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:import url="/content/i18n/setBundle.jsp"/>
    <title>journalist</title>
    <meta charset="utf-8"/>
</head>
<body>
    <p><fmt:message key="successfully_created" bundle="${lbl}"/></p>
</body>
</html>
