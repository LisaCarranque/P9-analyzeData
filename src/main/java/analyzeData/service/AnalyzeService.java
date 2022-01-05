package analyzeData.service;

import analyzeData.model.Note;
import analyzeData.model.Probability;
import analyzeData.utils.AnalyzeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The service class responsible for analyzing patient information and notes
 */
@Slf4j
@Service
public class AnalyzeService implements IAnalyzeService {

    @Autowired
    AnalyzeUtils analyzeUtils;

    /**
     * This method gives the output of patient data and related notes analysis
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return the probability of disease for this patient : none, borderline, in danger, early onset
     */
    public Probability analyzePatient(List<Note> notes, String birthdate, String gender) {
        log.info("AnalyzeService : Analyzing patient data");
        Probability probability = null;
        if (isNone(notes, birthdate, gender)) {
            probability = Probability.NONE;
        }
        ;
        if (isBorderline(notes, birthdate, gender)) {
            probability = Probability.BORDERLINE;
        }
        ;
        if (isInDanger(notes, birthdate, gender)) {
            probability = Probability.IN_DANGER;
        }
        ;
        if (isEarlyOnset(notes, birthdate, gender)) {
            probability = Probability.EARLY_ONSET;
        }
        ;
        return probability;
    }

    /**
     * This method checks if the probability of disease of this patient is : none
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is none else false
     */
    public Boolean isNone(List<Note> notes, String birthdate, String gender) {
        if (analyzeUtils.findTrigger(notes).size() >= 0) {
            log.info("AnalyzeService : Analyzing patient data gives result NONE");
            return true;
        }
        return false;
    }

    /**
     * This method checks if the probability of disease of this patient is : borderline
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is borderline else false
     */
    public Boolean isBorderline(List<Note> notes, String birthdate, String gender) {
        if (analyzeUtils.findTrigger(notes).size() >= 2 &&
                analyzeUtils.getAge(birthdate) > 30) {
            log.info("AnalyzeService : Analyzing patient data gives result BORDERLINE");
            return true;
        }
        return false;
    }

    /**
     * This method checks if the probability of disease of this patient is : in danger
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is in danger else false
     */
    public Boolean isInDanger(List<Note> notes, String birthdate, String gender) {
        if (analyzeUtils.findTrigger(notes).size() >= 3 &&
                analyzeUtils.getAge(birthdate) < 30
                && gender.equals("M")) {
            log.info("AnalyzeService : Analyzing patient data gives result IN DANGER");
            return true;
        }
        if (analyzeUtils.findTrigger(notes).size() >= 4 &&
                analyzeUtils.getAge(birthdate.toString()) < 30
                && gender.equals("F")) {
            log.info("AnalyzeService : Analyzing patient data gives result IN DANGER");
            return true;
        }
        if (analyzeUtils.findTrigger(notes).size() >= 6 &&
                analyzeUtils.getAge(birthdate) > 30) {
            log.info("AnalyzeService : Analyzing patient data gives result IN DANGER");
            return true;
        }
        return false;
    }

    /**
     * This method checks if the probability of disease of this patient is : early onset
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is early onset else false
     */
    public Boolean isEarlyOnset(List<Note> notes, String birthdate, String gender) {
        if (analyzeUtils.findTrigger(notes).size() >= 5 &&
                analyzeUtils.getAge(birthdate) < 30
                && gender.equals("M")) {
            log.info("AnalyzeService : Analyzing patient data gives result EARLY ONSET");
            return true;
        }
        if (analyzeUtils.findTrigger(notes).size() >= 7 &&
                analyzeUtils.getAge(birthdate) < 30
                && gender.equals("F")) {
            log.info("AnalyzeService : Analyzing patient data gives result EARLY ONSET");
            return true;
        }
        if (analyzeUtils.findTrigger(notes).size() >= 8 &&
                analyzeUtils.getAge(birthdate) > 30) {
            log.info("AnalyzeService : Analyzing patient data gives result EARLY ONSET");
            return true;
        }
        return false;
    }
}
