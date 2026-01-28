package TestDao;

import org.example.Dao.ManagerPerformanceReviewDAO;
import org.example.models.PerformanceReview;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerPerformanceReviewTest {

    @Test
    void testManagerPerformanceReviewDAOObjectCreation() {
        ManagerPerformanceReviewDAO dao = new ManagerPerformanceReviewDAO();
        assertNotNull(dao);
    }

    @Test
    void testGetSubmittedReviewsForManagerMethodExists() {
        assertDoesNotThrow(() ->
                ManagerPerformanceReviewDAO.class.getMethod(
                        "getSubmittedReviewsForManager",
                        int.class
                )
        );
    }

    @Test
    void testReviewEmployeePerformanceMethodExists() {
        assertDoesNotThrow(() ->
                ManagerPerformanceReviewDAO.class.getMethod(
                        "reviewEmployeePerformance",
                        int.class,
                        int.class,
                        String.class
                )
        );
    }


    @Test
    void testGetSubmittedReviewsForManagerExecutesSafely() {
        ManagerPerformanceReviewDAO dao = new ManagerPerformanceReviewDAO();

        assertDoesNotThrow(() -> {
            List<PerformanceReview> reviews =
                    dao.getSubmittedReviewsForManager(-99999);
            assertNotNull(reviews);
        });
    }
}
