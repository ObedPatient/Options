/**
 * DTO for transferring User status option data between layers.
 */
package rw.evolve.eprocurement.user_status_option.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserStatusOptionDto {

    /** Unique identifier for the User status option */
    @JsonProperty("user_status_option_id")
    private String id;

    /** name of User status option*/
    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 150 characters")
    private String name;

    /** description of the User status option*/
    @JsonProperty("description")
    @NotNull(message = "description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
