package TestService;

import org.example.Dao.ManagerTeamDAO;
import org.example.models.Employee;
import org.example.models.LoggedInUserContext;
import org.example.service.ManagerTeamService;
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
class ManagerTeamServiceTest {

    @Mock
    private ManagerTeamDAO managerTeamDAO;

    private ManagerTeamService managerTeamService;

    @BeforeEach
    void setUp() throws Exception {
        managerTeamService = new ManagerTeamService();


        injectMock(managerTeamService, "teamDAO", managerTeamDAO);


        LoggedInUserContext.setEmployeeId(100);
    }

    @Test
    void getMyTeam_success() {

        Employee e1 = new Employee();
        Employee e2 = new Employee();

        when(managerTeamDAO.findTeamMembers(100))
                .thenReturn(List.of(e1, e2));

        List<Employee> result =
                managerTeamService.getMyTeam();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(managerTeamDAO).findTeamMembers(100);
    }

    @Test
    void getMyTeam_returnsEmptyList() {

        when(managerTeamDAO.findTeamMembers(100))
                .thenReturn(List.of());

        List<Employee> result =
                managerTeamService.getMyTeam();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(managerTeamDAO).findTeamMembers(100);
    }



    private void injectMock(Object target, String fieldName, Object mock)
            throws Exception {

        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, mock);
    }
}

