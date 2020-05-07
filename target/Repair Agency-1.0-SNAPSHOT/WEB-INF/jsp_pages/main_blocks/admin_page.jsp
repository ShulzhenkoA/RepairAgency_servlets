<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>


<c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq '/admin_home'}">
    <c:choose>
        <c:when test="${not empty managers}">
            <h6>Managers registered in the system.</h6>
            <c:forEach var="manager" items="${managers}" varStatus="status">
                <cust:user_info loop_num="${status.count}" user_for_mapping="${manager}"/>
                <br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="col-md-12 rounded page-content">
                <h6>There are no registered managers in the system.</h6>
            </div>
        </c:otherwise>
    </c:choose>
    <br>
    <c:choose>
        <c:when test="${not empty masters}">
            <h6>Masters registered in the system.</h6>
            <c:forEach var="master" items="${masters}" varStatus="status">
                <cust:user_info loop_num="${status.count}" user_for_mapping="${master}"/>
                <br>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="col-md-12 rounded page-content">
                <h6>There are no registered masters in the system.</h6>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${requestScope['javax.servlet.forward.servlet_path'] eq '/man_mas_registration'}">
    <cust:registration_form/>
</c:if>