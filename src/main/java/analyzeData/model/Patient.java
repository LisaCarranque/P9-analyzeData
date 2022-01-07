package analyzeData.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Size(min=1, max=40)
    String firstname;
    @NonNull
    @NotEmpty
    @Size(min=1, max=40)
    String lastname;
    @NonNull
    Gender gender;
    @NotEmpty
    @NonNull
    @Size(min=1, max=40)
    String address;
    @NonNull
    @NotEmpty
    @Size(min=1, max=40)
    String phone;
    @NonNull
    LocalDate birthdate;
    UUID uuid;

}