<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 24.10.16
  Time: 17:20
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
    <title><fmt:message key="Friends" bundle="${lbl}"/></title>
    <link rel="stylesheet" href="content/css/mainStyle.css">
    <script src="content/js/leftMenu.js"></script>
    <meta charset="utf-8"/>
</head>
<body>
<c:import url="/header.jsp"/>
<div id="main-div">
    <c:import url="/content/users_pages/left_panel.jsp"/>
    <div id="friend-list">
        <c:forEach items="${friendsDao.getFriends(user.id)}" var="friend_id">
            <div class="friend_friend-list">
                <a href="/Journalist.ru/id${friend_id}">
                    <div class="friend-image_friend-list">
                        <c:choose>
                            <c:when test="${userDao.getUserById(friend_id).avatar}">
                                <img src="content/images/users_avatars/${friend_id}">
                            </c:when>
                            <c:otherwise>
                                <img src="content/images/users_images/avatar_less.png">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="name_friend-list">
                        ${userDao.getUserById(friend_id).surname} ${userDao.getUserById(friend_id).name}
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</body>
</html>
