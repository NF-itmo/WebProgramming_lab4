package org.historyService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Entity
@Table(name = "ResultGroups", schema = "history_service")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ResultGroups_id_gen")
    @SequenceGenerator(name = "ResultGroups_id_gen", sequenceName = "ResultGroups_id_seq", allocationSize = 1, schema = "history_service")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer ownerId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String groupName;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = OffsetDateTime.now();
        }
    }
}