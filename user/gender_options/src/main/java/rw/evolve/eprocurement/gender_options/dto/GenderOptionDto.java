/**
 * DTO for transferring Gender option data between layers.
 */
package rw.evolve.eprocurement.gender_options.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GenderOptionDto {

    /** Unique identifier for the gender option */
    @JsonProperty("gender_option_id")
    private Long id;

    /** name of gender option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the gender option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
