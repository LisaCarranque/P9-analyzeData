package analyzeData.utils;

import analyzeData.model.Note;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * This class gather util methods for patient data analysis
 */
@Component
public class AnalyzeUtils {

    /**
     * This method detects trigger words in patient notes
     * @param notes the notes related to the analyzed patient
     * @return the list of triggers found in patient notes
     */
    public static List<String> findTrigger(List<Note> notes) {
        List<String> triggers = new ArrayList<>();
        getAllTriggers().stream().forEach(
                trigger -> {
                    notes.stream().forEach(
                            note -> {
                                if (note.getContent().contains(trigger) && !triggers.contains(trigger)) {
                                    triggers.add(trigger);
                                }
                            });
                });
        return triggers;
    }

    /**
     * This method gets the age of the patient based on the birthdate
     * @param birthdate the birthdate with the format : "yyyy-MM-dd"
     * @return the age of the patient as an integer
     */
    public static Integer getAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(birthdate, formatter);
        Period period = Period.between(localDate, LocalDate.now());
        int age = period.getYears();
        return age;
    }

    /**
     * This method is used to get all triggers to proceed notes analysis
     * @return the list of triggers used to analyze patient notes
     */
    public static List<String> getAllTriggers() {
        List<String> triggers = new ArrayList<>();
        triggers.add("Hemoglobin A1C");
        triggers.add("Microalbumin");
        triggers.add("Height");
        triggers.add("Weight");
        triggers.add("Smoker");
        triggers.add("Abnormal");
        triggers.add("Cholesterol");
        triggers.add("Dizziness");
        triggers.add("Relapse");
        triggers.add("Reaction");
        triggers.add("Antibodies");
        return triggers;
    }

}
