package com.josko.passenger.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
public class SliceMetaData {

    @Column(name = "created_ts")
    private Instant createdTs;

    @Column(name = "updated_ts")
    private Instant updatedTs;

    @Column(name = "datasource_name")
    private String datasource;

    @Column(name = "purge_ts")
    private Instant purgeTs;

    @PrePersist
    public void onPrePersist() {
        this.createdTs = Instant.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updatedTs = Instant.now();
    }

}
