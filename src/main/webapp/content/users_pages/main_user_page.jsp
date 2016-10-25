<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 17.10.16
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:useBean id="userOnPage" scope="session" class="model.User"/>
<jsp:useBean id="user" scope="session" class="model.User"/>
<jsp:useBean id="genderDao" scope="session" class="dao.GenderDao"/>
<jsp:useBean id="friendsDao" scope="session" class="dao.FriendsDao"/>
<jsp:useBean id="newsDao" scope="session" class="dao.NewsDao"/>

<div style="position: relative;width: 100%;height: 26em;overflow: hidden;">
    <c:import url="/content/users_pages/left_panel.jsp"/>
    <c:if test="${userOnPage.id ne -1}">
        <div id="photo-panel">
            <div id="photo">
                <c:choose>
                    <c:when test="${userOnPage.avatar}">
                        <img src="content/images/users_avatars/${userOnPage.id}">
                    </c:when>
                    <c:otherwise>
                        <img src="content/images/users_images/avatar_less.png">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="control-button">
                <c:choose>
                    <c:when test="${user.id eq userOnPage.id}">
                        <form action="edit" method="post">
                            <button type="submit" formmethod="post">
                                <fmt:message key="Edit" bundle="${lbl}"/>
                            </button>
                        </form>
                        <button onclick="showPhotoUpload()"><fmt:message key="Upload_photo" bundle="${lbl}"/></button>
                    </c:when>
                    <c:otherwise>
                        <button onclick="showMessageDiv()">
                            <fmt:message key="Send_message" bundle="${lbl}"/>
                        </button>
                        <c:choose>
                            <c:when test="${friendsDao.isFriend(user.id,userOnPage.id)}">
                                <form action="service/removeFriend" method="post">
                                    <button type="submit" formmethod="post">
                                        <fmt:message key="Remove_from_friend" bundle="${lbl}"/>
                                    </button>
                                    <input type="hidden" name="user_id" value="${user.id}">
                                    <input type="hidden" name="friend_id" value="${userOnPage.id}">
                                    <input type="hidden" name="backUri"
                                           value="${pageContext.request.requestURI}id${userOnPage.id}">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="service/addFriend" method="post">
                                    <button type="submit" formmethod="post">
                                        <fmt:message key="Add_to_friend" bundle="${lbl}"/>
                                    </button>
                                    <input type="hidden" name="user_id" value="${user.id}">
                                    <input type="hidden" name="friend_id" value="${userOnPage.id}">
                                    <input type="hidden" name="backUri"
                                           value="${pageContext.request.requestURI}id${userOnPage.id}">
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div id="user-info-panel">
            <div>
                <h1>
                    <jsp:getProperty name="userOnPage" property="surname"/>
                    <jsp:getProperty name="userOnPage" property="name"/>
                </h1>
            </div>
            <hr>
            <c:if test="${sessionScope.user.email ne null}">
                <div class="row_user-info-panel">
                    <div class="label-row_user-info-panel">
                        <fmt:message key="Email" bundle="${lbl}"/>:
                    </div>
                    <div class="value-row_user-info-panel">
                        <jsp:getProperty name="userOnPage" property="email"/>
                    </div>
                </div>
            </c:if>
            <c:if test="${sessionScope.user.telephoneNumber ne null}">
                <div class="row_user-info-panel">
                    <div class="label-row_user-info-panel">
                        <fmt:message key="Telephone" bundle="${lbl}"/>:
                    </div>
                    <div class="value-row_user-info-panel">
                        <jsp:getProperty name="userOnPage" property="telephoneNumber"/>
                    </div>
                </div>
            </c:if>
            <div class="row_user-info-panel">
                <div class="label-row_user-info-panel">
                    <fmt:message key="Date_of_birthday" bundle="${lbl}"/>:
                </div>
                <div class="value-row_user-info-panel">
                    <fmt:formatDate value="${userOnPage.birthdayDay}" pattern="dd.MM.yyyy"/>
                </div>
            </div>
            <div class="row_user-info-panel">
                <div class="label-row_user-info-panel">
                    <fmt:message key="Gender" bundle="${lbl}"/>:
                </div>
                <div class="value-row_user-info-panel">
                    <fmt:message key="${genderDao.getNameById(userOnPage.gender)}" bundle="${lbl}"/>
                </div>
            </div>
            <div class="row_user-info-panel">
                <div class="label-row_user-info-panel" style="float: none;">
                    <fmt:message key="About_myself" bundle="${lbl}"/>:<br>
                </div>
                <div class="value-row_user-info-panel" style="padding: 10px;">
                    <pre><jsp:getProperty name="userOnPage" property="aboutSelf"/></pre>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${userOnPage.id eq -1}">
        <div id="photo-panel">
            <fmt:message key="This_profile_deleted_or_not_created" bundle="${lbl}"/>
        </div>
    </c:if>
