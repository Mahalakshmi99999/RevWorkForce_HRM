package TestService;

import org.example.Dao.EmployeeDAO;
import org.example.Dao.LoginDAO;
import org.example.models.Employee;
import org.example.models.LoggedInUserContext;
import org.example.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private LoginDAO loginDAO;

    @Mock
    private EmployeeDAO employeeDAO;

    private LoginService loginService;

    @BeforeEach
    void setUp() throws Exception {
        loginService = new LoginService();


        injectMock(loginService, "loginDAO", loginDAO);
        injectMock(loginService, "employeeDAO", employeeDAO);
    }



    @Test
    void login_success() {

        Employee emp = new Employee();
        emp.setEmployeeId(1);
        emp.setManagerId(10);
        emp.setRoleName("EMPLOYEE");

        when(loginDAO.validateLogin(1, "pass")).thenReturn(1);
        when(employeeDAO.findEmployeeWithRole(1)).thenReturn(emp);

        boolean result = loginService.login(1, "pass");

        assertTrue(result);
        assertEquals(1, LoggedInUserContext.getEmployeeId());
        assertEquals(10, LoggedInUserContext.getManagerId());
        assertEquals("EMPLOYEE", LoggedInUserContext.getRoleName());

        verify(loginDAO).validateLogin(1, "pass");
        verify(employeeDAO).findEmployeeWithRole(1);
    }

    @Test
    void login_fails_when_credentials_invalid() {

        when(loginDAO.validateLogin(1, "wrong")).thenReturn(-1);

        boolean result = loginService.login(1, "wrong");

        assertFalse(result);
        verify(loginDAO).validateLogin(1, "wrong");
        verifyNoInteractions(employeeDAO);
    }

    @Test
    void login_fails_when_employee_not_found() {

        when(loginDAO.validateLogin(1, "pass")).thenReturn(1);
        when(employeeDAO.findEmployeeWithRole(1)).thenReturn(null);

        boolean result = loginService.login(1, "pass");

        assertFalse(result);
        verify(loginDAO).validateLogin(1, "pass");
        verify(employeeDAO).findEmployeeWithRole(1);
    }



    @Test
    void changePassword_success() {

        when(loginDAO.getPassword(1)).thenReturn("oldpass");
        when(loginDAO.updatePassword(1, "newpass")).thenReturn(true);

        boolean result =
                loginService.changePassword(1, "oldpass", "newpass");

        assertTrue(result);
        verify(loginDAO).getPassword(1);
        verify(loginDAO).updatePassword(1, "newpass");
    }

    @Test
    void changePassword_fails_when_user_not_found() {

        when(loginDAO.getPassword(1)).thenReturn(null);

        boolean result =
                loginService.changePassword(1, "old", "new");

        assertFalse(result);
        verify(loginDAO).getPassword(1);
        verify(loginDAO, never()).updatePassword(anyInt(), anyString());
    }

    @Test
    void changePassword_fails_when_current_password_wrong() {

        when(loginDAO.getPassword(1)).thenReturn("correct");

        boolean result =
                loginService.changePassword(1, "wrong", "new");

        assertFalse(result);
        verify(loginDAO).getPassword(1);
        verify(loginDAO, never()).updatePassword(anyInt(), anyString());
    }



    @Test
    void fetchSecurityQuestion_success() {

        when(loginDAO.getSecurityQuestion(1)).thenReturn("Your pet name?");

        String question =
                loginService.fetchSecurityQuestion(1);

        assertEquals("Your pet name?", question);
        verify(loginDAO).getSecurityQuestion(1);
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

