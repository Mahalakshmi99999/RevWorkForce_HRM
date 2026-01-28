package TestDao;

import org.example.Dao.LeaveBalanceDAO;
import org.example.models.LeaveBalance;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeaveBalanceDAOTest {

    @Test
    void testLeaveBalanceDAOObjectCreation() {
        LeaveBalanceDAO dao = new LeaveBalanceDAO();
        assertNotNull(dao);
    }

    @Test
    void testFindByEmployeeAndTypeMethodExists() {
        assertDoesNotThrow(() ->
                LeaveBalanceDAO.class.getMethod(
                        "findByEmployeeAndType",
                        int.class,
                        int.class
                )
        );
    }

    @Test
    void testDeductLeaveBalanceMethodExists() {
        assertDoesNotThrow(() ->
                LeaveBalanceDAO.class.getMethod(
                        "deductLeaveBalance",
                        int.class,
                        int.class,
                        int.class
                )
        );
    }

    @Test
    void testGetLeaveBalanceByEmployeeIdMethodExists() {
        assertDoesNotThrow(() ->
                LeaveBalanceDAO.class.getMethod(
                        "getLeaveBalanceByEmployeeId",
                        int.class
                )
        );
    }


    @Test
    void testFindByEmployeeAndTypeReturnsNullForInvalidIds() {
        LeaveBalanceDAO dao = new LeaveBalanceDAO();

        assertDoesNotThrow(() -> {
            LeaveBalance balance =
                    dao.findByEmployeeAndType(-99999, -99999);
            assertNull(balance);
        });
    }


    @Test
    void testDeductLeaveBalanceReturnsFalseForInvalidIds() {
        LeaveBalanceDAO dao = new LeaveBalanceDAO();

        boolean result = assertDoesNotThrow(() ->
                dao.deductLeaveBalance(-99999, -99999, 5)
        );

        assertFalse(result);
    }


    @Test
    void testGetLeaveBalanceByEmployeeIdReturnsEmptyListForInvalidId() {
        LeaveBalanceDAO dao = new LeaveBalanceDAO();

        assertDoesNotThrow(() -> {
            List<LeaveBalance> balances =
                    dao.getLeaveBalanceByEmployeeId(-99999);
            assertNotNull(balances);
        });
    }
}

