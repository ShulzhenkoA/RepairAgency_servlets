package ua.javaexternal_shulzhenko.repair_service.servlet;

import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;
import ua.javaexternal_shulzhenko.repair_service.models.order.Order;
import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationConstants;
import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.OrdersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.UsersDBService;
import ua.javaexternal_shulzhenko.repair_service.services.authentication.UserAuthenticator;
import ua.javaexternal_shulzhenko.repair_service.models.forms.OrderForm;
import ua.javaexternal_shulzhenko.repair_service.services.pagination.PagePaginationHandler;
import ua.javaexternal_shulzhenko.repair_service.services.validation.FormValidator;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PaginationModel;
import ua.javaexternal_shulzhenko.repair_service.models.forms.LoginForm;
import ua.javaexternal_shulzhenko.repair_service.models.forms.RegistrationForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/reviews", "/home", "/customer_home", "/customer_order_history",
        "/registration", "/login", "/contacts", "/leave_review",
        "/create_order", "/manager_home", "/error", "/logout",
        "/admin_home", "/man_mas_registration", "/user_deleting", "/master_home",
        "/master_completed_orders", "/active_orders", "/completed_orders", "/order_history",
        "/customers", "/masters"})
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
        List<Order> orders;
        int entityAmount;
        PaginationModel paginationModel;


        switch (servletPath) {
            case "/home":
            case "/contacts":
                req.setAttribute("aside_menu", "aside_menu.jsp");
                req.setAttribute("main_block", "common_home.jsp");
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/login":
                req.setAttribute("main_block", "login_main_block.jsp");
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/logout":
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/home");
                break;
            case "/customer_home":
            case "/customer_order_history":
            case "/master_home":
            case "/master_completed_orders":
                req.setAttribute("aside_menu", "aside_menu.jsp");
                req.setAttribute("main_block", "customer_master_page.jsp");
                user = getUserFromSession(req);
                pageNum = extractPageNum(req);
                offset = computeOffset(pageNum);
                if (servletPath.equals("/customer_home")) {
                    orders = OrdersDBService.getOrdersByOffsetAmountExcludeStatus(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(), OrderStatus.ORDER_COMPLETED);
                    entityAmount = OrdersDBService.getOrdersAmountByExcludeStatus(user, OrderStatus.ORDER_COMPLETED);
                } else {
                    orders = OrdersDBService.getOrdersByOffsetAmountStatus(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(), OrderStatus.ORDER_COMPLETED);
                    entityAmount = OrdersDBService.getOrdersAmountByStatus(user, OrderStatus.ORDER_COMPLETED);
                }

                paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                        pageNum, entityAmount, PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                req.setAttribute("orders", orders);
                req.setAttribute("pgModel", paginationModel);
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/manager_home":
            case "/active_orders":
            case "/completed_orders":
            case "/order_history":
            case "/customers":
            case "/masters":
                req.setAttribute("aside_menu", "aside_menu.jsp");
                req.setAttribute("main_block", "manager_home.jsp");
                user = getUserFromSession(req);
                pageNum = extractPageNum(req);
                offset = computeOffset(pageNum);

                if (servletPath.equals("/manager_home")) {
                    orders = OrdersDBService.getOrdersByOffsetAmountStatus(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(), OrderStatus.PENDING);
                    entityAmount = OrdersDBService.getOrdersAmountByStatus(user, OrderStatus.PENDING);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, entityAmount, PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                    req.setAttribute("orders", orders);
                } else if (servletPath.equals("/active_orders")) {
                    orders = OrdersDBService.getOrdersByOffsetAmountExcludeStatus(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(), OrderStatus.ORDER_COMPLETED);
                    entityAmount = OrdersDBService.getOrdersAmountByExcludeStatus(user, OrderStatus.ORDER_COMPLETED);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, entityAmount, PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                    req.setAttribute("orders", orders);
                } else if (servletPath.equals("/completed_orders")) {
                    orders = OrdersDBService.getOrdersByOffsetAmountStatus(user, offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(), OrderStatus.ORDER_COMPLETED);
                    entityAmount = OrdersDBService.getOrdersAmountByStatus(user, OrderStatus.ORDER_COMPLETED);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, entityAmount, PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                    req.setAttribute("orders", orders);
                } else if (servletPath.equals("/order_history")) {
                    orders = OrdersDBService.getOrdersByAmountOffsetTwoStatuses(offset,
                            PaginationConstants.ORDERS_FOR_PAGE.getAmount(),
                            OrderStatus.ORDER_COMPLETED, OrderStatus.REJECTED);
                    entityAmount = OrdersDBService.getOrdersAmountByTwoStatuses(
                            OrderStatus.ORDER_COMPLETED, OrderStatus.REJECTED);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, entityAmount, PaginationConstants.ORDERS_FOR_PAGE.getAmount());
                    req.setAttribute("orders", orders);
                } else if (servletPath.equals("/customers")) {
                    List<User> customers = UsersDBService.getUsersByRoleOffsetAmount(Role.CUSTOMER,
                            offset, PaginationConstants.USERS_FOR_PAGE.getAmount());
                    entityAmount = UsersDBService.getUsersAmountByRole(Role.CUSTOMER);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, entityAmount, PaginationConstants.USERS_FOR_PAGE.getAmount());
                    req.setAttribute("customers", customers);
                } else {
                    List<User> masters = UsersDBService.getUsersByRoleOffsetAmount(Role.MASTER,
                            offset, PaginationConstants.USERS_FOR_PAGE.getAmount());
                    entityAmount = UsersDBService.getUsersAmountByRole(Role.MASTER);
                    paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(),
                            pageNum, entityAmount, PaginationConstants.USERS_FOR_PAGE.getAmount());
                    req.setAttribute("masters", masters);
                }
                req.setAttribute("pgModel", paginationModel);
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/admin_home":
            case "/man_mas_registration":
                if (servletPath.equals("/admin_home")) {
                    List<User> managers = UsersDBService.getUsersByRole(Role.MANAGER);
                    List<User> masters = UsersDBService.getUsersByRole(Role.MASTER);
                    req.setAttribute("managers", managers);
                    req.setAttribute("masters", masters);
                }
                req.setAttribute("aside_menu", "aside_menu.jsp");
                req.setAttribute("main_block", "admin_page.jsp");
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/reviews":
                req.setAttribute("aside_menu", "aside_menu.jsp");
                req.setAttribute("main_block", "reviews.jsp");
                pageNum = extractPageNum(req);
                paginationModel = pagePaginationHandler.createPaginationModel(req.getRequestURI(), pageNum, 1000, 50);
                req.setAttribute("paginationModel", paginationModel);
                req.setAttribute("page", pageNum);
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/create_order":
                req.setAttribute("aside_menu", "aside_menu.jsp");
                req.setAttribute("main_block", "order_form.jsp");
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/registration":
                req.setAttribute("main_block", "registration_main_block.jsp");
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            case "/error":
                req.setAttribute("main_block", "404.jsp");
                req.getRequestDispatcher("WEB-INF/jsp_pages/core_page.jsp").forward(req, resp);
                break;
            default:
                resp.getWriter().print("THIS PATH DON'T PROCESS");
                break;
        }
    }

    private int extractPageNum(HttpServletRequest req) {
        String page = req.getParameter("page");
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
                Map<String, String> inconsistencies = FormValidator.validateForm(loginForm);
                if (inconsistencies.isEmpty()) {
                    User user = UserAuthenticator.authenticate(loginForm);
                    addUserToSession(req, user);
                    String targetPath = defineTargetPathAfterLogin(req, user);
                    resp.sendRedirect(req.getContextPath() + targetPath);
                } else {
                    if (inconsistencies.get("email") != null) {
                        throw new VerificationException(VerificationException.VerificationExceptionType.EMAIL);
                    } else {
                        throw new VerificationException(VerificationException.VerificationExceptionType.PASS);
                    }
                }
            } catch (VerificationException exc) {
                req.setAttribute(exc.getType().name(), "");
                req.setAttribute("prevForm", loginForm);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                doGet(req, resp);
                throw new VerificationException(exc.getType());
            } catch (IllegalAccessException e) {
                e.printStackTrace();                                     //////////////////////////////////handle this exception !!!!!!!!!!!!!!!!!!!!!!!!!
            }

        } else if (req.getServletPath().equals("/registration") || req.getServletPath().equals("/man_mas_registration")) {

            try {
                RegistrationForm registrationForm = new RegistrationForm(req);
                Map<String, String> inconsistencies = FormValidator.validateForm(registrationForm);
                if (inconsistencies.isEmpty()) {
                    UsersDBService.createUser(registrationForm);
                    req.setAttribute("userWasRegistered", "");
                } else {
                    req.setAttribute("inconsistencies", inconsistencies);
                    req.setAttribute("prevForm", registrationForm);
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();                                     /////////////////////////////////handle this exception !!!!!!!!!!!!!!!!!!!!!!!!!
            }
            doGet(req, resp);

        } else if (req.getServletPath().equals("/create_order")) {
            try {
                OrderForm orderForm = new OrderForm(req);
                Map<String, String> inconsistencies = FormValidator.validateForm(orderForm);
                if (inconsistencies.isEmpty()) {
                    OrdersDBService.addOrder(new Order(orderForm));
                    Order order = OrdersDBService.getLastOrderForRegUser(orderForm.getUser().getId());
                    req.setAttribute("madeOrder", order);
                } else {
                    req.setAttribute("inconsistencies", inconsistencies);
                    req.setAttribute("prevForm", orderForm);
                }
                doGet(req, resp);
            } catch (IllegalAccessException e) {
                e.printStackTrace();                                //////////////////////////////////handle this exception !!!!!!!!!!!!!!!!!!!!!!!!!
            }
        } else if (req.getServletPath().equals("/user_deleting")) {
            int userId = Integer.parseInt(req.getParameter("userId"));
            UsersDBService.deleteUser(userId);
            resp.sendRedirect(req.getContextPath() + "/admin_home");
        }
    }

    private String defineTargetPathAfterLogin(HttpServletRequest req, User user) {
        String path = (String) req.getSession().getAttribute("previousBeforeLogin");
        if (path != null) {
            return path;
        } else {
            switch (user.getRole()) {
                case CUSTOMER:
                    return "/customer_home";
                case ADMIN:
                    return "/admin_home";
                case MANAGER:
                    return "/manager_home";
                case MASTER:
                    return "/master_home";
                default:
                    return "/home";
            }
        }
    }

    private void addUserToSession(HttpServletRequest req, User user) {
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
    }

    private User getUserFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute("user");
    }
}