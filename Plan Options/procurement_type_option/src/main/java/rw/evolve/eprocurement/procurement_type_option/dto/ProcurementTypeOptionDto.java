/**
 * DTO for transferring Account type option data between layers.
 */
package rw.evolve.eprocurement.procurement_type_option.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProcurementTypeOptionDto {

    /** Unique identifier for the Procurement type */
    @JsonProperty("procurement_type_option_id")
    private Long id;

    /** name of account type option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the procurement type option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
