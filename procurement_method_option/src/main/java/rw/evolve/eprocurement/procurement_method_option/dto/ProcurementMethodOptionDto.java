/**
 * DTO for transferring Account type option data between layers.
 */
package rw.evolve.eprocurement.procurement_method_option.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProcurementMethodOptionDto {

    /** Unique identifier for the Procurement method */
    @JsonProperty("procurement_method_option_id")
    private Long id;

    /** name of account method option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the procurement method option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
