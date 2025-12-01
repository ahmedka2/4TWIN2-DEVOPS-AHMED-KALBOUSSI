package tn.esprit.studentmanagement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Status;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @InjectMocks
    private EnrollmentService enrollmentService;

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
    void testGetAllEnrollments() {
        // Arrange
        List<Enrollment> enrollments = Arrays.asList(enrollment1, enrollment2);
        when(enrollmentRepository.findAll()).thenReturn(enrollments);

        // Act
        List<Enrollment> result = enrollmentService.getAllEnrollments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(Status.ACTIVE, result.get(0).getStatus());
        assertEquals(Status.COMPLETED, result.get(1).getStatus());
        verify(enrollmentRepository, times(1)).findAll();
    }

    @Test
    void testGetEnrollmentById() {
        // Arrange
        when(enrollmentRepository.findById(1L)).thenReturn(Optional.of(enrollment1));

        // Act
        Enrollment result = enrollmentService.getEnrollmentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdEnrollment());
        assertEquals(85.5, result.getGrade());
        assertEquals(Status.ACTIVE, result.getStatus());
        verify(enrollmentRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveEnrollment() {
        // Arrange
        when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment1);

        // Act
        Enrollment result = enrollmentService.saveEnrollment(enrollment1);

        // Assert
        assertNotNull(result);
        assertEquals(85.5, result.getGrade());
        assertEquals(Status.ACTIVE, result.getStatus());
        verify(enrollmentRepository, times(1)).save(enrollment1);
    }

    @Test
    void testDeleteEnrollment() {
        // Arrange
        doNothing().when(enrollmentRepository).deleteById(1L);

        // Act
        enrollmentService.deleteEnrollment(1L);

        // Assert
        verify(enrollmentRepository, times(1)).deleteById(1L);
    }
}
