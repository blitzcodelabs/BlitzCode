package org.blitzcode.api.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // I don't think this is supposed to be here, if firebase generates ID
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    private Boolean admin;
}
