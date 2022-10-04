package com.slapples.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";

    private String name;

    @Id
    @GeneratedValue
    @Column(name = "ROLE_ID")
    private Long roleId;

    @ManyToMany
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(getName(), role.getName()) && Objects.equals(getRoleId(), role.getRoleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getRoleId());
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
