package com.flixify.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity(name = "users")
@EqualsAndHashCode(callSuper = false)
public class User extends Auditable {

    private String userName;

    private String displayName;

    @OneToOne
    private Account account;

    public User(String userName, String displayName, Account account) {
        this.userName = userName;
        this.displayName = displayName;
        this.account = account;
    }
}
