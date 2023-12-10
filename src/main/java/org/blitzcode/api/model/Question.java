package org.blitzcode.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Questions have text field to keep the question, a list of wrong options that will be shown.
 * The correct answer is inserted randomly when the question is served to the frontend.
 * Every Question object is specific to the base/target language combination.
 */
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
    private String text;

    @Column(nullable = false)
    private String correctAnswer;

    @ElementCollection
    @CollectionTable(name = "wrong_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option")
    private List<String> wrongOptions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language baseLanguage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language targetLanguage;
}
