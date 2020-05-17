package ua.javaexternal_shulzhenko.repair_service.controllers.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

class SessionCreateFilterTest {

    @InjectMocks
    private SessionCreateFilter sessionCreateFilter;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private FilterChain filterChain;
    @Mock
    private HttpSession session;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void settingSession_whenSessionExist_doFilter() throws IOException, ServletException {
        when(req.getSession(false)).thenReturn(session);
        sessionCreateFilter.doCustomFilter(req, resp, filterChain);
        verify(filterChain, times(1)).doFilter(req, resp);
    }
}