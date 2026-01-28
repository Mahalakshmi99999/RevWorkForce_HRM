package TestService;

import org.example.Dao.EmployeeDAO;
import org.example.models.Employee;
import org.example.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() throws Exception {
        employeeService = new EmployeeService();


        injectMock(employeeService, "employeeDAO", employeeDAO);
    }



    @Test
    void viewProfile_success() {

        Employee emp = new Employee();
        emp.setEmployeeId(1);

        when(employeeDAO.findById(1)).thenReturn(emp);

        Employee result = employeeService.viewProfile(1);

        assertNotNull(result);
        assertEquals(1, result.getEmployeeId());
        verify(employeeDAO).findById(1);
    }

    @Test
    void viewProfile_returnsNull_whenEmployeeNotFound() {

        when(employeeDAO.findById(1)).thenReturn(null);

        Employee result = employeeService.viewProfile(1);

        assertNull(result);
        verify(employeeDAO).findById(1);
    }



    @Test
    void updateProfile_success() {

        when(employeeDAO.updateProfile(1, "9999999999", "Hyd"))
                .thenReturn(true);

        boolean result =
                employeeService.updateProfile(1, "9999999999", "Hyd");

        assertTrue(result);
        verify(employeeDAO)
                .updateProfile(1, "9999999999", "Hyd");
    }

    @Test
    void updateProfile_fails_whenPhoneInvalid() {

        boolean result =
                employeeService.updateProfile(1, "", "Hyd");

        assertFalse(result);
        verifyNoInteractions(employeeDAO);
    }

    @Test
    void updateProfile_fails_whenAddressInvalid() {

        boolean result =
                employeeService.updateProfile(1, "9999999999", " ");

        assertFalse(result);
        verifyNoInteractions(employeeDAO);
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

