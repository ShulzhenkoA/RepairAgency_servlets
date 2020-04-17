package ua.javaexternal_shulzhenko.repair_service.servlets;

import ua.javaexternal_shulzhenko.repair_service.exceptions.NotFoundException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/exc_servlet")
public class ExceptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        throw new NotFoundException( req.getRequestURL() + " was not found");
    }
}
