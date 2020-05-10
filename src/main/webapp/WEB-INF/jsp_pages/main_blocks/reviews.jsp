<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cust" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<div class="col-md-12">
    <c:if test="">

    </c:if>
    <c:choose>
        <c:when test="${user.role ne Role.CUSTOMER and user.role ne Role.UNKNOWN}">
            <div class="col-md-8 red-mess">Only customer can leave a review.</div>
        </c:when>
        <c:when test="${user.role ne Role.CUSTOMER}">
            <div class="col-md-8 red-mess">Log in if you want write a review.</div>
        </c:when>
        <c:otherwise>
            <c:if test="${success eq null}">
                <c:if test="${inconsistencies.contains('reviewContent')}">
                    <p class="formError">
                        You can't leave empty review.
                    </p>
                </c:if>
                <form action="${pageContext.request.contextPath}/leave_review" method="post">
                <textarea id="review-textarea" class="form-control" name="reviewContent" required
                          placeholder="<fmt:message key="cra.reviews.comment_area_pl"/>"></textarea>
                    <input type="hidden" name="customerID" value="${user.id}">
                    <div class="col-md-4 offset-md-8">
                        <button type="submit" class="btn btn-block"><fmt:message
                                key="cra.reviews.leave_review_btn"/></button>
                    </div>
                </form>
            </c:if>
            <c:if test="${reviewCreated ne null}">
                Your review was successfully added.
            </c:if>
        </c:otherwise>
    </c:choose>
    <hr>
</div>

<div class="col-md-12" id="review">
    <c:forEach var="review" items="${reviews}">
        <h5>${review.customer.firstName}</h5>
        <p>${review.reviewContent}</p>
        <p id="reviewDate"><small>${review.dateTime}</small></p>
        <hr>
    </c:forEach>
</div>

<cust:pagination pagination_model="${pgModel}"/>