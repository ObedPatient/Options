/**
 * Data Transfer Object (DTO) for structuring error responses in the application.
 * Used to return standardized error details to clients when exceptions occur.
 */
package rw.evolve.eprocurement.theme_status.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessageDto {

    /**
     * The message content of the response.
     */
    @JsonProperty("message")
    private String message;

    /**
     * The response details, typically a JSON string of the response data.
     */
    @JsonProperty("status")
    private String status;

    /**
     * Timestamp of the response.
     */
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
}
