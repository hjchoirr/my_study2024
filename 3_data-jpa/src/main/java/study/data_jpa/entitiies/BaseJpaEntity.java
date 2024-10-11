package study.data_jpa.entitiies;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseJpaEntity {
    @Column(updatable = false)
    private LocalDateTime createdDt;
    private LocalDateTime lastModifiedDt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDt = now;
        this.lastModifiedDt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedDt = LocalDateTime.now();
    }
}

