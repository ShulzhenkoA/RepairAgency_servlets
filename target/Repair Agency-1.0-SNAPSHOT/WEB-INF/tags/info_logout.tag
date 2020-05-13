<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.constants.CRAPaths" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<li class="nav-item">
    <div class="logout">
        <span>${user.firstName} ${user.lastName}</span>
        <a href="${pageContext.request.contextPath}${CRAPaths.LOGOUT}">
            <i class="fas fa-sign-out-alt"></i><fmt:message key="cra.logout"/></a>
    </div>
</li>