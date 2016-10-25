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
<jsp:useBean id="newsDao" scope="session" class="dao.NewsDao"/>
<jsp:useBean id="feedChannel" scope="session" class="model.FeedChannel"/>


<html>
${feedChannel.set(newsDao.getChannelByUrl(param.feeds_url))}
<head>
    <c:import url="/content/i18n/setBundle.jsp"/>
    <title><fmt:message key="News_feed" bundle="${lbl}"/></title>
    <link rel="stylesheet" href="content/css/mainStyle.css">
    <script src="content/js/leftMenu.js"></script>
    <meta charset="utf-8"/>
</head>
<body>
<c:import url="/header.jsp"/>
<div id="main-div">
    <c:import url="/content/users_pages/left_panel.jsp"/>
    <div id="feed-page-content">
        <div>
            <h3><p>${feedChannel.title}</p></h3>
            <a href="${feedChannel.link}">
                ${feedChannel.description}
            </a>
        </div>
        <div>
            <ul>
                <c:forEach items="${newsDao.getItemsOfFeed(feedChannel.feedsUrl)}" var="item">
                    <li><a href="${item.link}">${item.datetime} ${item.title}</a></li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
