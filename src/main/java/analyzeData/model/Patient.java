package analyzeData.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.UUID;


/**
 * The model for patients gathering their personal information
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Patient {

    Integer id;
    @NonNull
    @NotEmpty
    String firstname;
    @NonNull
    @NotEmpty
    String lastname;
    @NonNull
    Gender gender;
    @NotEmpty
    @NonNull
    String address;
    @NonNull
    @NotEmpty
    String phone;
    @NonNull
    LocalDate birthdate;
    UUID uuid;

}
