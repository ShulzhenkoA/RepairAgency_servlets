package ua.javaexternal_shulzhenko.repair_service.services.validation.regex;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class RegexTest {

    private final String USER_EMAIL = Regex.USER_EMAIL.getExpression();
    private final String USER_PASSWORD = Regex.USER_PASSWORD.getExpression();
    private final String NAMES = Regex.NAMES.getExpression();
    private final String CAR_BRAND = Regex.CAR_BRAND.getExpression();
    private final String CAR_MODEL = Regex.CAR_MODEL.getExpression();
    private final String CAR_YEAR = Regex.CAR_YEAR.getExpression();
    private final String PRICE = Regex.PRICE.getExpression();


    @ParameterizedTest
    @ValueSource(strings = {"user@mail.com", "USER@MAIL.com", "user_1@mail_1.com"})
    void userEmailRegex_validData_matchTrue(String email){
        boolean match = Pattern.matches(USER_EMAIL, email);
        assertTrue(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "usermail.com", "user@mailcom", "user@.com", "user@mail.", "user@mail.c", "user@mail.commm",
            "user@mail.Com", "user@mail.co1", "user%@mail.com", "user@mail/.com", "юser@mail.com"})
    void userEmailRegex_invalidData_matchFalse(String email){
        boolean match = Pattern.matches(USER_EMAIL, email);
        assertFalse(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {"User1234", "User1234567890123456"})
    void userPasswordRegex_validData_matchTrue(String password){
        boolean match = Pattern.matches(USER_PASSWORD, password);
        assertTrue(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "User123", "User12345678901234567", "user1234", "U1234567", "Userpass", "User1234%", "Юser1234"})
    void userPasswordRegex_invalidData_matchFalse(String password){
        boolean match = Pattern.matches(USER_PASSWORD, password);
        assertFalse(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "User", "user", "User-user", "User user", "Us", "Юзер",
            "UserUserUserUserUserUserUserUserUserUserUserUserUserUserUserUser"})
    void namesRegex_validData_matchTrue(String name){
        boolean match = Pattern.matches(NAMES, name);
        assertTrue(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "User1", "User?", " User", "U",
            "UserUserUserUserUserUserUserUserUserUserUserUserUserUserUserUserU"})
    void namesRegex_invalidData_matchFalse(String name){
        boolean match = Pattern.matches(NAMES, name);
        assertFalse(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Brand", "Brand - 100", "Бренд", "b1",
            "Brand012345678901234567890123456"})
    void carBrandRegex_validData_matchTrue(String brand){
        boolean match = Pattern.matches(CAR_BRAND, brand);
        assertTrue(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " Brand", "Brand/", "1brand", "1234","b", "Brand0123456789012345678901234567"})
    void carBrandRegex_invalidData_matchFalse(String brand){
        boolean match = Pattern.matches(CAR_BRAND, brand);
        assertFalse(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Model", "1Model - 100.", "Модель", "1234", "m1",
            "Model012345678901234567890123456"})
    void carModelRegex_validData_matchTrue(String model){
        boolean match = Pattern.matches(CAR_MODEL, model);
        assertTrue(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            " Model", "Model+", "m", "Model0123456789012345678901234567"})
    void carModelRegex_invalidData_matchFalse(String model){
        boolean match = Pattern.matches(CAR_MODEL, model);
        assertFalse(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2000", "1999", "1900"})
    void carYearRegex_validData_matchTrue(String year){
        boolean match = Pattern.matches(CAR_YEAR, year);
        assertTrue(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2100", "1899", "200+", "20001", "199"})
    void carYearRegex_invalidData_matchFalse(String year){
        boolean match = Pattern.matches(CAR_YEAR, year);
        assertFalse(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {"100", "0.01", "99.99", "0", "0.0", "0,1"})
    void priceRegex_validData_matchTrue(String price){
        boolean match = Pattern.matches(PRICE, price);
        assertTrue(match);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1b", "1.0.0", "0.001", "1+0", ".0", "1."})
    void priceRegex_invalidData_matchFalse(String price){
        boolean match = Pattern.matches(PRICE, price);
        assertFalse(match);
    }
}