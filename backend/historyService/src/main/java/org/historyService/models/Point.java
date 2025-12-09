package org.historyService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Entity
@Table(name = "PointResults", schema = "history_service")
@Builder @NoArgsConstructor @AllArgsConstructor
@Getter
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PointResults_id_gen")
    @SequenceGenerator(name = "PointResults_id_gen", sequenceName = "PointResults_id_seq", allocationSize = 1, schema = "history_service")
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "group_id")
    private Group group;

    @NotNull
    @Column(name = "x", nullable = false)
    private Float x;

    @NotNull
    @Column(name = "y", nullable = false)
    private Float y;

    @NotNull
    @Column(name = "r", nullable = false)
    private Float r;

    @NotNull
    @Column(name = "is_hitted", nullable = false)
    private boolean isHitted;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private OffsetDateTime timestamp;
}