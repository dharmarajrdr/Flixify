package com.flixify.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends Auditable {

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String displayName;

    @OneToOne
    private Account account;
}
