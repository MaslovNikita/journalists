<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 24.10.16
  Time: 23:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="userDao" scope="session" class="dao.UserDao"/>
<jsp:useBean id="user" scope="session" class="model.User"/>
<jsp:useBean id="friendsDao" scope="session" class="dao.FriendsDao"/>


<html>
<head>
    <c:import url="/content/i18n/setBundle.jsp"/>
    <title><fmt:message key="Search_page" bundle="${lbl}"/></title>
    <link rel="stylesheet" href="/Journalist.ru/content/css/mainStyle.css">
    <script src="/Journalist.ru/content/js/leftMenu.js"></script>
    <meta charset="utf-8"/>
</head>
<body>
<c:import url="/header.jsp"/>
<div id="main-div">
    <c:import url="/content/users_pages/left_panel.jsp"/>
    <div id="search-param">
        <form action="searchResult">
            <div class="search-row">
                <label><fmt:message key="Name" bundle="${lbl}"/></label>
                <input type="text" name="name" value="${requestScope.searchName}">
            </div>
            <div class="search-row">
                <label><fmt:message key="Surname" bundle="${lbl}"/></label>
                <input type="text" name="surname" value="${requestScope.searchSurname}">
            </div>
            <div class="search-row">
                <label><fmt:message key="Gender" bundle="${lbl}"/></label>
                <select id="form-gender" name="gender">
                    <option value="1"><fmt:message key="Male" bundle="${lbl}"/></option>
                    <option value="2"><fmt:message key="Female" bundle="${lbl}"/></option>
                    <option selected value="3"><fmt:message key="Unknown" bundle="${lbl}"/></option>
                </select>
            </div>
            <div class="search-row">
                <button type="submit" formmethod="post"><fmt:message key="Search" bundle="${lbl}"/></button>
            </div>
        </form>
    </div>
    <div id="search-result">
        <div id="header-result"><fmt:message key="Result" bundle="${lbl}"/>:</div>
        <div style="overflow-y: scroll;height: 300px;">
            <c:forEach items="${requestScope.foundedUsers}" var="foundProfile">
                <div class="row_search-result">
                    <a href="/Journalist.ru/id${foundProfile.id}">
                        <span>${foundProfile.surname}</span> <span>${foundProfile.name}</span>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
