package ua.javaexternal_shulzhenko.repair_service.servlet;

import ua.javaexternal_shulzhenko.repair_service.constants.CRAPaths;
import ua.javaexternal_shulzhenko.repair_service.exceptions.VerificationException;
import ua.javaexternal_shulzhenko.repair_service.servlet.commands.GetRequestsHandleCommands;
import ua.javaexternal_shulzhenko.repair_service.servlet.commands.PostRequestHandleCommands;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {CRAPaths.HOME, CRAPaths.REVIEWS, CRAPaths.CUSTOMER_HOME,
        CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.REGISTRATION, CRAPaths.LOGIN, CRAPaths.CREATE_ORDER,
        CRAPaths.MANAGER_HOME, CRAPaths.LOGOUT, CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION,
        CRAPaths.EDIT_USER, CRAPaths.DELETE_USER, CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS,
        CRAPaths.EDIT_STATUS, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER, CRAPaths.ORDER_HISTORY,
        CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.ERROR404, CRAPaths.ERROR500})
public class AppControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        GetRequestsHandleCommands.COMMANDS.get(servletPath).handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, VerificationException {
        String servletPath = req.getServletPath();
        PostRequestHandleCommands.COMMANDS.get(servletPath).handleRequest(req, resp);
    }
}