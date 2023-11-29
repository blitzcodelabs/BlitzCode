package org.blitzcode.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String python;

    @Column(nullable = false)
    private String java;

    @Column(nullable = false)
    private String wrongAnswer1;

    @Column(nullable = false)
    private String wrongAnswer2;

    @Column(nullable = false)
    private String wrongAnswer3;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
}
