package TestDao;

import org.example.Dao.AdminEmployeeDAO;
import org.example.models.Employee;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminEmployeeTest {

    @Test
    void testAdminEmployeeDAOObjectCreation() {
        AdminEmployeeDAO dao = new AdminEmployeeDAO();
        assertNotNull(dao);
    }

    @Test
    void testFindAllEmployeesMethodExists() {
        assertDoesNotThrow(() ->
                AdminEmployeeDAO.class.getMethod("findAllEmployees")
        );
    }

    @Test
    void testAddEmployeeAndReturnIdMethodExists() {
        assertDoesNotThrow(() ->
                AdminEmployeeDAO.class.getMethod(
                        "addEmployeeAndReturnId",
                        Employee.class
                )
        );
    }


    @Test
    void testFindAllEmployeesExecutesSafely() {
        AdminEmployeeDAO dao = new AdminEmployeeDAO();

        assertDoesNotThrow(() -> {
            List<Employee> employees = dao.findAllEmployees();
            assertNotNull(employees);
        });
    }
}
