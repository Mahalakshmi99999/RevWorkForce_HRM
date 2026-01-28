package TestDao;

import org.example.Dao.RoleDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleDAOTest {

    @Test
    void testRoleDAOObjectCreation() {
        RoleDAO dao = new RoleDAO();
        assertNotNull(dao);
    }

    @Test
    void testGetRoleNameByEmployeeIdMethodExists() {
        assertDoesNotThrow(() ->
                RoleDAO.class.getMethod(
                        "getRoleNameByEmployeeId",
                        int.class
                )
        );
    }

    @Test
    void testGetRoleNameByEmployeeIdReturnsNullForInvalidEmployee() {
        RoleDAO dao = new RoleDAO();

        assertDoesNotThrow(() -> {
            String role =
                    dao.getRoleNameByEmployeeId(-99999);
            assertNull(role);
        });
    }
}

