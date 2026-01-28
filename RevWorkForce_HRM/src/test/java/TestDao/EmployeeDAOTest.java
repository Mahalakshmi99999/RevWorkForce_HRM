package TestDao;

import org.example.Dao.EmployeeDAO;
import org.example.models.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeDAOTest {

    @Test
    void testEmployeeDAOObjectCreation() {
        EmployeeDAO dao = new EmployeeDAO();
        assertNotNull(dao);
    }

    @Test
    void testFindByIdMethodExists() {
        assertDoesNotThrow(() ->
                EmployeeDAO.class.getMethod("findById", int.class)
        );
    }

    @Test
    void testUpdateProfileMethodExists() {
        assertDoesNotThrow(() ->
                EmployeeDAO.class.getMethod(
                        "updateProfile",
                        int.class,
                        String.class,
                        String.class
                )
        );
    }

    @Test
    void testFindEmployeeWithRoleMethodExists() {
        assertDoesNotThrow(() ->
                EmployeeDAO.class.getMethod("findEmployeeWithRole", int.class)
        );
    }


    @Test
    void testFindByIdExecutesSafely() {
        EmployeeDAO dao = new EmployeeDAO();

        assertDoesNotThrow(() -> {
            Employee emp = dao.findById(-999); // non-existing ID
            assertNull(emp);
        });
    }


    @Test
    void testUpdateProfileReturnsFalseForInvalidEmployee() {
        EmployeeDAO dao = new EmployeeDAO();

        boolean result = assertDoesNotThrow(() ->
                dao.updateProfile(-999, "9999999999", "Invalid Address")
        );

        assertFalse(result);
    }


    @Test
    void testFindEmployeeWithRoleExecutesSafely() {
        EmployeeDAO dao = new EmployeeDAO();

        assertDoesNotThrow(() -> {
            Employee emp = dao.findEmployeeWithRole(-999);
            assertNull(emp);
        });
    }
}
