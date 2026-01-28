package TestService;

import org.example.Dao.AdminEmployeeDAO;
import org.example.Dao.LoginDAO;
import org.example.models.Employee;
import org.example.models.LoggedInUserContext;
import org.example.service.AdminEmployeeService;
import org.example.service.AuditLogService;
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
class AdminEmployeeServiceTest {

    @Mock
    private AdminEmployeeDAO adminEmployeeDAO;

    @Mock
    private LoginDAO loginDAO;

    @Mock
    private AuditLogService auditLogService;

    private AdminEmployeeService adminEmployeeService;

    @BeforeEach
    void setUp() throws Exception {
        adminEmployeeService = new AdminEmployeeService();


        injectMock(adminEmployeeService, "adminEmployeeDAO", adminEmployeeDAO);
        injectMock(adminEmployeeService, "loginDAO", loginDAO);
        injectMock(adminEmployeeService, "auditLogService", auditLogService);

        LoggedInUserContext.setEmployeeId(999);
    }



    @Test
    void getAllEmployees_success() {

        Employee e1 = new Employee();
        Employee e2 = new Employee();

        when(adminEmployeeDAO.findAllEmployees())
                .thenReturn(List.of(e1, e2));

        List<Employee> result =
                adminEmployeeService.getAllEmployees();

        assertEquals(2, result.size());
        verify(adminEmployeeDAO).findAllEmployees();
    }



    @Test
    void addEmployee_success() {

        Employee emp = new Employee();
        emp.setEmail("test@mail.com");

        when(adminEmployeeDAO.addEmployeeAndReturnId(emp))
                .thenReturn(1);

        when(loginDAO.createLoginCredentials(1, "welcome123"))
                .thenReturn(true);

        boolean result =
                adminEmployeeService.addEmployee(emp);

        assertTrue(result);

        verify(adminEmployeeDAO).addEmployeeAndReturnId(emp);
        verify(loginDAO).createLoginCredentials(1, "welcome123");
        verify(auditLogService).logAction(
                999,
                "ADD_EMPLOYEE",
                "Employee created with ID=1"
        );
    }

    @Test
    void addEmployee_fails_whenEmployeeInsertFails() {

        Employee emp = new Employee();
        emp.setEmail("fail@mail.com");

        when(adminEmployeeDAO.addEmployeeAndReturnId(emp))
                .thenReturn(-1);

        boolean result =
                adminEmployeeService.addEmployee(emp);

        assertFalse(result);

        verify(adminEmployeeDAO).addEmployeeAndReturnId(emp);
        verifyNoInteractions(loginDAO);
        verifyNoInteractions(auditLogService);
    }

    @Test
    void addEmployee_fails_whenLoginCreationFails() {

        Employee emp = new Employee();
        emp.setEmail("loginfail@mail.com");

        when(adminEmployeeDAO.addEmployeeAndReturnId(emp))
                .thenReturn(1);

        when(loginDAO.createLoginCredentials(1, "welcome123"))
                .thenReturn(false);

        boolean result =
                adminEmployeeService.addEmployee(emp);

        assertFalse(result);

        verify(adminEmployeeDAO).addEmployeeAndReturnId(emp);
        verify(loginDAO).createLoginCredentials(1, "welcome123");
        verifyNoInteractions(auditLogService);
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

