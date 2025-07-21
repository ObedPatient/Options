/**
 * DTO for transferring Prerequisites Activity File Type option data between layers.
 */
package rw.evolve.eprocurement.prerequisites_activity_type_options.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PrerequisitesActivityFileTypeOptionDto {

    /** Unique identifier for the Prerequisites Activity File Type option */
    @JsonProperty("prerequisites_activity_file_type_option_id")
    private Long id;

    /** name of Prerequisites Activity File Type option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the Prerequisites Activity File Type option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
