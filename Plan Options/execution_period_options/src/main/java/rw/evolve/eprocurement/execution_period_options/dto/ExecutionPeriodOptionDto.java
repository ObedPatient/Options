/**
 * DTO for transferring Execution period option data between layers.
 */
package rw.evolve.eprocurement.execution_period_options.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExecutionPeriodOptionDto {

    /** Unique identifier for the execution_period option */
    @JsonProperty("execution_period_option_id")
    private Long id;

    /** name of execution_period option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the execution_period option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
