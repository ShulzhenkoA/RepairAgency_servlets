<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="cust" tagdir="/WEB-INF/tags" %>

<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="ra_language"/>

<div class="col-md-12">
    <textarea id="review-textarea" name="comment-content"
              placeholder="<fmt:message key="ra.reviews.comment_area_pl"/>"></textarea>

    <div class="col-md-4 offset-md-8">
        <a href="index222.html" class="btn btn-dark btn-block" role="button"><fmt:message
                key="ra.reviews.leave_review_btn"/></a>
    </div>
    <hr>
</div>
<div class="col-md-12" id="review">
    <h5>Alfred</h5>
    <p>Phasellus consectetuer vestibulum elit. Sed cursus turpis vitae tortor.</p>
    <p id="reviewDateLink"><small>23.01.2016 4:23</small> | <a href="#"><fmt:message key="ra.reviews.reply_btn"/></a>
    </p>
    <hr>
</div>

<div class="col-md-8 offset-md-4">
    <div style="align-content: center">
        <cust:pagination pagination_model="${paginationModel}"/>
    </div>
</div>



