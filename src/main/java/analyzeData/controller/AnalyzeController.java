package analyzeData.controller;

import analyzeData.exception.ReportNotFoundException;
import analyzeData.model.Note;
import analyzeData.model.Patient;
import analyzeData.model.Probability;
import analyzeData.model.Report;
import analyzeData.proxy.MedicalNotesProxy;
import analyzeData.proxy.SearchPatientProxy;
import analyzeData.service.IAnalyzeService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This Controller is responsible for giving endpoints to analyze patient data
 */
@Slf4j
@RestController
@Api("Rest API for analyzeData endpoints")
public class AnalyzeController {

    @Autowired
    IAnalyzeService analyzeService;

    @Autowired
    SearchPatientProxy searchPatientProxy;

    @Autowired
    MedicalNotesProxy medicalNotesProxy;

    /**
     * This endpoint is responsible for analyzing a patient targeted by id
     * @param id the id of the targeted patient
     * @return the report e.g. the output of the analysis
     */
    @GetMapping("/assess")
    public Report analyzePatientData(@RequestParam String id) {
        log.info("AnalyzeController : Analyzing patient data");
        Patient patient = searchPatientProxy.getPatientById(id);
        List<Note> notes = medicalNotesProxy.findNotesByUuid(
                String.valueOf(patient.getUuid()));
        Probability probability = analyzeService.analyzePatient(notes, String.valueOf(patient.getBirthdate()),
                String.valueOf(patient.getGender()));
        Report report = Report.builder().probability(probability)
                .firstname(patient.getFirstname()).lastname(patient.getLastname())
                .uuid(patient.getUuid()).address(patient.getAddress())
                .birthdate(String.valueOf(patient.getBirthdate())).gender(String.valueOf(patient.getGender())).phone(patient.getPhone()).build();
        if (report.getProbability() == null) throw new ReportNotFoundException("No report found for patient with id: " + id);
        return report;
    }

    /**
     * This endpoint is responsible for analyzing a patient targeted by lastname
     * @param lastname the lastname of the targeted patient
     * @return the report e.g. the output of the analysis
     */
    @PostMapping("/assess/lastname")
    public List<Report> analyzePatientDataByLastname(@RequestParam String lastname) {
        log.info("AnalyzeController : Analyzing patient data");
        List<Report> reports = new ArrayList<>();
        List<Patient> patients = searchPatientProxy.getPatientByLastname(lastname);
        patients.stream().forEach(patient -> {
            List<Note> notes = medicalNotesProxy.findNotesByUuid(
                    String.valueOf(patient.getUuid()));
            Probability probability = analyzeService.analyzePatient(notes, String.valueOf(patient.getBirthdate()),
                    String.valueOf(patient.getGender()));
            reports.add(Report.builder().probability(probability)
                    .firstname(patient.getFirstname()).lastname(patient.getLastname())
                    .uuid(patient.getUuid()).address(patient.getAddress())
                    .birthdate(String.valueOf(patient.getBirthdate())).gender(String.valueOf(patient.getGender())).phone(patient.getPhone()).build());

        });
        if (reports.isEmpty()) throw new ReportNotFoundException("No report found for patient with lastname: " + lastname);
        return reports;
    }

    /**
     * This endpoint is responsible for analyzing all patient data
     * @return the reports e.g. the outputs of all patient data analysis
     */
    @GetMapping("/assess/all")
    public List<Report> analyzePatientsData() {
        log.info("AnalyzeController : Analyzing patient data");
        List<Report> reports = new ArrayList<>();
        List<Patient> patients = searchPatientProxy.getAll();
        patients.stream().forEach(patient -> {
            List<Note> notes = medicalNotesProxy.findNotesByUuid(
                    String.valueOf(patient.getUuid()));
            Probability probability = analyzeService.analyzePatient(notes, String.valueOf(patient.getBirthdate()),
                    String.valueOf(patient.getGender()));
            reports.add(Report.builder().probability(probability)
                    .firstname(patient.getFirstname()).lastname(patient.getLastname())
                    .uuid(patient.getUuid()).address(patient.getAddress())
                    .birthdate(String.valueOf(patient.getBirthdate())).gender(String.valueOf(patient.getGender())).phone(patient.getPhone()).build());

        });
        return reports;
    }
}