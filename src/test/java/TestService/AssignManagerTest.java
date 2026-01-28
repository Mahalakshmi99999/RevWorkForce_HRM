package TestService;

import org.example.Dao.AdminAssignManagerDAO;
import org.example.models.LoggedInUserContext;
import org.example.service.AdminAssignManagerService;
import org.example.service.AuditLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignManagerTest {

    @Mock
    private AdminAssignManagerDAO assignManagerDAO;

    @Mock
    private AuditLogService auditLogService;

    private AdminAssignManagerService service;

    @BeforeEach
    void setUp() throws Exception {
        service = new AdminAssignManagerService();

        injectMock(service, "assignManagerDAO", assignManagerDAO);
        injectMock(service, "auditLogService", auditLogService);


        LoggedInUserContext.setEmployeeId(500);
    }



    @Test
    void assignManager_fails_whenEmployeeEqualsManager() {

        boolean result = service.assignManager(10, 10);

        assertFalse(result);
        verifyNoInteractions(assignManagerDAO);
        verifyNoInteractions(auditLogService);
    }

    @Test
    void assignManager_success() {

        when(assignManagerDAO.assignManager(1, 2))
                .thenReturn(true);

        boolean result = service.assignManager(1, 2);

        assertTrue(result);

        verify(assignManagerDAO)
                .assignManager(1, 2);

        verify(auditLogService).logAction(
                500,
                "ASSIGN_MANAGER",
                "ManagerId=2 assigned to EmployeeId=1"
        );
    }

    @Test
    void assignManager_fails_whenDaoReturnsFalse() {

        when(assignManagerDAO.assignManager(1, 2))
                .thenReturn(false);

        boolean result = service.assignManager(1, 2);

        assertFalse(result);

        verify(assignManagerDAO)
                .assignManager(1, 2);

        verifyNoInteractions(auditLogService);
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

