package ua.javaexternal_shulzhenko.repair_service.services.authentication;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;
import ua.javaexternal_shulzhenko.repair_service.exceptions.AuthenticationException;
import ua.javaexternal_shulzhenko.repair_service.models.forms.LoginForm;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.resourses.ApplicationResourceBundle;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class UserAuthenticatorTest {

    @Mock
    private HttpServletRequest req;

    @BeforeAll
    static void setTestMode() {
        ApplicationResourceBundle.setTestBundle();
    }

    @BeforeEach
    void initMock() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @CsvSource({"testing_customer@mail.com, Testcustomer1", "testing_manager@mail.com, Testmanager1"})
    void authentication_loginFormWithCorrectUserData_giveUser(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);

        LoginForm form = new LoginForm(req);
        User user = UserAuthenticator.authenticate(form);

        assertAll(
                () -> assertEquals(email, user.getEmail()),
                () -> assertEquals(pass.hashCode(), user.getPassword()));
    }

    @ParameterizedTest
    @CsvSource({"not_existing_customer@mail.com, Testcustomer1", "not_existing_manager@mail.com, Testmanager1"})
    void authentication_loginFormWithWrongEmail_throwAuthenticationException(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);

        LoginForm form = new LoginForm(req);

        assertThrows(AuthenticationException.class, () -> UserAuthenticator.authenticate(form));
    }

    @ParameterizedTest
    @CsvSource({"testing_customer@mail.com, testcustomer", "testing_manager@mail.com, testmanager"})
    void authentication_loginFormWithInvalidPass_throwAuthenticationException(String email, String pass) {
        when(req.getParameter("email")).thenReturn(email);
        when(req.getParameter("pass")).thenReturn(pass);

        LoginForm form = new LoginForm(req);

        assertThrows(AuthenticationException.class, () -> UserAuthenticator.authenticate(form));
    }
}