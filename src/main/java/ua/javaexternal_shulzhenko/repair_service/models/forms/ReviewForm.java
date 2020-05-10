package ua.javaexternal_shulzhenko.repair_service.models.forms;

import ua.javaexternal_shulzhenko.repair_service.constants.Parameters;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.NotEmpty;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class ReviewForm implements Form{
    private final String customerID;
    @NotEmpty
    private final String reviewContent;
    private final LocalDateTime dateTime;

    public ReviewForm(HttpServletRequest req) {
        customerID = req.getParameter(Parameters.CUSTOMER_ID);
        reviewContent = req.getParameter(Parameters.REVIEW_CONTENT);
        dateTime = LocalDateTime.now();
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
