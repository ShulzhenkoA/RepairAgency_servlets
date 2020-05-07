<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cust" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<div class="col-md-12">
    <form action="${pageContext.request.contextPath}/leave_review" method="post">
        <textarea id="review-textarea" class="form-control" name="comment-content" placeholder="<fmt:message key="cra.reviews.comment_area_pl"/>"></textarea>
        <div class="col-md-4 offset-md-8">
            <button type="submit" class="btn btn-block"><fmt:message key="cra.reviews.leave_review_btn"/></button>
        </div>
    </form>
    <hr>
</div>
<div class="col-md-12" id="review">
    <h5>Alfred</h5>
    <p>Phasellus consectetuer vestibulum elit. Sed cursus turpis vitae tortor.</p>
    <p id="reviewDateLink"><small>23.01.2016 4:23</small> | <a href="#"><fmt:message key="cra.reviews.reply_btn"/></a>
    </p>
    <hr>
</div>

<cust:pagination pagination_model="${paginationModel}"/>