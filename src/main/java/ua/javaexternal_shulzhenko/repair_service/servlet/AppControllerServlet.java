package ua.javaexternal_shulzhenko.repair_service.servlet;

import ua.javaexternal_shulzhenko.repair_service.constants.Attributes;
import ua.javaexternal_shulzhenko.repair_service.constants.CRA_JSPFiles;
import ua.javaexternal_shulzhenko.repair_service.constants.Parameters;
import ua.javaexternal_shulzhenko.repair_service.constants.CRAPaths;
import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PageEntities;
import ua.javaexternal_shulzhenko.repair_service.models.forms.*;
import ua.javaexternal_shulzhenko.repair_service.models.order.Order;
import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationConstants;
import ua.javaexternal_shulzhenko.repair_service.models.review.Review;
import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.OrdersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.ReviewsDBService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.authentication.UserAuthenticator;
import ua.javaexternal_shulzhenko.repair_service.services.editing.EditingOrderValidator;
import ua.javaexternal_shulzhenko.repair_service.services.editing.imp.OrderEditor;
import ua.javaexternal_shulzhenko.repair_service.services.editing.imp.UserEditor;
import ua.javaexternal_shulzhenko.repair_service.services.pagination.PagePaginationHandler;
import ua.javaexternal_shulzhenko.repair_service.services.validation.FormValidator;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = {CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.CUSTOMER_HOME, CRAPaths.CUSTOMER_ORDER_HISTORY,
        CRAPaths.REGISTRATION, CRAPaths.LOGIN, CRAPaths.CONTACTS, CRAPaths.LEAVE_REVIEW,
        CRAPaths.CREATE_ORDER, CRAPaths.MANAGER_HOME, CRAPaths.LOGOUT,
        CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER, CRAPaths.DELETE_USER,
        CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER,
        CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.ERROR404, CRAPaths.ERROR500})
public class AppControllerServlet extends HttpServlet {

    private PagePaginationHandler pagePaginationHandler;

