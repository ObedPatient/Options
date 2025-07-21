/**
 * Data Transfer Object (DTO) for structuring error responses in the application.
 * Used to return standardized error details to clients when exceptions occur.
 */
package rw.evolve.eprocurement.execution_period_options.dto;

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
    private String response_message;

    /**
     * The response details, typically a JSON string of the response data.
     */
    private String responseStatus;

    /**
     * HTTP status code of the response.
     */
    private int statuscode;

    /**
     * Timestamp of the response.
     */
    private LocalDateTime timestamp;
}
