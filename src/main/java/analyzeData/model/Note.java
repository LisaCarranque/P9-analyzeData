package analyzeData.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Size(min=1, max=40)
    String content;
    UUID uuid;

}