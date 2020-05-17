package ua.javaexternal_shulzhenko.repair_service.controllers.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import ua.javaexternal_shulzhenko.repair_service.constants.Attributes;
import ua.javaexternal_shulzhenko.repair_service.constants.CRA_JSPFiles;
import ua.javaexternal_shulzhenko.repair_service.exceptions.AuthenticationException;
import ua.javaexternal_shulzhenko.repair_service.exceptions.AuthorizationException;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class ExceptionHandleFilterTest {


    @InjectMocks
    private ExceptionHandleFilter exceptionHandleFilter;

    @Mock
    private HttpServletRequest req;
    @Mock
    private  HttpServletResponse resp;
    @Mock
    private FilterChain filterChain;
    @Mock
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {"PASS", "EMAIL"})
    void catching_authenticationException_loggerAddExceptionMessage(String exceptionType) throws IOException, ServletException {
        doThrow(new AuthenticationException(AuthenticationException.VerificationExceptionType.valueOf(exceptionType))).
                when(filterChain).doFilter(req, resp);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);
        exceptionHandleFilter.doFilter(req, resp, filterChain);
    }

    @Test
    void catching_authorizationException_dispatchTo404Page() throws IOException, ServletException {
        doThrow(AuthorizationException.class).when(filterChain).doFilter(req, resp);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);
        exceptionHandleFilter.doFilter(req, resp, filterChain);
        verify(req).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.PAGE404);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @Test
    void catching_dataBaseInteractionException_dispatchTo500Page() throws IOException, ServletException {
        doThrow(DataBaseInteractionException.class).when(filterChain).doFilter(req, resp);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);
        exceptionHandleFilter.doFilter(req, resp, filterChain);
        verify(req).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.PAGE500);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }

    @Test
    void catching_otherException_dispatchTo500Page() throws IOException, ServletException {
        doThrow(NullPointerException.class).when(filterChain).doFilter(req, resp);
        when(req.getRequestDispatcher(CRA_JSPFiles.CORE_PAGE)).thenReturn(requestDispatcher);
        exceptionHandleFilter.doFilter(req, resp, filterChain);
        verify(req).setAttribute(Attributes.MAIN_BLOCK, CRA_JSPFiles.PAGE500);
        verify(req, times(1)).getRequestDispatcher(CRA_JSPFiles.CORE_PAGE);
        verify(requestDispatcher, times(1)).forward(req, resp);
    }
}