/**
 * DTO for transferring Country option data between layers.
 */
package rw.evolve.eprocurement.country.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CountryOptionDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    @NotNull(message = "name is mandatory")
    @Size(max = 255, message = "Name cannot exceed 250 characters")
    private String name;

    @JsonProperty("dial_code")
    @NotNull(message = "Dial code is mandatory")
    @Size(max = 255, message = "Dial code cannot exceed 250 characters")
    private String dialCode;

    @JsonProperty("code")
    @NotNull(message = "Country Abbreviation is mandatory")
    @Size(max = 255, message = "Country Abbreviation cannot exceed 250 characters")
    private String code;

    @JsonProperty("description")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
