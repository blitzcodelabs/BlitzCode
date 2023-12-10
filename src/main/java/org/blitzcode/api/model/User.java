package org.blitzcode.api.model;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import org.blitzcode.api.model.UserLessonProgress;

/**
 * The user class stores the User data gathered during Signup. The ID is provided by Firebase.
 * Every user has a unique email. The user's current target and base language are also stored here.
 * Lastly, User has a relation to UserLessonProgress for every lesson that the user has earned a point in.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"id", "progressList"})
public class User implements Serializable {

    @Id
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language baseLanguage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language targetLanguage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserLessonProgress> progressList;

}

