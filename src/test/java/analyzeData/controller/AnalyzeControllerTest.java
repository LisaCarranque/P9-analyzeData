package analyzeData.controller;

import analyzeData.exception.ReportNotFoundException;
import analyzeData.model.Gender;
import analyzeData.model.Note;
import analyzeData.model.Patient;
import analyzeData.model.Probability;
import analyzeData.proxy.MedicalNotesProxy;
import analyzeData.proxy.SearchPatientProxy;
import analyzeData.service.AnalyzeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * This class is used to test AnalyzeController
 */
@ExtendWith(MockitoExtension.class)
public class AnalyzeControllerTest {

    @Mock
    AnalyzeService analyzeService;

    @Mock
    SearchPatientProxy searchPatientProxy;

    @Mock
    MedicalNotesProxy medicalNotesProxy;

    @InjectMocks
    AnalyzeController analyzeController;

    @Test
    public void analyzePatientData() {
        LocalDate localDate = LocalDate.now();
        List<Note> notes = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Patient patient = Patient.builder().id(1).firstname("firstname")
                .lastname("lastname").address("addresss").phone("phone").uuid(uuid).birthdate(localDate).gender(Gender.M).build();
        when(searchPatientProxy.getPatientById(String.valueOf(1))).thenReturn(patient);
        when(medicalNotesProxy.findNotesByUuid(String.valueOf(uuid))).thenReturn(new ArrayList<>());
        when(analyzeService.analyzePatient(notes, String.valueOf(patient.getBirthdate()),
                String.valueOf(patient.getGender()))).thenReturn(Probability.BORDERLINE);
        analyzeController.analyzePatientData(String.valueOf(1));
        verify(analyzeService, times(1)).analyzePatient(notes, String.valueOf(localDate), String.valueOf(Gender.M));
    }

    @Test
    public void analyzePatientDataNyLastname() {
        LocalDate localDate = LocalDate.now();
        List<Note> notes = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Patient patient = Patient.builder().id(1).firstname("firstname")
                .lastname("lastname").address("addresss").phone("phone").uuid(uuid).birthdate(localDate).gender(Gender.M).build();
        when(searchPatientProxy.getPatientByLastname("lastname")).thenReturn(Collections.singletonList(patient));
        when(medicalNotesProxy.findNotesByUuid(String.valueOf(uuid))).thenReturn(new ArrayList<>());
        analyzeController.analyzePatientDataByLastname("lastname");
        verify(analyzeService, times(1)).analyzePatient(notes, String.valueOf(localDate), String.valueOf(Gender.M));
    }

    @Test
    public void analyzePatientThrowsExceptionData() {
        LocalDate localDate = LocalDate.now();
        List<Note> notes = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Patient patient = Patient.builder().id(1).firstname("firstname")
                .lastname("lastname").address("addresss").phone("phone").uuid(uuid).birthdate(localDate).gender(Gender.M).build();
        when(searchPatientProxy.getPatientById(String.valueOf(1))).thenReturn(patient);
        when(medicalNotesProxy.findNotesByUuid(String.valueOf(uuid))).thenReturn(new ArrayList<>());
        assertThrows(ReportNotFoundException.class,
                () -> analyzeController.analyzePatientData(String.valueOf(1)));
    }

    @Test
    public void analyzePatientDataByLastnameThrowsException() {
        assertThrows(ReportNotFoundException.class, () -> analyzeController.analyzePatientDataByLastname("lastname"));
    }

    @Test
    public void analyzeAllPatientsDataTest() {
        LocalDate localDate = LocalDate.now();
        List<Note> notes = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Patient patient = Patient.builder().id(1).firstname("firstname")
                .lastname("lastname").address("1st street").phone("phone").uuid(uuid).birthdate(localDate).gender(Gender.M).build();
        when(searchPatientProxy.getAll()).thenReturn(Collections.singletonList(patient));
        when(medicalNotesProxy.findNotesByUuid(String.valueOf(uuid))).thenReturn(new ArrayList<>());
        analyzeController.analyzePatientsData();
        verify(analyzeService, times(1)).analyzePatient(notes, String.valueOf(patient.getBirthdate()), String.valueOf(patient.getGender()));
    }


}