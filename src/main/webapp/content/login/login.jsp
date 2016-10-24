<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 03.10.16
  Time: 12:41
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:import url="/content/i18n/setBundle.jsp"/>
<html>
<head>
    <title>Sign in</title>
    <link rel="stylesheet" href="content/css/mainStyle.css">
</head>
<body>
<c:import url="/header.jsp"/>
<form accept-charset="utf-8" action="service/auth" method="post">
    <div class="form-row">
        <label for="form-login"><fmt:message key="Login" bundle="${lbl}"/>: </label>
        <input id="form-login" type="text" name="login">
    </div>
    <div class="form-row">
        <label for="form-password"><fmt:message key="Password" bundle="${lbl}"/>: </label>
        <input id="form-password" type="password" name="password">
    </div>
    <c:if test="${sessionScope.isIncorrcet eq 'true'}">
        <div class="form-row">
            <span><fmt:message key="Incorrect_login_or_password" bundle="${lbl}"/></span>
        </div>
    </c:if>
    <div class="form-row">
        <input type="submit" value="<fmt:message key="Submit" bundle="${lbl}"/>">
    </div>
</form>
<p><fmt:message key="New_to_journalist" bundle="${lbl}"/></p>
</body>
</html>

