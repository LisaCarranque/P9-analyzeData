package analyzeData.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Size(min=1, max=40)
    String firstname;
    @NonNull
    @NotEmpty
    @Size(min=1, max=40)
    String lastname;
    @NonNull
    String birthdate;
    @NonNull
    String gender;
    @NonNull
    @NotEmpty
    @Size(min=1, max=40)
    String address;
    @NonNull
    @NotEmpty
    @Size(min=1, max=40)
    String phone;
    UUID uuid;

}