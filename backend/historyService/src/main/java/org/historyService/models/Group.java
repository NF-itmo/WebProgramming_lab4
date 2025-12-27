package org.historyService.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private OffsetDateTime timestamp;
}