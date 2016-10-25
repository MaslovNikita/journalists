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
    <title><fmt:message key="Deleted_page" bundle="${lbl}"/></title>
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
            <span class="category-button"><a href="/Journalist.ru/inbox"><fmt:message key="Inbox" bundle="${lbl}"/></a></span>
            <span class="category-button"><a href="/Journalist.ru/sent"><fmt:message key="Sent" bundle="${lbl}"/></a></span>
            <span class="category-button"><a href="/Journalist.ru/deleted"><fmt:message key="Deleted" bundle="${lbl}"/></a></span>
        </div>
        <div class="message-content">
            <c:forEach items="${messageDao.getMessages(user.id,true,true)}" var="incoming_deleted_message">
            <c:choose>
            <c:when test="${incoming_deleted_message.viewed}">
            <div class="message">
                </c:when>
                <c:otherwise>
                <div class="message" style="font-weight: bold">
                    </c:otherwise>
                    </c:choose>
                    <div class="message-from">
                        <a href="/Journalist.ru/id${incoming_deleted_message.senderId}">${userDao.getUserById(incoming_deleted_message.senderId).surname} ${userDao.getUserById(incoming_deleted_message.senderId).name}</a>
                    </div>
                    <form action="reply_page.jsp" method="post">
                        <a href="/Journalist.ru/reply?message_id=${incoming_deleted_message.id}&is_incoming=true">
                            <div class="message_firstline">
                                    ${incoming_deleted_message.message}
                            </div>
                        </a>
                    </form>
                    <div class="message-delete">
                        <form action="service/removeFromTrash">
                            <button formmethod="post" type="submit">
                                <img src="content/images/icons/cancel.png">
                            </button>
                            <input type="hidden" name="message_id" value="${incoming_deleted_message.id}">
                            <input type="hidden" name="is_incoming" value="true">
                            <input type="hidden" name="backUri" value="${pageContext.request.requestURI}">
                        </form>
                    </div>
                    <div class="message_date"><fmt:formatDate value="${incoming_deleted_message.sendingTime}"
                                                              pattern="dd-MM-yyyy HH:mm"/></div>
                </div>
                </c:forEach>
                <c:forEach items="${messageDao.getMessages(user.id,true,false)}" var="outgoing_deleted_message">
                    <div class="message">
                        <div class="message-to">
                            <a href="/Journalist.ru/id${outgoing_deleted_message.receiverId}">${userDao.getUserById(outgoing_deleted_message.receiverId).surname} ${userDao.getUserById(outgoing_deleted_message.receiverId).name}</a>
                        </div>
                        <form action="reply_page.jsp" method="post">
                            <a href="/Journalist.ru/reply?message_id=${outgoing_deleted_message.id}&is_incoming=false">
                                <div class="message_firstline">
                                        ${outgoing_deleted_message.message}
                                </div>
                            </a>
                        </form>
                        <div class="message-delete">
                            <form action="service/removeFromTrash">
                                <button formmethod="post" type="submit">
                                    <img src="content/images/icons/cancel.png">
                                </button>
                                <input type="hidden" name="message_id" value="${outgoing_deleted_message.id}">
                                <input type="hidden" name="is_incoming" value="false">
                                <input type="hidden" name="backUri" value="${pageContext.request.requestURI}">
                            </form>
                        </div>
                        <div class="message_date"><fmt:formatDate value="${outgoing_deleted_message.sendingTime}"
                                                                  pattern="dd-MM-yyyy HH:mm"/></div>
                    </div>
                </c:forEach>
            </div>
            <div>
                <form action="service/clearDeleted">
                    <button formmethod="post" type="submit"><fmt:message key="Clear" bundle="${lbl}"/></button>
                    <input type="hidden" name="user_id" value="${user.id}">
                    <input type="hidden" name="backUri" value="${pageContext.request.requestURI}">
                </form>
            </div>
        </div>
    </div>
</body>
</html>
