package analyzeData.service;

import analyzeData.model.Note;
import analyzeData.model.Probability;
import analyzeData.utils.AnalyzeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test AnalyzeService
 */
@Slf4j
@ExtendWith(MockitoExtension.class)
public class AnalyzeServiceTest {

    @Mock
    AnalyzeUtils analyzeUtils;

    @InjectMocks
    AnalyzeService analyzeService;


    @Test
    public void isNone() {
        LocalDate birthdate = LocalDate.now();
        List<Note> notes = new ArrayList<>();
        assertTrue(analyzeService.isNone(notes, String.valueOf(birthdate), "M"));
        assertTrue(analyzeService.isNone(notes, String.valueOf(birthdate), "F"));
    }

    @Test
    public void isBorderlineTest() {
        List<Note> notes = new ArrayList<>();
        LocalDate birthdateOld = LocalDate.of(1970, 1, 1);
        notes.add(Note.builder().content("Reaction").build());
        notes.add(Note.builder().content("Height").build());
        assertTrue(analyzeService.isBorderline(notes, String.valueOf(birthdateOld), "M"));
        assertTrue(analyzeService.isBorderline(notes, String.valueOf(birthdateOld), "F"));
    }

    @Test
    public void isInDangerTest() {
        LocalDate birthdate = LocalDate.of(2002, 1, 1);
        List<Note> notes = new ArrayList<>();
        notes.add(Note.builder().content("Dizziness").build());
        notes.add(Note.builder().content("Cholesterol").build());
        notes.add(Note.builder().content("Weight").build());
        assertTrue(analyzeService.isInDanger(notes, String.valueOf(birthdate), "M"));
        assertFalse(analyzeService.isInDanger(notes, String.valueOf(birthdate), "F"));
        notes.add(Note.builder().content("Antibodies").build());
        assertTrue(analyzeService.isInDanger(notes, String.valueOf(birthdate), "F"));
        LocalDate birthdateOld = LocalDate.MAX;
        notes.add(Note.builder().content("Reaction").build());
        notes.add(Note.builder().content("Height").build());
        assertTrue(analyzeService.isInDanger(notes, String.valueOf(birthdateOld), "M"));
        assertTrue(analyzeService.isInDanger(notes, String.valueOf(birthdateOld), "F"));
    }

    @Test
    public void isEarlyOnsetTest() {
        LocalDate birthdate = LocalDate.of(2002, 1, 1);
        List<Note> notes = new ArrayList<>();
        notes.add(Note.builder().content("Dizziness").build());
        notes.add(Note.builder().content("Cholesterol").build());
        notes.add(Note.builder().content("Weight").build());
        notes.add(Note.builder().content("Relapse").build());
        notes.add(Note.builder().content("Smoker").build());
        assertTrue(analyzeService.isEarlyOnset(notes, String.valueOf(birthdate), "M"));
        assertFalse(analyzeService.isEarlyOnset(notes, String.valueOf(birthdate), "F"));
        notes.add(Note.builder().content("Antibodies").build());
        notes.add(Note.builder().content("Height").build());
        assertTrue(analyzeService.isEarlyOnset(notes, String.valueOf(birthdate), "F"));
        LocalDate birthdateOld = LocalDate.MAX;
        notes.add(Note.builder().content("Reaction").build());
        assertTrue(analyzeService.isEarlyOnset(notes, String.valueOf(birthdateOld), "M"));
        assertTrue(analyzeService.isEarlyOnset(notes, String.valueOf(birthdateOld), "F"));
    }

    //Tests all possible male patient cases
    @Test
    public void analyzeYoungMalePatientWithZeroTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithZeroTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithOneTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithOneTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithTwoTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithTwoTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithThreeTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithThreeTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithFourTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithFourTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithFiveTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithFiveTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithSixTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithSixTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithSevenTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithSevenTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeYoungMalePatientWithEightTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        notes2.add(Note.builder().content("Smoker").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }

    @Test
    public void analyzeOldMalePatientWithEightTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        notes2.add(Note.builder().content("Smoker").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "M"));
    }


    //Tests all possible female patient cases
    @Test
    public void analyzeYoungFemalePatientWithZeroTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithZeroTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithOneTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithOneTermTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithTwoTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithTwoTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithThreeTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        assertEquals(Probability.NONE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithThreeTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithFourTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithFourTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithFiveTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithFiveTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        assertEquals(Probability.BORDERLINE, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithSixTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithSixTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithSevenTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOlFemalePatientWithSevenTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        assertEquals(Probability.IN_DANGER, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeYoungFemalePatientWithEightTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(2002, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        notes2.add(Note.builder().content("Smoker").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }

    @Test
    public void analyzeOldFemalePatientWithEightTermsTest() {
        List<Note> notes2 = new ArrayList<>();
        LocalDate birthdateOld2 = LocalDate.of(1970, 1, 1);
        notes2.add(Note.builder().content("Reaction").build());
        notes2.add(Note.builder().content("Body Height").build());
        notes2.add(Note.builder().content("Body Weight").build());
        notes2.add(Note.builder().content("Antibodies").build());
        notes2.add(Note.builder().content("Dizziness").build());
        notes2.add(Note.builder().content("Cholesterol").build());
        notes2.add(Note.builder().content("Relapse").build());
        notes2.add(Note.builder().content("Smoker").build());
        assertEquals(Probability.EARLY_ONSET, analyzeService.analyzePatient(notes2, String.valueOf(birthdateOld2), "F"));
    }
}
