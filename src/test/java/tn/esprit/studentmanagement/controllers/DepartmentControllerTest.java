package tn.esprit.studentmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.services.IDepartmentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        department1 = new Department();
        department1.setIdDepartment(1L);
        department1.setName("Computer Science");
        department1.setLocation("Building A");
        department1.setPhone("111-222-3333");
        department1.setHead("Dr. Smith");

        department2 = new Department();
        department2.setIdDepartment(2L);
        department2.setName("Mathematics");
        department2.setLocation("Building B");
        department2.setPhone("444-555-6666");
        department2.setHead("Dr. Johnson");
    }

    @Test
    void testGetAllDepartment() throws Exception {
        // Arrange
        List<Department> departments = Arrays.asList(department1, department2);
        when(departmentService.getAllDepartments()).thenReturn(departments);

        // Act & Assert
        mockMvc.perform(get("/Depatment/getAllDepartment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Computer Science"))
                .andExpect(jsonPath("$[1].name").value("Mathematics"));

        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void testGetDepartment() throws Exception {
        // Arrange
        when(departmentService.getDepartmentById(1L)).thenReturn(department1);

        // Act & Assert
        mockMvc.perform(get("/Depatment/getDepartment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Computer Science"))
                .andExpect(jsonPath("$.location").value("Building A"));

        verify(departmentService, times(1)).getDepartmentById(1L);
    }

    @Test
    void testCreateDepartment() throws Exception {
        // Arrange
        when(departmentService.saveDepartment(any(Department.class))).thenReturn(department1);

        // Act & Assert
        mockMvc.perform(post("/Depatment/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Computer Science"));

        verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }

    @Test
    void testUpdateDepartment() throws Exception {
        // Arrange
        when(departmentService.saveDepartment(any(Department.class))).thenReturn(department1);

        // Act & Assert
        mockMvc.perform(put("/Depatment/updateDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Computer Science"));

        verify(departmentService, times(1)).saveDepartment(any(Department.class));
    }

    @Test
    void testDeleteDepartment() throws Exception {
        // Arrange
        doNothing().when(departmentService).deleteDepartment(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/Depatment/deleteDepartment/1"))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).deleteDepartment(1L);
    }
}
