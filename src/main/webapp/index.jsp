<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 27.09.16
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:import url="content/i18n/setBundle.jsp"/>

<html>
<head>
    <title>First page</title>
    <link rel="stylesheet" href="content/css/mainStyle.css">
    <script src="content/js/popupDiv.js"></script>
    <script src="content/js/leftMenu.js"></script>
</head>
<body>
<c:import url="header.jsp"/>
<div id="main-div">
    <c:choose>
        <c:when test="${sessionScope.containsKey('user')}">
            <c:import url="content/users_pages/main_user_page.jsp"/>
        </c:when>
        <c:otherwise>
            <fmt:message key="Welcome_to_journalist" bundle="${lbl}"/>
        </c:otherwise>
    </c:choose>
</div>

<div id="shadow-background" style="visibility: hidden; z-index: 999;" onclick="hidePopup()">

</div>

<div id="popup-upload-photo" style="visibility: hidden; z-index: 1000;">
    ${requestScope.message}
    <form method="post" action="photoUploadServlet" enctype="multipart/form-data">
        <input type="file" name="file" size="60">
        <button type="submit" formmethod="post"><fmt:message key="Upload" bundle="${lbl}"/></button>
    </form>
    <button onclick="hidePopup()"><fmt:message key="Close" bundle="${lbl}"/></button>
</div>

<div id="popup-message-div" style="visibility: hidden; z-index: 1000;">
    <form action="sendMessage">
        <div id="message-header">
            <div id="image-user-message">
                <c:choose>
                    <c:when test="${userOnPage.avatar}">
                        <img src="content/images/users_avatar/${userOnPage.id}">
                    </c:when>
                    <c:otherwise>
                        <img src="content/images/users_images/avatar_less.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="surname-name">
                <p>${userOnPage.surname} ${userOnPage.name}</p>
            </div>
        </div>
        <div id="message-text">
            <textarea name="message_text" required></textarea>
        </div>
        <div id="message-button">
            <button formmethod="post"><fmt:message key="Send" bundle="${lbl}"/></button>
        </div>
        <input type="hidden" name="from-message" value="${user.id}">
        <input type="hidden" name="to-message" value="${userOnPage.id}">
        <input type="hidden" name="backURI" value="${pageContext.request.requestURI}">
    </form>
</div>

</body>
</html>
