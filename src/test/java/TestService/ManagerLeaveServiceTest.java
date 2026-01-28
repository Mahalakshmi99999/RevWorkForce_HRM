package TestService;

import org.example.Dao.LeaveApplicationDAO;
import org.example.Dao.LeaveBalanceDAO;
import org.example.models.LeaveApplication;
import org.example.models.LoggedInUserContext;
import org.example.service.AuditLogService;
import org.example.service.ManagerLeaveService;
import org.example.service.NotificationService;
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
class ManagerLeaveServiceTest {

    @Mock
    private LeaveApplicationDAO leaveApplicationDAO;

    @Mock
    private LeaveBalanceDAO leaveBalanceDAO;

    @Mock
    private NotificationService notificationService;

    @Mock
    private AuditLogService auditLogService;

    private ManagerLeaveService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new ManagerLeaveService();


        injectMock(service, "leaveApplicationDAO", leaveApplicationDAO);
        injectMock(service, "leaveBalanceDAO", leaveBalanceDAO);
        injectMock(service, "notificationService", notificationService);
        injectMock(service, "auditLogService", auditLogService);


        LoggedInUserContext.setEmployeeId(300);
    }



    @Test
    void approveLeave_fails_whenLeaveNotFound() {

        when(leaveApplicationDAO.findByLeaveAppId(1))
                .thenReturn(null);

        boolean result = service.approveLeave(1, "OK");

        assertFalse(result);
        verify(leaveApplicationDAO).findByLeaveAppId(1);
        verifyNoInteractions(leaveBalanceDAO, notificationService, auditLogService);
    }

    @Test
    void approveLeave_fails_whenApproveDaoFails() {

        LeaveApplication leave = new LeaveApplication();
        leave.setEmployeeId(10);
        leave.setLeaveTypeId(1);
        leave.setWorkingDays(2);

        when(leaveApplicationDAO.findByLeaveAppId(1))
                .thenReturn(leave);
        when(leaveApplicationDAO.approveLeave(1, "OK"))
                .thenReturn(false);

        boolean result = service.approveLeave(1, "OK");

        assertFalse(result);
        verify(leaveApplicationDAO).approveLeave(1, "OK");
        verifyNoInteractions(leaveBalanceDAO, notificationService, auditLogService);
    }

    @Test
    void approveLeave_fails_whenBalanceDeductionFails() {

        LeaveApplication leave = new LeaveApplication();
        leave.setEmployeeId(10);
        leave.setLeaveTypeId(1);
        leave.setWorkingDays(3);

        when(leaveApplicationDAO.findByLeaveAppId(1))
                .thenReturn(leave);
        when(leaveApplicationDAO.approveLeave(1, "OK"))
                .thenReturn(true);
        when(leaveBalanceDAO.deductLeaveBalance(10, 1, 3))
                .thenReturn(false);

        boolean result = service.approveLeave(1, "OK");

        assertFalse(result);
        verify(leaveBalanceDAO)
                .deductLeaveBalance(10, 1, 3);
        verifyNoInteractions(notificationService, auditLogService);
    }

    @Test
    void approveLeave_success() {

        LeaveApplication leave = new LeaveApplication();
        leave.setEmployeeId(10);
        leave.setLeaveTypeId(2);
        leave.setWorkingDays(5);

        when(leaveApplicationDAO.findByLeaveAppId(1))
                .thenReturn(leave);
        when(leaveApplicationDAO.approveLeave(1, "Approved"))
                .thenReturn(true);
        when(leaveBalanceDAO.deductLeaveBalance(10, 2, 5))
                .thenReturn(true);

        boolean result = service.approveLeave(1, "Approved");

        assertTrue(result);

        verify(notificationService).notifyEmployee(
                10,
                "Your leave request (ID: 1) has been APPROVED.",
                "LEAVE_APPROVED"
        );

        verify(auditLogService).logAction(
                300,
                "APPROVE_LEAVE",
                "LeaveAppId=1"
        );
    }



    @Test
    void rejectLeave_fails_whenLeaveNotFound() {

        when(leaveApplicationDAO.findByLeaveAppId(2))
                .thenReturn(null);

        boolean result = service.rejectLeave(2, "No");

        assertFalse(result);
        verifyNoInteractions(notificationService, auditLogService);
    }

    @Test
    void rejectLeave_success() {

        LeaveApplication leave = new LeaveApplication();
        leave.setEmployeeId(20);

        when(leaveApplicationDAO.findByLeaveAppId(2))
                .thenReturn(leave);
        when(leaveApplicationDAO.rejectLeave(2, "No"))
                .thenReturn(true);

        boolean result = service.rejectLeave(2, "No");

        assertTrue(result);

        verify(notificationService).notifyEmployee(
                20,
                "Your leave request (ID: 2) has been REJECTED.",
                "LEAVE_REJECTED"
        );

        verify(auditLogService).logAction(
                300,
                "REJECT_LEAVE",
                "LeaveAppId=2"
        );
    }



    @Test
    void getPendingLeaves_success() {

        when(leaveApplicationDAO.findPendingByManagerId(300))
                .thenReturn(List.of(new LeaveApplication(), new LeaveApplication()));

        List<LeaveApplication> result =
                service.getPendingLeaves(300);

        assertEquals(2, result.size());
        verify(leaveApplicationDAO).findPendingByManagerId(300);
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

