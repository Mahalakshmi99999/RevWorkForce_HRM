package TestDao;

import org.example.Dao.NotificationDAO;
import org.example.models.Notification;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationDAOTest {

    @Test
    void testNotificationDAOObjectCreation() {
        NotificationDAO dao = new NotificationDAO();
        assertNotNull(dao);
    }

    @Test
    void testCreateNotificationMethodExists() {
        assertDoesNotThrow(() ->
                NotificationDAO.class.getMethod(
                        "createNotification",
                        int.class,
                        String.class,
                        String.class
                )
        );
    }

    @Test
    void testGetNotificationsByEmployeeIdMethodExists() {
        assertDoesNotThrow(() ->
                NotificationDAO.class.getMethod(
                        "getNotificationsByEmployeeId",
                        int.class
                )
        );
    }

    @Test
    void testMarkAsReadMethodExists() {
        assertDoesNotThrow(() ->
                NotificationDAO.class.getMethod(
                        "markAsRead",
                        int.class
                )
        );
    }


    @Test
    void testGetNotificationsByEmployeeIdExecutesSafely() {
        NotificationDAO dao = new NotificationDAO();

        assertDoesNotThrow(() -> {
            List<Notification> notifications =
                    dao.getNotificationsByEmployeeId(-99999);
            assertNotNull(notifications);
        });
    }
}

