package TestDao;

import org.example.Dao.ManagerTeamDAO;
import org.example.models.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTeamtest {

    @Test
    void testManagerTeamDAOObjectCreation() {
        ManagerTeamDAO dao = new ManagerTeamDAO();
        assertNotNull(dao);
    }

    @Test
    void testFindTeamMembersMethodExists() {
        assertDoesNotThrow(() ->
                ManagerTeamDAO.class.getMethod(
                        "findTeamMembers",
                        int.class
                )
        );
    }


    @Test
    void testFindTeamMembersExecutesSafely() {
        ManagerTeamDAO dao = new ManagerTeamDAO();

        assertDoesNotThrow(() -> {
            List<Employee> team =
                    dao.findTeamMembers(-99999);
            assertNotNull(team);
        });
    }
}
