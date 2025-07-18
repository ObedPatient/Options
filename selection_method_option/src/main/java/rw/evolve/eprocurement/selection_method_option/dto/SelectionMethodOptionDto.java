/**
 * DTO for transferring Account type option data between layers.
 */
package rw.evolve.eprocurement.selection_method_option.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SelectionMethodOptionDto {

    /** Unique identifier for the selection method */
    @JsonProperty("selection_method_option_id")
    private Long id;

    /** name of selection method option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the selection method option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

}
