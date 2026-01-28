package TestService;

import org.example.Dao.LeaveBalanceDAO;
import org.example.models.LeaveBalance;
import org.example.service.LeaveBalanceService;
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
class LeaveBalanceServiceTest {

    @Mock
    private LeaveBalanceDAO leaveBalanceDAO;

    private LeaveBalanceService leaveBalanceService;

    @BeforeEach
    void setUp() throws Exception {
        leaveBalanceService = new LeaveBalanceService();


        injectMock(leaveBalanceService, "leaveBalanceDAO", leaveBalanceDAO);
    }

    @Test
    void getLeaveBalance_success() {

        LeaveBalance b1 = new LeaveBalance();
        LeaveBalance b2 = new LeaveBalance();

        when(leaveBalanceDAO.getLeaveBalanceByEmployeeId(1))
                .thenReturn(List.of(b1, b2));

        List<LeaveBalance> result =
                leaveBalanceService.getLeaveBalance(1);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(leaveBalanceDAO)
                .getLeaveBalanceByEmployeeId(1);
    }

    @Test
    void getLeaveBalance_returnsEmptyList() {

        when(leaveBalanceDAO.getLeaveBalanceByEmployeeId(2))
                .thenReturn(List.of());

        List<LeaveBalance> result =
                leaveBalanceService.getLeaveBalance(2);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(leaveBalanceDAO)
                .getLeaveBalanceByEmployeeId(2);
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

