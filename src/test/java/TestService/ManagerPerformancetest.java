package TestService;

import org.example.Dao.ManagerPerformanceReviewDAO;
import org.example.models.LoggedInUserContext;
import org.example.models.PerformanceReview;
import org.example.service.ManagerPerformanceReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerPerformancetest {

    @Mock
    private ManagerPerformanceReviewDAO reviewDAO;

    private ManagerPerformanceReviewService reviewService;

    @BeforeEach
    void setUp() throws Exception {
        reviewService = new ManagerPerformanceReviewService();


        injectMock(reviewService, "reviewDAO", reviewDAO);


        LoggedInUserContext.setEmployeeId(200);
    }


    @Test
    void getSubmittedReviews_success() {

        PerformanceReview r1 = new PerformanceReview();
        PerformanceReview r2 = new PerformanceReview();

        when(reviewDAO.getSubmittedReviewsForManager(200))
                .thenReturn(List.of(r1, r2));

        List<PerformanceReview> result =
                reviewService.getSubmittedReviews();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(reviewDAO).getSubmittedReviewsForManager(200);
    }

    @Test
    void getSubmittedReviews_returnsEmptyList() {

        when(reviewDAO.getSubmittedReviewsForManager(200))
                .thenReturn(List.of());

        List<PerformanceReview> result =
                reviewService.getSubmittedReviews();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(reviewDAO).getSubmittedReviewsForManager(200);
    }


    @Test
    void reviewEmployee_success() {

        when(reviewDAO.reviewEmployeePerformance(1, 5, "Good"))
                .thenReturn(true);

        boolean result =
                reviewService.reviewEmployee(1, 5, "Good");

        assertTrue(result);
        verify(reviewDAO)
                .reviewEmployeePerformance(1, 5, "Good");
    }

    @Test
    void reviewEmployee_fails() {

        when(reviewDAO.reviewEmployeePerformance(1, 2, "Bad"))
                .thenReturn(false);

        boolean result =
                reviewService.reviewEmployee(1, 2, "Bad");

        assertFalse(result);
        verify(reviewDAO)
                .reviewEmployeePerformance(1, 2, "Bad");
    }



    @Test
    void reviewEmployeePerformance_delegatesCorrectly() {

        when(reviewDAO.reviewEmployeePerformance(3, 4, "OK"))
                .thenReturn(true);

        boolean result =
                reviewService.reviewEmployeePerformance(3, 4, "OK");

        assertTrue(result);
        verify(reviewDAO)
                .reviewEmployeePerformance(3, 4, "OK");
    }


    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

