package org.blitzcode.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Keeps track of a lesson-user pair, and stores the number of times the user completed a set of questions.
 */
@Entity
@Table(name = "user_lesson_progress")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLessonProgress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(name = "completed_points")
    private Integer completedPoints;
}