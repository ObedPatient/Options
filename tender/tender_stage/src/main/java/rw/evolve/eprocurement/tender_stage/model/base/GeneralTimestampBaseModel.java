/**
 * Base class providing timestamp fields for entities in the procurement system.
 * This class is used as a superclass for entities that require tracking of creation,
 */
package rw.evolve.eprocurement.tender_stage.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class GeneralTimestampBaseModel {


    @CreationTimestamp
    @Column(name = "created_at", length = 255, nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at", length = 255)
    private LocalDateTime updatedAt;


    @Column(name = "deleted_at", length = 30)
    private LocalDateTime deletedAt;
}
