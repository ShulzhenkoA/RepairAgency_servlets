package ua.javaexternal_shulzhenko.repair_service.servlet.commands;

import ua.javaexternal_shulzhenko.repair_service.constants.Attributes;
import ua.javaexternal_shulzhenko.repair_service.constants.CRAPaths;
import ua.javaexternal_shulzhenko.repair_service.constants.CRA_JSPFiles;
import ua.javaexternal_shulzhenko.repair_service.constants.Parameters;
import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;
import ua.javaexternal_shulzhenko.repair_service.models.forms.*;
import ua.javaexternal_shulzhenko.repair_service.models.order.Order;
import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.authentication.UserAuthenticator;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.OrdersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.ReviewsDBService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.editing.EditingOrderValidator;
import ua.javaexternal_shulzhenko.repair_service.services.editing.imp.OrderEditor;
import ua.javaexternal_shulzhenko.repair_service.services.editing.imp.UserEditor;
import ua.javaexternal_shulzhenko.repair_service.services.validation.FormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PostRequestHandleCommands {
    public static final Map<String, RequestHandler> COMMANDS = new HashMap<>();

    static {
        COMMANDS.put(CRAPaths.LOGIN, (req, resp) -> {
            LoginForm loginForm = new LoginForm(req);
            try {
                Set<String> inconsistencies = FormValidator.validateForm(loginForm);
                if (inconsistencies.isEmpty()) {
                    User user = UserAuthenticator.authenticate(loginForm);
                    addUserToSession(req, user);
                    String targetPath = defineTargetPathAfterLogin(req, user);
                    resp.sendRedirect(req.getContextPath() + targetPath);
                } else {
                    if (inconsistencies.contains(Parameters.EMAIL)) {
                        throw new VerificationException(VerificationException.VerificationExceptionType.EMAIL);
                    } else {
                        throw new VerificationException(VerificationException.VerificationExceptionType.PASS);
                    }
                }
            } catch (VerificationException exc) {
                req.setAttribute(exc.getType().name(), "");
                req.setAttribute(Attributes.PREV_FORM, loginForm);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.LOGIN_MAIN_BLOCK);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                throw new VerificationException(exc.getType());
            }
        });

        COMMANDS.put(CRAPaths.REGISTRATION, (req, resp) -> {
            RegistrationForm registrationForm = new RegistrationForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(registrationForm);
            if (inconsistencies.isEmpty()) {
                UsersDBService.createUser(registrationForm);
                req.setAttribute(Attributes.SUCCESS, "");
            } else {
                req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
                req.setAttribute(Attributes.PREV_FORM, registrationForm);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.REGISTRATION_MAIN_BLOCK);
            req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
        });

        COMMANDS.put(CRAPaths.MAN_MAS_REGISTRATION, (req, resp) -> {
            RegistrationForm registrationForm = new RegistrationForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(registrationForm);
            if (inconsistencies.isEmpty()) {
                UsersDBService.createUser(registrationForm);
                req.setAttribute(Attributes.SUCCESS, "");
            } else {
                req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
                req.setAttribute(Attributes.PREV_FORM, registrationForm);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
            req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ADMIN_PAGE);
            req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
        });

        COMMANDS.put(CRAPaths.CREATE_ORDER, (req, resp) -> {
            OrderForm orderForm = new OrderForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(orderForm);
            if (inconsistencies.isEmpty()) {
                OrdersDBService.addOrder(orderForm);
                Order order = OrdersDBService.getLastOrderForRegUser(orderForm.getUser().getId());
                req.setAttribute(Attributes.MADE_ORDER, order);
            } else {
                req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
                req.setAttribute(Attributes.PREV_FORM, orderForm);
            }
            req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
            req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ORDER_FORM);
            req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
        });

        COMMANDS.put(CRAPaths.DELETE_USER, (req, resp) -> {
            int userId = Integer.parseInt(req.getParameter(Parameters.DELETING_USER_ID));
            UsersDBService.deleteUser(userId);
            resp.sendRedirect(req.getContextPath() + CRAPaths.ADMIN_HOME);
        });

        COMMANDS.put(CRAPaths.EDIT_USER, (req, resp) -> {
            UserEditingForm form = new UserEditingForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(form);
            if (inconsistencies.isEmpty()) {
                User user = UsersDBService.getUserByID(form.getId());
                new UserEditor(form, user).
                        compareFirstName().
                        compareLastName().
                        compareEmail().
                        compareRole().edit();
                resp.sendRedirect(req.getContextPath() + CRAPaths.ADMIN_HOME);
            } else {
                req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
                req.setAttribute(Attributes.PREV_FORM, form);
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.USER_EDITING_MAIN_BLOCK);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
            }
        });

        COMMANDS.put(CRAPaths.EDIT_ORDER, (req, resp) -> {
            OrderEditingForm form = new OrderEditingForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(form);
            EditingOrderValidator.checkIfNeedMasterForThisStatus(form, inconsistencies);
            EditingOrderValidator.checkIfNeedPreviousPrice(form, inconsistencies);
            if (inconsistencies.isEmpty()) {
                Order order = OrdersDBService.getOrderById(form.getId());
                new OrderEditor(form, order).
                        comparePrice().
                        compareMasters().
                        compareStatus().
                        compareManagerComment().edit();
                resp.sendRedirect(req.getContextPath() + CRAPaths.MANAGER_HOME);
            } else {
                List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
                Order order = OrdersDBService.getOrderById(form.getId());
                req.setAttribute(Attributes.CUR_ORDER_STATUS, order.getStatus());
                req.setAttribute(Attributes.MASTERS, masters);
                req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
                req.setAttribute(Attributes.PREV_FORM, form);
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ORDER_EDITING_MAIN_BLOCK);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
            }
        });

        COMMANDS.put(CRAPaths.EDIT_STATUS, (req, resp) -> {
            String status = req.getParameter(Parameters.STATUS);
            String orderID = req.getParameter(Parameters.ORDER_ID);
            if (status.equals(OrderStatus.REPAIR_WORK.name())) {
                OrdersDBService.editOrderStatus(orderID, status);
            } else if (status.equals(OrderStatus.REPAIR_COMPLETED.name())) {
                OrdersDBService.editOrderStatusCompletionDate(orderID, status, LocalDateTime.now());
            }
            resp.sendRedirect(req.getContextPath() + CRAPaths.MASTER_HOME);
        });

        COMMANDS.put(CRAPaths.REVIEWS, (req, resp) -> {
            ReviewForm form = new ReviewForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(form);
            if (inconsistencies.isEmpty()) {
                ReviewsDBService.addReview(form);
                req.setAttribute(Attributes.SUCCESS, "");
            } else {
                req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
                req.setAttribute(Attributes.PREV_FORM, form);
            }
            GetRequestsHandleCommands.COMMANDS.get(CRAPaths.REVIEWS).handleRequest(req, resp);
        });
    }

    private static String defineTargetPathAfterLogin(HttpServletRequest req, User user) {
        switch (user.getRole()) {
            case CUSTOMER:
                String path = (String) req.getSession().getAttribute(Attributes.TO_CREATE_ORDER);
                if (path != null) {
                    return path;
                } else {
                    return CRAPaths.CUSTOMER_HOME;
                }
            case ADMIN:
                return CRAPaths.ADMIN_HOME;
            case MANAGER:
                return CRAPaths.MANAGER_HOME;
            case MASTER:
                return CRAPaths.MASTER_HOME;
            default:
                return CRAPaths.HOME;
        }

    }

    private static void addUserToSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute(Attributes.USER, user);
    }
}
