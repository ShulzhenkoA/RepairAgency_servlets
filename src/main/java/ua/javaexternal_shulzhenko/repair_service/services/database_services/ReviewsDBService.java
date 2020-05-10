package ua.javaexternal_shulzhenko.repair_service.services.database_services;

import ua.javaexternal_shulzhenko.repair_service.models.forms.ReviewForm;
import ua.javaexternal_shulzhenko.repair_service.models.pagination.PageEntities;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.Queries;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.UniversalDAOFactory;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.review.Review;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.connection.DBConnectionsPool;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultHandlerFactory;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultTemplate;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ReviewsDBService {
    private static final UniversalDAOFactory DAO_FACTORY = UniversalDAOFactory.getDaoFactory();

    public static void addReview(ReviewForm reviewForm){
        LinkedList<Object> reviewFields = new LinkedList<>();
        extractReviewFields(reviewForm, reviewFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_REVIEW.getQuery(), reviewFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add review to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractReviewFields(ReviewForm reviewForm, LinkedList<Object> reviewFields) {
        reviewFields.add(reviewForm.getCustomerID());
        reviewFields.add(reviewForm.getDateTime());
        reviewFields.add(reviewForm.getReviewContent());
    }

    public static PageEntities<Review> getReviewsByOffsetAmount(int offset, int amount) {
        try {
            PageEntities<Review> reviews = new PageEntities<>();
            reviews.setEntities((List<Review>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_REVIEWS_BY_OFFSET_AMOUNT.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.REVIEWS), offset, amount));
            reviews.setEntitiesTotalAmount(geReviewsTotalAmount());
            return reviews;
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get review from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int geReviewsTotalAmount() {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_REVIEW_AMOUNT.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT));
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }
}
