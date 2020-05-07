package ua.javaexternal_shulzhenko.repair_service.services.validation.regex;

public enum  Regex {
    EMPTY(""),
    USER_EMAIL("[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z]{2,4}"),
    USER_PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$"),
    NAMES("^[\\p{L}](?=.*[\\p{L}])[- \\p{L}]{1,64}"),
    CAR_BRAND("^[\\p{L}](?=.*[\\d\\p{L}])[- .\\d\\p{L}]{1,64}"),
    CAR_MODEL("^[\\d\\p{L}](?=.*[\\d\\p{L}])[- .\\d\\p{L}]{1,64}"),
    CAR_YEAR("^(19|20)\\d{2}");

    private String expression;

    Regex(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
