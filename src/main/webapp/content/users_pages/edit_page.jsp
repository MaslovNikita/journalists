<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 18.10.16
  Time: 23:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="user" scope="session" class="model.User"/>

<html>
<head>
    <c:import url="/content/i18n/setBundle.jsp"/>
    <title>Edit profile</title>
    <link rel="stylesheet" href="content/css/edit.css">
    <script src="content/js/leftMenu.js"></script>
    <meta charset="utf-8"/>
</head>
<body>
<c:import url="/header.jsp"/>
<div id="main-div">
    <c:import url="/content/users_pages/left_panel.jsp"/>
    <form accept-charset="utf-8" method="post" class="edit-form" id="form-edit" action="service/edit">
        <div class="form-row">
            <label for="form-name"><fmt:message key="Name" bundle="${lbl}"/></label>
            <input type="text" required name="name" value="${user.name}" id="form-name">
        </div>
        <div class="form-row">
            <label for="form-surname"><fmt:message key="Surname" bundle="${lbl}"/></label>
            <input type="text" required name="surname" value="${user.surname}" id="form-surname">
        </div>
        <div class="form-row">
            <label for="form-date-birthday"><fmt:message key="Date_of_birthday" bundle="${lbl}"/>: </label>
            <input type="text" id="form-date-birthday" name="date_birthday"
                   value="<fmt:formatDate value="${user.birthdayDay}" pattern="dd.MM.yyyy"/>"
                   placeholder='<fmt:message key="placeholder_date" bundle="${lbl}"/>'
                   pattern="^\d\d?.\d\d?.\d\d\d\d$">
        </div>
        <div class="form-row">
            <label for="form-telephone"><fmt:message key="Telephone" bundle="${lbl}"/></label>
            <input type="text" name="telephone" value="${user.telephoneNumber}" id="form-telephone"
                   pattern="^\d*$">
        </div>
        <div class="form-row">
            <label for="form-email"><fmt:message key="Email" bundle="${lbl}"/></label>
            <input type="email" name="email" value="${user.email}" id="form-email">
        </div>
        <div class="form-row">
            <label for="form-gender"><fmt:message key="Gender" bundle="${lbl}"/></label>
            <select id="form-gender" name="gender">
                <option selected value="1"><fmt:message key="Male" bundle="${lbl}"/></option>
                <option value="2"><fmt:message key="Female" bundle="${lbl}"/></option>
                <option value="3"><fmt:message key="Unknown" bundle="${lbl}"/></option>
            </select>
        </div>
        <div class="form-row">
            <label for="form-about-self"><fmt:message key="About_self" bundle="${lbl}"/>: </label>
            <textarea form="form-edit" id="form-about-self" name="about_self">${user.aboutSelf}</textarea>
        </div>
        <div class="form-row">
            <button type="submit" formmethod="post">
                <fmt:message key="Save" bundle="${lbl}"/>
            </button>
            <button type="button" onclick="history.back()">
                <fmt:message key="Close" bundle="${lbl}"/>
            </button>
        </div>
    </form>
</div>

</body>
</html>
