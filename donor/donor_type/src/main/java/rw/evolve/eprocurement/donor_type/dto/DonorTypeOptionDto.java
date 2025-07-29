/**
 * DTO for transferring Donor type option data between layers.
 */
package rw.evolve.eprocurement.donor_type.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DonorTypeOptionDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
