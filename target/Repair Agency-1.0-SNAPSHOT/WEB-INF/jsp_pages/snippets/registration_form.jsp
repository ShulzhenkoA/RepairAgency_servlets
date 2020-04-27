<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] == '/registration' ? '' : ' modal-dialog-centered'}">
    <div class="modal-content">
        <div class="modal-header">
            <h4 class="modal-title">Реєстрація</h4>
        </div>
        <div class="modal-body">
            <form action="${pageContext.request.contextPath}/registration" method="post">
                <p class="formError">${inconsistencies.firstName}</p>
                <div class="form-group">
                    <input type="text" class="form-control" required placeholder="Введіть ім'я" name="fName"
                           value="${prevFName}">
                </div>
                <p class="formError">${inconsistencies.lastName}</p>
                <div class="form-group">
                    <input type="text" class="form-control" required placeholder="Введіть прізвище" name="lName"
                           value="${prevLName}">
                </div>
                <p class="formError">${inconsistencies.email}</p>
                <div class="form-group">
                    <input type="text" class="form-control" required placeholder="E-mail" name="email"
                           value="${prevEmail}">
                </div>
                <p class="formError">${inconsistencies.pass}</p>
                <div class="form-group">

                    <input type="password" class="form-control" required placeholder="Введіть пароль" name="pass">
                </div>
                <p class="formError">${inconsistencies.passConfirmation}</p>
                <div class="form-group">
                    <input type="password" class="form-control" required placeholder="Повторіть пароль" name="passConf">
                </div>
                <input type="hidden" value="REGISTERED_USER" name="role">
                <div class="col-sm-6 offset-sm-3 submit-button">
                    <button type="submit" class="btn btn-block">Зареєструватися</button>
                </div>
            </form>
        </div>
        <div class=" modal-footer">
            <div class="col-sm-12">
                <a <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.servlet_path'] == '/registration'}">
                        href="${pageContext.request.contextPath}/login"
                    </c:when>
                    <c:otherwise>
                        href="" data-toggle="modal" data-target="#loginForm" data-dismiss="modal"
                    </c:otherwise>
                </c:choose>>Я вже зареєстрований</a>
            </div>
        </div>
    </div>
</div>