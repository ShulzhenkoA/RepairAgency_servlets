<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog${requestScope['javax.servlet.forward.servlet_path'] == '/login' ? '' : ' modal-dialog-centered'}">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title">Вхід</h4>
        </div>
        <div class="modal-body">
            <p class="formError">${errorMessage}</p>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="E-mail" name="email" value="${prevEmail}">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="Введіть пароль" name="pass">
                </div>
                <div class="col-sm-6 offset-sm-3 submit-button">
                    <button type="submit" class="btn btn-block">Увійти</button>
                </div>
            </form>
        </div>
        <div class=" modal-footer">
            <div class="col-sm-12">
                У вас ще немає облікового запису?
                <a <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/login'}">
                        href="${pageContext.request.contextPath}/registration"
                    </c:when>
                    <c:otherwise>
                        href="" data-toggle="modal" data-target="#registerForm" data-dismiss="modal"
                    </c:otherwise>
                </c:choose>> Зареєструватися</a>
            </div>
        </div>
    </div>
</div>