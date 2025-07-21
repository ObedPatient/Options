/**
 * DTO for transferring UnitOfMeasure option data between layers.
 */
package rw.evolve.eprocurement.unit_of_measure_options.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UnitOfMeasureOptionDto {

    /** Unique identifier for the UnitOfMeasure option */
    @JsonProperty("unit_of_measure_option_id")
    private Long id;

    /** name of UnitOfMeasure option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the UnitOfMeasure option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;


}
