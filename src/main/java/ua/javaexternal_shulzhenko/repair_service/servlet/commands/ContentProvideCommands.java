package ua.javaexternal_shulzhenko.repair_service.servlet.commands;

import ua.javaexternal_shulzhenko.repair_service.constants.Attributes;
import ua.javaexternal_shulzhenko.repair_service.constants.CRAPaths;
import ua.javaexternal_shulzhenko.repair_service.constants.CRA_JSPFiles;
import ua.javaexternal_shulzhenko.repair_service.constants.Parameters;
import ua.javaexternal_shulzhenko.repair_service.models.order.Order;
import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PageEntities;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationConstants;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationModel;
import ua.javaexternal_shulzhenko.repair_service.models.review.Review;
import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.OrdersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.ReviewsDBService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.pagination.PagePaginationHandler;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentProvideCommands {

    public static final Map<String, RequestHandler> COMMANDS = new HashMap<>();
    private static final PagePaginationHandler pagePaginationHandler = new PagePaginationHandler();

    static {

        COMMANDS.put(CRAPaths.LOGIN, (req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.LOGIN_MAIN_BLOCK));

        COMMANDS.put(CRAPaths.REGISTRATION, (req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.REGISTRATION_MAIN_BLOCK));

        COMMANDS.put(CRAPaths.EDIT_USER, (req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.USER_EDITING_MAIN_BLOCK));

        COMMANDS.put(CRAPaths.EDIT_ORDER, (req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.ORDER_EDITING_MAIN_BLOCK));

        COMMANDS.put(CRAPaths.CREATE_ORDER, (req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.ORDER_FORM));

        COMMANDS.put(CRAPaths.ERROR404, (req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.PAGE404));

        COMMANDS.put(CRAPaths.ERROR500, (req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.PAGE500));

        COMMANDS.put(CRAPaths.MAN_MAS_REGISTRATION,(req, resp) -> dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.ADMIN_PAGE));

        COMMANDS.put(CRAPaths.HOME, (req, resp) -> {
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Review> reviews = ReviewsDBService.getReviewsByOffsetAmount(offset,
                    PaginationConstants.REVIEWS_FOR_HOME.getAmount());
            req.setAttribute(Attributes.REVIEWS, reviews.getEntities());
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.COMMON_HOME);
        });

        COMMANDS.put(CRAPaths.LOGOUT, (req, resp) -> {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + CRAPaths.HOME);
        });

        COMMANDS.put(CRAPaths.CUSTOMER_HOME,(req, resp) -> {
            User user = getUserFromSession(req);
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Order> orders = OrdersDBService.getCustomerOrdersByOffsetAmountTwoExcludeStatuses(user, offset,
                    PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                    OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
            req.setAttribute(Attributes.ORDERS, orders.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.CUSTOMER_MASTER_PAGE);
        });

        COMMANDS.put(CRAPaths.CUSTOMER_ORDER_HISTORY,(req, resp) -> {
            User user = getUserFromSession(req);
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Order> orders = OrdersDBService.getCustomerOrdersByOffsetAmountTwoStatuses(user, offset,
                    PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                    OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
            req.setAttribute(Attributes.ORDERS, orders.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.CUSTOMER_MASTER_PAGE);
        });

        COMMANDS.put(CRAPaths.MANAGER_HOME,(req, resp) -> {
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Order> orders = OrdersDBService.getManagerOrdersByOffsetAmountStatus(offset,
                    PaginationConstants.ORDERS_FOR_PAGE.getAmount(), OrderStatus.PENDING);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
            List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
            req.setAttribute(Attributes.ORDERS, orders.getEntities());
            req.setAttribute(Attributes.MASTERS, masters);
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.MANAGER_HOME);
        });

        COMMANDS.put(CRAPaths.ACTIVE_ORDERS,(req, resp) -> {
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Order> orders = OrdersDBService.getManagerOrdersByAmountOffsetMultipleExcludeStatuses(offset,
                    PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                    OrderStatus.PENDING, OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
            List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
            req.setAttribute(Attributes.ORDERS, orders.getEntities());
            req.setAttribute(Attributes.MASTERS, masters);
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.MANAGER_HOME);
        });

        COMMANDS.put(CRAPaths.ORDER_HISTORY,(req, resp) -> {
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Order> orders = OrdersDBService.getManagerOrdersByOffsetAmountTwoStatuses(offset,
                    PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                    OrderStatus.ORDER_COMPLETED, OrderStatus.REJECTED);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
            req.setAttribute(Attributes.ORDERS, orders.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.MANAGER_HOME);
        });

        COMMANDS.put(CRAPaths.CUSTOMERS,(req, resp) -> {
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<User> customers = UsersDBService.getUsersByRoleOffsetAmount(Role.CUSTOMER,
                    offset, PaginationConstants.USERS_FOR_PAGE.getAmount());
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, customers.getEntitiesTotalAmount(), PaginationConstants.USERS_FOR_PAGE.getAmount());
            req.setAttribute(Attributes.CUSTOMERS, customers.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.MANAGER_HOME);

        });

        COMMANDS.put(CRAPaths.MASTERS,(req, resp) -> {
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<User> masters = UsersDBService.getUsersByRoleOffsetAmount(Role.MASTER,
                    offset, PaginationConstants.USERS_FOR_PAGE.getAmount());
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, masters.getEntitiesTotalAmount(), PaginationConstants.USERS_FOR_PAGE.getAmount());
            req.setAttribute(Attributes.MASTERS, masters.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.MANAGER_HOME);
        });

        COMMANDS.put(CRAPaths.MASTER_HOME,(req, resp) -> {
            User user = getUserFromSession(req);
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Order> orders = OrdersDBService.getMasterOrdersByOffsetAmountTwoExcludeStatuses(user, offset,
                    PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                    OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
            req.setAttribute(Attributes.ORDERS, orders.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.CUSTOMER_MASTER_PAGE);
        });

        COMMANDS.put(CRAPaths.MASTER_COMPLETED_ORDERS,(req, resp) -> {
            User user = getUserFromSession(req);
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Order> orders = OrdersDBService.getMasterOrdersByOffsetAmountTwoStatuses(user, offset,
                    PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                    OrderStatus.REJECTED, OrderStatus.ORDER_COMPLETED);
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                    pageNum, orders.getEntitiesTotalAmount(), PaginationConstants.ORDERS_FOR_PAGE.getAmount());
            req.setAttribute(Attributes.ORDERS, orders.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.CUSTOMER_MASTER_PAGE);
        });

        COMMANDS.put(CRAPaths.ADMIN_HOME,(req, resp) -> {
            List<User> managers = UsersDBService.getUsersByRole(Role.MANAGER);
            List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
            req.setAttribute(Attributes.MANAGERS, managers);
            req.setAttribute(Attributes.MASTERS, masters);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.ADMIN_PAGE);
        });

        COMMANDS.put(CRAPaths.REVIEWS,(req, resp) -> {
            int pageNum = extractPageNum(req);
            int offset = computeOffset(pageNum);
            PageEntities<Review> reviews = ReviewsDBService.getReviewsByOffsetAmount(
                    offset, PaginationConstants.REVIEWS_FOR_REVIEW.getAmount());
            PaginationModel paginationModel = pagePaginationHandler.createPaginationModel(
                    req.getRequestURI(), pageNum,
                    reviews.getEntitiesTotalAmount(), PaginationConstants.REVIEWS_FOR_REVIEW.getAmount());
            req.setAttribute(Attributes.REVIEWS, reviews.getEntities());
            req.setAttribute(Attributes.PG_MODEL, paginationModel);
            dispatchToCorePageWithBlock(req, resp, CRA_JSPFiles.REVIEWS);
        });
    }

    private ContentProvideCommands() {}

    private static void dispatchToCorePageWithBlock(HttpServletRequest req, HttpServletResponse resp, String block)
            throws ServletException, IOException {
        req.setAttribute(Attributes.MAIN_BLOCK, block);
        req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE).forward(req, resp);
    }

    private static int extractPageNum(HttpServletRequest req) {
        String page = req.getParameter(Parameters.PAGE);
        if (page != null) {
            return Integer.parseInt(page);
        } else {
            return 1;
        }
    }

    private static int computeOffset(int pageNum) {
        return (pageNum - 1) * PaginationConstants.ORDERS_FOR_PAGE.getAmount();
    }

    private static User getUserFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute(Attributes.USER);
    }
}