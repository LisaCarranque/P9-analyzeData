package analyzeData.utils;

import analyzeData.model.Note;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class is used to test AnalyzeUtils
 */
@ExtendWith(MockitoExtension.class)
public class AnalyzeUtilsTest {

    @InjectMocks
    AnalyzeUtils analyzeUtils;

    @Test
    public void findTrigger() {
        List<Note> notes = new ArrayList<>();
        notes.add(Note.builder().content("Dizziness").build());
        notes.add(Note.builder().content("Cholesterol").build());
        assertTrue(analyzeUtils.findTrigger(notes).size() == 2);
    }


    @Test
    public void getAgeTest() {
        String birthdate = "2019-06-01";
        assertEquals("2", String.valueOf(analyzeUtils.getAge(birthdate)));
    }

    @Test
    public void getAllTriggers() {
        assertEquals(11, analyzeUtils.getAllTriggers().size());
    }


}
