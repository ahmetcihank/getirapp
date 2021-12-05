package org.getir.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class TimestampEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    @Getter
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false)
    @Getter
    private Date updated;

    @PrePersist
    protected void onCreate() {
        updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }
}
