package org.blitzcode.app.data;

import dev.hilla.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private @Nonnull String name;
    private @Nonnull String email;
    private @Nonnull Role role;

}