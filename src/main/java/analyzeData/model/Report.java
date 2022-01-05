package analyzeData.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * The model for the report e.g. the outcome of the analysis of patient information and related notes
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Report {

    Probability probability;
    @NotEmpty
    @NonNull
    String firstname;
    @NonNull
    @NotEmpty
    String lastname;
    @NonNull
    String birthdate;
    @NonNull
    String gender;
    @NonNull
    @NotEmpty
    String address;
    @NonNull
    @NotEmpty
    String phone;
    UUID uuid;

}
