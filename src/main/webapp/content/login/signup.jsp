<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 10.10.16
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <c:import url="/content/i18n/setBundle.jsp"/>
    <title><fmt:message key="Register" bundle="${lbl}"/></title>
    <link rel="stylesheet" href="content/css/register.css">
    <meta charset="utf-8"/>
</head>
<body>
<c:import url="/header.jsp"/>
<form accept-charset="utf-8" method="post" id="register_data" action="service/register" class="reg-form">
    <div class="form-row">
        <p class="title"><fmt:message key="Create_your_account" bundle="${lbl}"/></p>
    </div>
    <div class="form-row">
        <label for="form-login"><fmt:message key="Login" bundle="${lbl}"/>: </label>
        <input type="text" id="form-login" name="login" autocomplete="on" required value="${sessionScope.tmpLogin}">
        <c:if test="${sessionScope.isLoginOccupied.equals('true')}">
            <span class="error-format">
                <fmt:message key="Login_is_occupied" bundle="${lbl}"/>
            </span>
        </c:if>
    </div>
    <div class="form-row">
        <label for="form-password"><fmt:message key="Password" bundle="${lbl}"/>: </label>
        <input type="password" id="form-password" name="password" required>
    </div>
    <div class="form-row">
        <label for="form-name"><fmt:message key="Name" bundle="${lbl}"/>: </label>
        <input type="text" id="form-name" name="name" value="${sessionScope.tmpName}" required>
    </div>
    <div class="form-row">
        <label for="form-surname"><fmt:message key="Surname" bundle="${lbl}"/>: </label>
        <input type="text" id="form-surname" name="surname" value="${sessionScope.tmpSurname}" required>
    </div>
    <div class="form-row">
        <label for="form-email"><fmt:message key="Email" bundle="${lbl}"/>: </label>
        <input type="text" id="form-email" name="email" value="${sessionScope.tmpEmail}">
    </div>
    <div class="form-row">
        <label for="form-date-birthday"><fmt:message key="Date_of_birthday" bundle="${lbl}"/>: </label>
        <input type="text" id="form-date-birthday" name="date_birthday" value="${sessionScope.tmpDateBirthday}"
               placeholder='<fmt:message key="placeholder_date" bundle="${lbl}"/>'
               pattern="^\d\d?.\d\d?.\d\d\d\d$">
        <c:if test="${sessionScope.isDateIncorrect.equals('true')}">
            <span class="error-format">
                <fmt:message key="Date_is_incorrect" bundle="${lbl}"/>
            </span>
        </c:if>
    </div>
    <div class="form-row">
        <label for="form-about-self"><fmt:message key="About_self" bundle="${lbl}"/>: </label>
        <textarea form="register_data" id="form-about-self" name="about_self">${sessionScope.tmpAboutSelf}</textarea>
    </div>
    <button type="submit" formmethod="post">
        <fmt:message key="Submit" bundle="${lbl}"/>
    </button>
</form>
</body>
</html>
