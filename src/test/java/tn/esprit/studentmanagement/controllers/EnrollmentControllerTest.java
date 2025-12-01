package tn.esprit.studentmanagement.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Status;
import tn.esprit.studentmanagement.services.IEnrollment;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnrollmentController.class)
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IEnrollment enrollmentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Enrollment enrollment1;
    private Enrollment enrollment2;

    @BeforeEach
    void setUp() {
        enrollment1 = new Enrollment();
        enrollment1.setIdEnrollment(1L);
        enrollment1.setEnrollmentDate(LocalDate.of(2023, 9, 1));
        enrollment1.setGrade(85.5);
        enrollment1.setStatus(Status.ACTIVE);

        enrollment2 = new Enrollment();
        enrollment2.setIdEnrollment(2L);
        enrollment2.setEnrollmentDate(LocalDate.of(2023, 9, 15));
        enrollment2.setGrade(90.0);
        enrollment2.setStatus(Status.COMPLETED);
    }

    @Test
    void testGetAllEnrollment() throws Exception {
        // Arrange
        List<Enrollment> enrollments = Arrays.asList(enrollment1, enrollment2);
        when(enrollmentService.getAllEnrollments()).thenReturn(enrollments);

        // Act & Assert
        mockMvc.perform(get("/Enrollment/getAllEnrollment"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].grade").value(85.5))
                .andExpect(jsonPath("$[1].grade").value(90.0));

        verify(enrollmentService, times(1)).getAllEnrollments();
    }

    @Test
    void testGetEnrollment() throws Exception {
        // Arrange
        when(enrollmentService.getEnrollmentById(1L)).thenReturn(enrollment1);

        // Act & Assert
        mockMvc.perform(get("/Enrollment/getEnrollment/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value(85.5))
                .andExpect(jsonPath("$.status").value("ACTIVE"));

        verify(enrollmentService, times(1)).getEnrollmentById(1L);
    }

    @Test
    void testCreateEnrollment() throws Exception {
        // Arrange
        when(enrollmentService.saveEnrollment(any(Enrollment.class))).thenReturn(enrollment1);

        // Act & Assert
        mockMvc.perform(post("/Enrollment/createEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollment1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value(85.5));

        verify(enrollmentService, times(1)).saveEnrollment(any(Enrollment.class));
    }

    @Test
    void testUpdateEnrollment() throws Exception {
        // Arrange
        when(enrollmentService.saveEnrollment(any(Enrollment.class))).thenReturn(enrollment1);

        // Act & Assert
        mockMvc.perform(put("/Enrollment/updateEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollment1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade").value(85.5));

        verify(enrollmentService, times(1)).saveEnrollment(any(Enrollment.class));
    }

    @Test
    void testDeleteEnrollment() throws Exception {
        // Arrange
        doNothing().when(enrollmentService).deleteEnrollment(anyLong());

        // Act & Assert
        mockMvc.perform(delete("/Enrollment/deleteEnrollment/1"))
                .andExpect(status().isOk());

        verify(enrollmentService, times(1)).deleteEnrollment(1L);
    }
}
