<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 23.10.16
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="userDao" scope="session" class="dao.UserDao"/>
<jsp:useBean id="user" scope="session" class="model.User"/>
<jsp:useBean id="messageDao" scope="session" class="dao.MessageDao"/>
<jsp:useBean id="readMessage" scope="session" class="model.Message"/>
<jsp:useBean id="receiverUser" scope="page" class="model.User"/>

<html>
<head>
    <title><fmt:message key="Reply" bundle="${lbl}"/></title>
    <link rel="stylesheet" href="content/css/reply.css">
    <script src="content/js/leftMenu.js"></script>
    <meta charset="utf-8"/>
    <c:import url="/content/i18n/setBundle.jsp"/>
</head>
<body>
<c:import url="/header.jsp"/>
<div id="main-div">
    <c:import url="/content/users_pages/left_panel.jsp"/>
    <div id="reply-main">
        <c:choose>
            <c:when test="${messageDao.isLetterIncoming(param.message_id,user.id)}">
                ${readMessage.set(messageDao.getMessage(param.message_id,true))}
                ${receiverUser.set(userDao.getUserById(readMessage.senderId))}
                <form action="service/replyServlet">
                    <div class="message-reply">
                        <div class="header-message-reply">
                            <p><fmt:message key="From" bundle="${lbl}"/> : ${receiverUser.surname} ${receiverUser.name}</p>
                        </div>
                        <div class="body-reply"><p>${readMessage.message}</p></div>
                        <div>
                            <div class="reply-text"><textarea name="message-text" required></textarea></div>
                            <input type="hidden" name="from-message" value="${user.id}">
                            <input type="hidden" name="to-message" value="${receiverUser.id}">
                            <button type="submit" formmethod="post"><fmt:message key="Send" bundle="${lbl}"/></button>
                        </div>
                    </div>
                </form>
            </c:when>
            <c:when test="${messageDao.isLetterOutgoing(param.message_id,user.id)}">
                ${readMessage.set(messageDao.getMessage(param.message_id,false))}
                ${receiverUser.set(userDao.getUserById(readMessage.receiverId))}
                <form action="service/replyServlet">
                    <div class="message-reply">
                        <div class="header-message-reply">
                            <p><fmt:message key="To" bundle="${lbl}"/>: ${receiverUser.surname} ${receiverUser.name}</p>
                        </div>
                        <div class="body-reply"><p>${readMessage.message}</p></div>
                        <div>
                            <div class="reply-text"><textarea name="message-text" required></textarea></div>
                            <input type="hidden" name="from-message" value="${user.id}">
                            <input type="hidden" name="to-message" value="${receiverUser.id}">
                            <button type="submit" formmethod="post"><fmt:message key="Send" bundle="${lbl}"/>Send</button>
                        </div>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <fmt:message key="No_such_message" bundle="${lbl}"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
