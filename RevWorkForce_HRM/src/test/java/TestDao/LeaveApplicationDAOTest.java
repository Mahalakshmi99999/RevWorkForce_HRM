package TestDao;

import org.example.Dao.LeaveApplicationDAO;
import org.example.models.LeaveApplication;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaveApplicationDAOTest {

    @Test
    void testLeaveApplicationDAOObjectCreation() {
        LeaveApplicationDAO dao = new LeaveApplicationDAO();
        assertNotNull(dao);
    }

    @Test
    void testApplyLeaveMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("applyLeave", LeaveApplication.class)
        );
    }

    @Test
    void testFindByEmployeeIdMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("findByEmployeeId", int.class)
        );
    }

    @Test
    void testFindPendingLeavesByEmployeeMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("findPendingLeavesByEmployee", int.class)
        );
    }

    @Test
    void testCancelPendingLeaveMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("cancelPendingLeave", int.class, int.class)
        );
    }

    @Test
    void testGetPendingLeavesForManagerMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("getPendingLeavesForManager", int.class)
        );
    }

    @Test
    void testFindPendingByManagerIdMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("findPendingByManagerId", int.class)
        );
    }

    @Test
    void testApproveLeaveMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("approveLeave", int.class, String.class)
        );
    }

    @Test
    void testRejectLeaveMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("rejectLeave", int.class, String.class)
        );
    }

    @Test
    void testFindByLeaveAppIdMethodExists() {
        assertDoesNotThrow(() ->
                LeaveApplicationDAO.class.getMethod("findByLeaveAppId", int.class)
        );
    }


    @Test
    void testFindByEmployeeIdExecutesSafely() {
        LeaveApplicationDAO dao = new LeaveApplicationDAO();

        assertDoesNotThrow(() -> {
            List<LeaveApplication> leaves =
                    dao.findByEmployeeId(-99999);
            assertNotNull(leaves);
        });
    }


    @Test
    void testFindPendingByManagerIdExecutesSafely() {
        LeaveApplicationDAO dao = new LeaveApplicationDAO();

        assertDoesNotThrow(() -> {
            List<LeaveApplication> leaves =
                    dao.findPendingByManagerId(-99999);
            assertNotNull(leaves);
        });
    }


    @Test
    void testFindByLeaveAppIdReturnsNullForInvalidId() {
        LeaveApplicationDAO dao = new LeaveApplicationDAO();

        assertDoesNotThrow(() -> {
            LeaveApplication leave =
                    dao.findByLeaveAppId(-99999);
            assertNull(leave);
        });
    }


    @Test
    void testCancelPendingLeaveReturnsFalseForInvalidIds() {
        LeaveApplicationDAO dao = new LeaveApplicationDAO();

        boolean result = assertDoesNotThrow(() ->
                dao.cancelPendingLeave(-99999, -99999)
        );

        assertFalse(result);
    }
}

