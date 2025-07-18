/**
 * DTO for transferring Scheme option data between layers.
 */
package rw.evolve.eprocurement.schemes_option.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SchemeOptionDto {

    /** Unique identifier for the scheme option */
    @JsonProperty("scheme_option_id")
    private Long id;

    /** name of scheme option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the scheme option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

}
