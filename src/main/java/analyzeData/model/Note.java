package analyzeData.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * The model of note added to patient history for further analysis
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class Note {

    String id;
    @NotEmpty
    @NonNull
    String content;
    UUID uuid;

}
