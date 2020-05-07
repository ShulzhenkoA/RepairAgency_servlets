<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cust" %>
<%@ page import="ua.javaexternal_shulzhenko.repair_service.models.order.RepairType" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="ra_language"/>

<c:choose>
    <c:when test="${madeOrder eq null}">
        <div class="col-md-8 offset-md-2 rounded page-content">
            <form action="${pageContext.request.contextPath}/create_order" method="post">
                <h3><fmt:message key="cra.repair_order.header"/></h3>
                <div class="col-lg-12 form-group">
                    <c:if test="${inconsistencies.carBrand ne null}">
                        <p class="formError">
                            Car Brand can`t be empty, start with slash or number, have less than 2 letters or digits,
                            can have only .- signs.
                        </p>
                    </c:if>
                    <input type="text" class="form-control" required placeholder="Car brand" name="car_brand"
                           value="${prevForm.carBrand}">
                </div>
                <div class="col-lg-12 form-group">
                    <c:if test="${inconsistencies.carModel ne null}">
                        <p class="formError">
                            Car Model can`t be empty, start with slash, have less than 2 letters or digits, can have
                            only .- signs.
                        </p>
                    </c:if>
                    <input type="text" class="form-control" required placeholder="Car model" name="car_model"
                           value="${prevForm.carModel}">
                </div>
                <div class="col-lg-5 form-group">
                    <c:if test="${inconsistencies.carYear ne null}">
                        <p class="formError">
                            Year 19xx or 20xx
                        </p>
                    </c:if>
                    <input type="text" class="form-control" required placeholder="Car year of manufacture"
                           name="car_year" value="${prevForm.carYear}">
                </div>
                <div class="col-lg-5 form-group">
                    <c:if test="${inconsistencies.repairType ne null}">
                        <p class="formError">
                            Choose repair type
                        </p>
                    </c:if>
                    <select name="repair_type" class="custom-select mb-3">
                        <c:choose>
                            <c:when test="${prevForm.repairType eq null}">
                                <option selected>Repair type</option>
                                <option value="${RepairType.ENGINE_REPAIR}">Engine repair</option>
                                <option value="${RepairType.CHASSIS_REPAIR}">Chassis repair</option>
                                <option value="${RepairType.TRANSMISSION_REPAIR}">Transmission repair</option>
                                <option value="${RepairType.BATTERY_REPLACEMENT}">Battery replacement</option>
                                <option value="${RepairType.OIL_CHANGE}">Oil change</option>
                                <option value="${RepairType.PAINTING_WORKS}">Painting works</option>
                                <option value="${RepairType.OTHER}">Other</option>
                            </c:when>
                            <c:otherwise>
                                <option>Repair type</option>
                                <option ${prevForm.repairType eq RepairType.ENGINE_REPAIR ? 'selected' : ''}
                                        value="${RepairType.ENGINE_REPAIR}">Engine repair
                                </option>
                                <option ${prevForm.repairType eq RepairType.CHASSIS_REPAIR ? 'selected' : ''}
                                        value="${RepairType.CHASSIS_REPAIR}">Chassis repair
                                </option>
                                <option ${prevForm.repairType eq RepairType.TRANSMISSION_REPAIR ? 'selected' : ''}
                                        value="${RepairType.TRANSMISSION_REPAIR}">Transmission repair
                                </option>
                                <option ${prevForm.repairType eq RepairType.BATTERY_REPLACEMENT ? 'selected' : ''}
                                        value="${RepairType.BATTERY_REPLACEMENT}">Battery replacement
                                </option>
                                <option ${prevForm.repairType eq RepairType.OIL_CHANGE ? 'selected' : ''}
                                        value="${RepairType.OIL_CHANGE}">Oil change
                                </option>
                                <option ${prevForm.repairType eq RepairType.PAINTING_WORKS ? 'selected' : ''}
                                        value="${RepairType.PAINTING_WORKS}">Painting works
                                </option>
                                <option ${prevForm.repairType eq RepairType.OTHER ? 'selected' : ''}
                                        value="${RepairType.OTHER}">Other
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="col-lg-12 form-group">
                    <c:if test="${inconsistencies.repairDescription ne null}">
                        <p class="formError">
                            Enter your car breakdown description
                        </p>
                    </c:if>
                    <textarea class="form-control" name="repair_description"
                              placeholder="Describe your vehicle breakdown or repair that you would like to do.">${prevForm.repairDescription}</textarea>
                </div>
                <div id="order_footer" class="col-lg-12 row">
                    <div class="col-lg-8">
                        <p>To make repair order you must be registered.</p>
                    </div>
                    <div class="col-lg-4">
                        <button type="submit" class="btn btn-block">Make order</button>
                    </div>
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <h3>Order was successfully submitted</h3>
        <cust:order loop_num="" order_obejct="${madeOrder}"/>
    </c:otherwise>
</c:choose>

