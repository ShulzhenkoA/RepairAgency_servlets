package ua.javaexternal_shulzhenko.repair_service.services.database_services;

import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.Queries;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.UniversalDAOFactory;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.review.Review;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.connection.DBConnectionsPool;

import java.sql.SQLException;
import java.util.LinkedList;

public class ReviewsDBService {
    private static final UniversalDAOFactory DAO_FACTORY = UniversalDAOFactory.getDaoFactory();

    public static void addReview(Review review){
        LinkedList<Object> reviewFields = new LinkedList<>();
        extractReviewFields(review, reviewFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_ORDER.getQuery(), reviewFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add review to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractReviewFields(Review review, LinkedList<Object> reviewFields) {
        reviewFields.add(review.getUserId());
        reviewFields.add(review.getContent());
        reviewFields.add(review.getDateTime());
    }


}