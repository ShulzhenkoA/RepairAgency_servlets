package ua.javaexternal_shulzhenko.repair_service.services.validation;

public enum  Regex {
    EMPTY(""),
    EMAIL("[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z]{2,4}"),
    PASSWORD("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$"),
    NAMES("^[\\p{L}](?=.*[\\p{L}])[- \\p{L}]{2,64}");
    private String expression;

    Regex(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
