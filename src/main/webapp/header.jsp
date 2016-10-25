<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 11.10.16
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="header">
    <div id="language-header">
        <form action="service/change_lang" method="POST">
            <button name="btn" value="ru_RU" type="submit" formmethod="post"><img src="content/images/flags/Russia.png">
            </button>
            <button name="btn" value="en_GB" type="submit" formmethod="post"><img src="content/images/flags/England.png">
            </button>
            <input type="hidden" name="backURI" value="${pageContext.request.requestURI}">
        </form>
    </div>
    <div id="login-header">
        <c:choose>
            <c:when test="${sessionScope.containsKey('user')}">
                <a href="service/signOut"><fmt:message key="sign_out" bundle="${lbl}"/></a>
            </c:when>
            <c:otherwise>
                <a href="login"> <fmt:message key="sign_in" bundle="${lbl}"/></a>
                <a href="signup"> <fmt:message key="sign_up" bundle="${lbl}"/></a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