</div>

<c:choose>
    <c:when test="${user.id eq userOnPage.id or friendsDao.isFriend(user.id,userOnPage.id)}">
        <div id="news-feed">
            <div id="header-news-feed">
                <div id="title-header-news-feed">
                    <c:choose>
                        <c:when test="${user.id eq userOnPage.id}">
                            <h2><fmt:message key="Your_news_feed" bundle="${lbl}"/></h2>
                        </c:when>
                        <c:otherwise>
                            <h2><fmt:message key="News_feed_of"
                                             bundle="${lbl}"/> ${userOnPage.surname} ${userOnPage.name}</h2>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${user.id eq userOnPage.id}">
                    <div id="add-feed">
                        <form action="service/subscribe">
                            <label>Url: </label>
                            <input type="text" name="url">
                            <button type="submit" formmethod="post"><fmt:message key="Subscribe"
                                                                                 bundle="${lbl}"/></button>
                            <input type="hidden" name="owner_id" value="${user.id}">
                            <input type="hidden" name="backUri" value="${pageContext.request.requestURI}">
                        </form>
                    </div>
                </c:if>
            </div>
            <div id="content_news-feed">
                <c:forEach items="${newsDao.getFeedsListOfUsers(userOnPage.id)}" var="feedChannel">
                    <div class="row_content_news-feed">
                        <a href="feed?feeds_url=${feedChannel.feedsUrl}">
                            <div class="info_row_content_news-feed">
                                <h3><p>${feedChannel.title}</p></h3>
                                    ${feedChannel.description}
                            </div>
                        </a>
                        <c:choose>
                            <c:when test="${newsDao.haveItNewsFeed(user.id,feedChannel.feedsUrl)}">
                                <form action="service/unsubscribe"
                                      style="display: block;position: absolute;top: 0;right: 0;margin: 5px;">
                                    <button type="submit" formmethod="post"><fmt:message key="Unsubscribe"
                                                                                         bundle="${lbl}"/></button>
                                    <input type="hidden" name="owner_id" value="${user.id}">
                                    <input type="hidden" name="url" value="${feedChannel.feedsUrl}">
                                    <input type="hidden" name="backUri"
                                           value="${pageContext.request.requestURI}id${userOnPage.id}">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="service/subscribe"
                                      style="display: block;position: absolute;top: 0;right: 0;margin: 5px;">
                                    <button type="submit" formmethod="post"><fmt:message key="Subscribe"
                                                                                         bundle="${lbl}"/></button>
                                    <input type="hidden" name="owner_id" value="${user.id}">
                                    <input type="hidden" name="url" value="${feedChannel.feedsUrl}">
                                    <input type="hidden" name="backUri"
                                           value="${pageContext.request.requestURI}id${userOnPage.id}">
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="news-feed">
            <h2><fmt:message key="Profile_is_not_in_your_friend_list" bundle="${lbl}"/>!</h2>
        </div>
    </c:otherwise>
</c:choose>
