<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 23.10.16
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="userDao" scope="session" class="dao.UserDao"/>
<jsp:useBean id="user" scope="session" class="model.User"/>
<jsp:useBean id="messageDao" scope="session" class="dao.MessageDao"/>


<html>
<head>
    <c:import url="/content/i18n/setBundle.jsp"/>
    <title>inbox page</title>
    <link rel="stylesheet" href="content/css/mail.css">
    <script src="content/js/leftMenu.js"></script>
    <meta charset="utf-8"/>
</head>
<body>
<c:import url="/header.jsp"/>
<div id="main-div">
    <c:import url="/content/users_pages/left_panel.jsp"/>
    <div class="messages">
        <div class="messages-category-button">
            <span class="category-button"><a href="/Journalist.ru/inbox">Inbox</a></span>
            <span class="category-button"><a href="/Journalist.ru/sent">Sent</a></span>
            <span class="category-button"><a href="/Journalist.ru/deleted">Deleted</a></span>
        </div>
        <div class="message-content">
            <c:forEach items="${messageDao.getMessages(user.id,false,true)}" var="incoming_message">
                <div class="message">
                    <div class="message-from">
                        <a href="/Journalist.ru/id${incoming_message.senderId}">${userDao.getUserById(incoming_message.senderId).surname} ${userDao.getUserById(incoming_message.senderId).name}</a>
                    </div>
                    <form action="reply_page.jsp" method="post">
                        <a href="/Journalist.ru/reply?message_id=${incoming_message.id}&is_incoming=true">
                            <c:choose>
                            <c:when test="${incoming_message.viewed}">
                            <div class="message_firstline">
                                </c:when>
                                <c:otherwise>
                                <div class="message_firstline" style="font-weight: bold">
                                    </c:otherwise>
                                    </c:choose>
                                        ${incoming_message.message}
                                </div>
                        </a>
                    </form>
                    <div class="message-delete">
                        <form action="service/deleteMessage">
                            <button formmethod="post" type="submit">
                                <img src="content/images/icons/cancel.png">
                            </button>
                            <input type="hidden" name="message_id" value="${incoming_message.id}">
                            <input type="hidden" name="is_incoming" value="true">
                            <input type="hidden" name="backUri" value="${pageContext.request.requestURI}">
                        </form>
                    </div>
                    <div class="message_date"><fmt:formatDate value="${incoming_message.sendingTime}"
                                                              pattern="dd-MM-yyyy HH:mm"/></div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
