package TestService;

import org.example.Dao.LeaveApplicationDAO;
import org.example.Dao.LeaveBalanceDAO;
import org.example.Dao.LeaveTypeDAO;
import org.example.models.LeaveApplication;
import org.example.models.LeaveBalance;
import org.example.models.LeaveType;
import org.example.service.LeaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveTypeDAO leaveTypeDAO;

    @Mock
    private LeaveBalanceDAO leaveBalanceDAO;

    @Mock
    private LeaveApplicationDAO leaveApplicationDAO;

    private LeaveService leaveService;

    @BeforeEach
    void setUp() throws Exception {
        leaveService = new LeaveService();

        injectMock(leaveService, "leaveTypeDAO", leaveTypeDAO);
        injectMock(leaveService, "leaveBalanceDAO", leaveBalanceDAO);
        injectMock(leaveService, "leaveApplicationDAO", leaveApplicationDAO);
    }



    @Test
    void applyLeave_fails_whenStartDateAfterEndDate() {

        boolean result = leaveService.applyLeave(
                1, 10, 1,
                LocalDate.now().plusDays(5),
                LocalDate.now(),
                "Vacation"
        );

        assertFalse(result);
        verifyNoInteractions(leaveBalanceDAO, leaveApplicationDAO);
    }

    @Test
    void applyLeave_fails_whenLeaveBalanceNotFound() {

        when(leaveBalanceDAO.findByEmployeeAndType(1, 1))
                .thenReturn(null);

        boolean result = leaveService.applyLeave(
                1, 10, 1,
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                "Vacation"
        );

        assertFalse(result);
        verify(leaveBalanceDAO)
                .findByEmployeeAndType(1, 1);
        verifyNoInteractions(leaveApplicationDAO);
    }

    @Test
    void applyLeave_fails_whenInsufficientBalance() {

        LeaveBalance balance = new LeaveBalance();
        balance.setAvailableDays(1);

        when(leaveBalanceDAO.findByEmployeeAndType(1, 1))
                .thenReturn(balance);

        boolean result = leaveService.applyLeave(
                1, 10, 1,
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                "Vacation"
        );

        assertFalse(result);
        verify(leaveBalanceDAO)
                .findByEmployeeAndType(1, 1);
        verifyNoInteractions(leaveApplicationDAO);
    }

    @Test
    void applyLeave_success() {

        LeaveBalance balance = new LeaveBalance();
        balance.setAvailableDays(10);

        when(leaveBalanceDAO.findByEmployeeAndType(1, 1))
                .thenReturn(balance);

        when(leaveApplicationDAO.applyLeave(any(LeaveApplication.class)))
                .thenReturn(true);

        boolean result = leaveService.applyLeave(
                1, 10, 1,
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                "Vacation"
        );

        assertTrue(result);

        verify(leaveBalanceDAO)
                .findByEmployeeAndType(1, 1);

        verify(leaveApplicationDAO)
                .applyLeave(any(LeaveApplication.class));
    }



    @Test
    void getAllLeaveTypes_success() {

        LeaveType lt1 = new LeaveType();
        LeaveType lt2 = new LeaveType();

        when(leaveTypeDAO.findAll())
                .thenReturn(List.of(lt1, lt2));

        List<LeaveType> result =
                leaveService.getAllLeaveTypes();

        assertEquals(2, result.size());
        verify(leaveTypeDAO).findAll();
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}
