<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="cust" tagdir="/WEB-INF/tags" %>

<div class="col-md-12">
    <textarea id="review-textarea" name="comment-content" placeholder="Type a new comment"></textarea>

    <div class="col-md-4 offset-md-8">
        <a href="index222.html" class="btn btn-dark btn-block" role="button"> Залишити відгук</a>
    </div>
    <hr>
</div>
<div class="col-md-12" id="review">
    <h5>Alfred</h5>
    <p>Phasellus consectetuer vestibulum elit. Sed cursus turpis vitae tortor.</p>
    <p id="reviewDateLink"><small>23.01.2016 4:23</small> | <a href="#">Відповісти</a></p>
    <hr>
</div>

<div class="col-md-8 offset-md-4" >
    <div style="align-content: center">
        <cust:pagination pagination_model="${paginationModel}"/>
    </div>
</div>



