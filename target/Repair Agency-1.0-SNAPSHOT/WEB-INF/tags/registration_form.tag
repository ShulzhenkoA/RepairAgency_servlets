<%@ tag pageEncoding="UTF-8" %>
<%@ tag import="ua.javaexternal_shulzhenko.repair_service.models.user.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>


<c:choose>
    <c:when test="${userWasRegistered eq null}">
        <div class="modal-dialog ${requestScope['javax.servlet.forward.servlet_path'] eq '/registration' ? '' : ' modal-dialog-centered'}">
            <div class="modal-content">
                <div class="modal-header">
                    <c:choose>
                        <c:when test="${user.role.name() eq Role.ADMIN}">
                            <h4 class="modal-title">New manager/master registration</h4>
                        </c:when>
                        <c:otherwise>
                            <h4 class="modal-title"><fmt:message key="cra.reg_form.header"/></h4>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="modal-body">
                    <form action="<c:choose>
                            <c:when test="${user.role.name() eq Role.ADMIN}">
                                ${pageContext.request.contextPath}/man_mas_registration
                            </c:when>
                            <c:otherwise>
                                ${pageContext.request.contextPath}/registration
                            </c:otherwise>
                        </c:choose>" method="post">
                        <c:if test="${inconsistencies.firstName ne null}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.f_name"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="text" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.f_name_pl"/>" name="fName"
                                   value="${prevForm.firstName}">
                        </div>
                        <c:if test="${inconsistencies.lastName ne null}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.l_name"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="text" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.l_name_pl"/>" name="lName"
                                   value="${prevForm.lastName}">
                        </div>
                        <c:if test="${inconsistencies.email ne null}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.email"/>
                            </p>
                        </c:if>
                        <c:if test="${inconsistencies.notFreeEmail ne null}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.not_free_email"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="text" class="form-control" required placeholder="E-mail" name="email"
                                   value="${prevForm.email}">
                        </div>

                        <c:if test="${inconsistencies.password ne null}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.pass"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="password" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.pass_pl"/>" name="pass">
                        </div>
                        <c:if test="${inconsistencies.passwordConfirmation ne null}">
                            <p class="formError">
                                <fmt:message key="cra.reg_form.inc.pass_conf"/>
                            </p>
                        </c:if>
                        <div class="form-group">
                            <input type="password" class="form-control" required
                                   placeholder="<fmt:message key="cra.reg_form.pass_conf_pl"/>" name="passConf">
                        </div>
                        <c:choose>
                            <c:when test="${user.role.name() eq 'ADMIN'}">
                                <div class="form-group reg_radio">
                                    <c:if test="${inconsistencies.role ne null}">
                                        <p class="formError">
                                            <fmt:message key="cra.reg_form.inc.role"/>
                                        </p>
                                    </c:if>
                                    <div class="form-check-inline">
                                        <input type="radio" class="form-check-input" name="role" value="MASTER"
                                            ${prevForm.role eq Role.MASTER ? 'checked' : ''}>Master
                                    </div>
                                    <div class="form-check-inline">
                                        <input type="radio" class="form-check-input" name="role" value="MANAGER"
                                            ${prevForm.role eq Role.MANAGER ? 'checked' : ''}>Manager
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" value="REGISTERED_USER" name="role">
                            </c:otherwise>
                        </c:choose>
                        <div class="col-sm-6 offset-sm-3 submit-button">
                            <button type="submit" class="btn btn-block"><fmt:message
                                    key="cra.reg_form.reg_btn"/></button>
                        </div>
                    </form>
                </div>
                <div class=" modal-footer">
                    <c:if test="${user.role.name() ne Role.ADMIN}">
                        <div class="col-sm-12">
                            <a <c:choose>
                                <c:when test="${requestScope['javax.servlet.forward.servlet_path'] eq '/registration'}">
                                    href="${pageContext.request.contextPath}/login"
                                </c:when>
                                <c:otherwise>
                                    href="" data-toggle="modal" data-target="#loginForm" data-dismiss="modal"
                                </c:otherwise>
                            </c:choose>><fmt:message key="cra.reg_form.alr_reg_btn"/></a>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div id="scc_reg" class="col-md-12" >
            <c:if test="${param.role eq Role.CUSTOMER}">
                <h3>You have successfully registered</h3>
                <a href="${pageContext.request.contextPath}/login">Go to the login page</a>
            </c:if>
            <c:if test="${param.role eq Role.MASTER}"><h3>The master's account has been successfully registered</h3></c:if>
            <c:if test="${param.role eq Role.MANAGER}"><h3>The manager's account has been successfully registered</h3></c:if>
        </div>
    </c:otherwise>
</c:choose>