package analyzeData.proxy;

import analyzeData.model.Patient;
import lombok.Generated;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This interface is used as proxy for Feign Client Discovery
 * which allows to use the searchPatient microservice endpoints
 */
@FeignClient(name = "searchPatient", url = "172.23.0.4:8081")
@Generated
public interface SearchPatientProxy {

    @RequestMapping("/patient/list")
    public List<Patient> home();

    @RequestMapping("/patient/add")
    public Patient addPatientInformation(@RequestBody Patient patient);

    @GetMapping("/patient/update/{id}")
    public Patient updatePatientInformation(@PathVariable String id);

    @PostMapping("/patient/update")
    public Patient validateUpdate(@RequestBody Patient patient);

    @RequestMapping("/patient/get/{id}")
    public Patient getPatientById(@PathVariable String id);

    @RequestMapping("/patient/getAll")
    public List<Patient> getAll();

    @RequestMapping("/patient/getByLastname/{lastname}")
    public List<Patient> getPatientByLastname(@PathVariable String lastname);

}