    @Override
    public void init() throws ServletException {
        pagePaginationHandler = new PagePaginationHandler();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();

        User user;
        int pageNum;
        int offset;
        PageEntities<Order> orders;
        PaginationModel paginationModel;
        PageEntities<Review> reviews;
        switch (servletPath) {
            case "/home":
            case "/contacts":
                pageNum = extractPageNum(req);
                offset = computeOffset(pageNum);
                reviews = ReviewsDBService.getReviewsByOffsetAmount(offset,
                        PaginationConstants.REVIEWS_FOR_HOME.getAmount());
                req.setAttribute(Attributes.REVIEWS, reviews.getEntities());
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.COMMON_HOME);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/login":
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.LOGIN_MAIN_BLOCK);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/logout":
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + CRAPaths.HOME);
                break;
            case "/customer_home":
            case "/customer_order_history":
                user = getUserFromSession(req);
                pageNum = extractPageNum(req);
                offset = computeOffset(pageNum);
                if (servletPath.equals("/customer_home")) {
                    orders = OrdersDBService.getCustomerOrdersByOffsetAmountTwoExcludeStatuses(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                            OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
                } else {
                    orders = OrdersDBService.getCustomerOrdersByOffsetAmountTwoStatuses(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                            OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
                }
                paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                        pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                req.setAttribute(Attributes.ORDERS, orders.getEntities());
                req.setAttribute(Attributes.PG_MODEL, paginationModel);
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.CUSTOMER_MASTER_PAGE);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/manager_home":
            case "/active_orders":
            case "/order_history":
            case "/customers":
            case "/masters":
                pageNum = extractPageNum(req);
                offset = computeOffset(pageNum);

                if (servletPath.equals("/manager_home")) {
                    orders = OrdersDBService.getManagerOrdersByOffsetAmountStatus(offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(), OrderStatus.PENDING);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                    List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
                    req.setAttribute(Attributes.ORDERS, orders.getEntities());
                    req.setAttribute(Attributes.MASTERS, masters);
                } else if (servletPath.equals("/active_orders")) {
                    orders = OrdersDBService.getManagerOrdersByAmountOffsetMultipleExcludeStatuses(offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                            OrderStatus.PENDING, OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                    List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
                    req.setAttribute(Attributes.ORDERS, orders.getEntities());
                    req.setAttribute(Attributes.MASTERS, masters);
                } else if (servletPath.equals("/order_history")) {
                    orders = OrdersDBService.getManagerOrdersByOffsetAmountTwoStatuses(offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                            OrderStatus.ORDER_COMPLETED, OrderStatus.REJECTED);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                    req.setAttribute(Attributes.ORDERS, orders.getEntities());
                } else if (servletPath.equals("/customers")) {
                    PageEntities<User> customers = UsersDBService.getUsersByRoleOffsetAmount(Role.CUSTOMER,
                            offset, PaginationConstants.USERS_FOR_PAGE.getAmount());
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, customers.getEntitiesTotalAmount(), PaginationConstants.USERS_FOR_PAGE.getAmount());
                    req.setAttribute(Attributes.CUSTOMERS, customers.getEntities());
                } else {
                    PageEntities<User> masters = UsersDBService.getUsersByRoleOffsetAmount(Role.MASTER,
                            offset, PaginationConstants.USERS_FOR_PAGE.getAmount());
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, masters.getEntitiesTotalAmount(), PaginationConstants.USERS_FOR_PAGE.getAmount());
                    req.setAttribute(Attributes.MASTERS, masters.getEntities());
                }
                req.setAttribute(Attributes.PG_MODEL, paginationModel);
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.MANAGER_HOME);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/edit_order":
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ORDER_EDITING_MAIN_BLOCK);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/master_home":
            case "/master_completed_orders":
                user = getUserFromSession(req);
                pageNum = extractPageNum(req);
                offset = computeOffset(pageNum);
                if (servletPath.equals("/master_home")) {
                    orders = OrdersDBService.getMasterOrdersByOffsetAmountTwoExcludeStatuses(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                            OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
                } else {
                    orders = OrdersDBService.getMasterOrdersByOffsetAmountTwoStatuses(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                            OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
                }
                paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                        pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                req.setAttribute(Attributes.ORDERS, orders.getEntities());
                req.setAttribute(Attributes.PG_MODEL, paginationModel);
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.CUSTOMER_MASTER_PAGE);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/admin_home":
            case "/man_mas_registration":
                if (servletPath.equals("/admin_home")) {
                    List<User> managers = UsersDBService.getUsersByRole(Role.MANAGER);
                    List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
                    req.setAttribute(Attributes.MANAGERS, managers);
                    req.setAttribute(Attributes.MASTERS, masters);
                }
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ADMIN_PAGE);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/edit_user":
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.USER_EDITING_MAIN_BLOCK);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/reviews":
            case "/leave_review":
                pageNum = extractPageNum(req);
                offset = computeOffset(pageNum);
                reviews = ReviewsDBService.getReviewsByOffsetAmount(
                        offset, PaginationConstants.REVIEWS_FOR_REVIEW.getAmount());
                paginationModel = pagePaginationHandler.createPaginationModel(
                        req.getRequestURI(), pageNum,
                        reviews.getEntitiesTotalAmount(), PaginationConstants.REVIEWS_FOR_REVIEW.getAmount());
                req.setAttribute(Attributes.REVIEWS, reviews.getEntities());
                req.setAttribute(Attributes.PG_MODEL, paginationModel);
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.REVIEWS);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/create_order":
                req.setAttribute(Attributes.ASIDE_MENU, CRA_JSPFiles.ASIDE_MENU);
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.ORDER_FORM);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/registration":
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.REGISTRATION_MAIN_BLOCK);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/error404":
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.PAGE404);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            case "/error500":
                req.setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.PAGE500);
                req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
                break;
            default:
                resp.getWriter().print("THIS PATH DON'T PROCESS");
                break;
        }
    }

    private int extractPageNum(HttpServletRequest req) {
        String page = req.getParameter(Parameters.PAGE);
        if (page != null) {
            return Integer.parseInt(page);
        } else {
            return 1;
        }
    }

    private int computeOffset(int pageNum) {
        return (pageNum - 1) * PaginationConstants.ORDERS_FOR_PAGE.getAmount();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, VerificationException {
        if (req.getServletPath().equals("/login")) {
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
                doGet(req, resp);
                throw new VerificationException(exc.getType());
            }

        } else if (req.getServletPath().equals("/registration") || req.getServletPath().equals("/man_mas_registration")) {
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
            doGet(req, resp);

        } else if (req.getServletPath().equals("/create_order")) {
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
            doGet(req, resp);

        } else if (req.getServletPath().equals("/delete_user")) {
            int userId = Integer.parseInt(req.getParameter(Parameters.DELETING_USER_ID));
            UsersDBService.deleteUser(userId);
            resp.sendRedirect(req.getContextPath() + CRAPaths.ADMIN_HOME);
        } else if (req.getServletPath().equals("/edit_user")) {
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
                doGet(req, resp);
            }

        } else if (req.getServletPath().equals("/edit_order")) {
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
                doGet(req, resp);
            }

        } else if (req.getServletPath().equals("/edit_status")) {
            String status = req.getParameter(Parameters.STATUS);
            String orderID = req.getParameter(Parameters.ORDER_ID);
            if (status.equals(OrderStatus.REPAIR_WORK.name())) {
                OrdersDBService.editOrderStatus(orderID, status);
            } else if (status.equals(OrderStatus.REPAIR_COMPLETED.name())) {
                OrdersDBService.editOrderStatusCompletionDate(orderID, status, LocalDateTime.now());
            }
            resp.sendRedirect(req.getContextPath() + CRAPaths.MASTER_HOME);
        } else if (req.getServletPath().equals("/leave_review")) {
            ReviewForm form = new ReviewForm(req);
            Set<String> inconsistencies = FormValidator.validateForm(form);
            if (inconsistencies.isEmpty()) {
                ReviewsDBService.addReview(form);
                req.setAttribute(Attributes.SUCCESS, "");
            } else {
                req.setAttribute(Attributes.INCONSISTENCIES, inconsistencies);
                req.setAttribute(Attributes.PREV_FORM, form);
            }
            doGet(req, resp);
        }
    }

    private String defineTargetPathAfterLogin(HttpServletRequest req, User user) {
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

    private void addUserToSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute(Attributes.USER, user);
    }

    private User getUserFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute(Attributes.USER);
    }
}