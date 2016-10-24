<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: homie
  Date: 12.10.16
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:requestEncoding value="utf-8" />
<c:choose>
    <c:when test="${cookie.lang.value eq 'ru_RU'}">
        <fmt:setLocale value="ru_RU"/>
    </c:when>
    <c:when test="${cookie.lang.value eq 'en_GB'}">
        <fmt:setLocale value="en_GB"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="default"/>
    </c:otherwise>
</c:choose>

<fmt:setBundle basename="i18n.label" var="lbl" scope="application"/>
