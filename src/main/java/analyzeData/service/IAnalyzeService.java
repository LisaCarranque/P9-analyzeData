package analyzeData.service;

import analyzeData.model.Note;
import analyzeData.model.Probability;

import java.util.List;

public interface IAnalyzeService {

    /**
     * This method gives the output of patient data and related notes analysis
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return the probability of disease for this patient : none, borderline, in danger, early onset
     */
    Probability analyzePatient(List<Note> notes, String birthdate, String gender);

    /**
     * This method checks if the probability of disease of this patient is : none
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is none else false
     */
    public Boolean isNone(List<Note> notes, String birthdate, String gender);

    /**
     * This method checks if the probability of disease of this patient is : borderline
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is borderline else false
     */
    public Boolean isBorderline(List<Note> notes, String birthdate, String gender);

    /**
     * This method checks if the probability of disease of this patient is : in danger
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is in danger else false
     */
    public Boolean isInDanger(List<Note> notes, String birthdate, String gender);

    /**
     * This method checks if the probability of disease of this patient is : early onset
     * @param notes the medical notes related to this patient
     * @param birthdate the birthdate of the patient
     * @param gender the gender of the patient
     * @return true if it is early onset else false
     */
    public Boolean isEarlyOnset(List<Note> notes, String birthdate, String gender);

}
