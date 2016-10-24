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
                    <form action="" method="post">
                        <button type="submit" formmethod="post">
                            <fmt:message key="Add_to_friend" bundle="${lbl}"/>
                        </button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div id="user-info-panel">
        <div class="row_user-info-panel">
            <jsp:getProperty name="userOnPage" property="surname"/>
            <jsp:getProperty name="userOnPage" property="name"/>
        </div>
        <hr>
        <c:if test="${sessionScope.user.email ne null}">
            <div class="row_user-info-panel">
                <fmt:message key="Email" bundle="${lbl}"/>:
                <jsp:getProperty name="userOnPage" property="email"/>
            </div>
        </c:if>

        <c:if test="${sessionScope.user.telephoneNumber ne null}">
            <div class="row_user-info-panel">
                <fmt:message key="Telephone" bundle="${lbl}"/>:
                <jsp:getProperty name="userOnPage" property="telephoneNumber"/>
            </div>
        </c:if>

        <div class="row_user-info-panel">
            <fmt:message key="Date_of_birthday" bundle="${lbl}"/>:
            <fmt:formatDate value="${userOnPage.birthdayDay}" pattern="dd.MM.yyyy"/>
        </div>
        <div class="row_user-info-panel">
            <fmt:message key="Gender" bundle="${lbl}"/>:
            ${genderDao.getNameById(userOnPage.gender)}
        </div>

        <div class="row_user-info-panel">
            <fmt:message key="About_self" bundle="${lbl}"/>:<br>
            <pre><jsp:getProperty name="userOnPage" property="aboutSelf"/></pre>
        </div>
    </div>
</c:if>
<c:if test="${userOnPage.id eq -1}">
    <div id="photo-panel">
        <fmt:message key="This_profile_deleted_or_not_created" bundle="${lbl}"/>
    </div>
</c:if>
