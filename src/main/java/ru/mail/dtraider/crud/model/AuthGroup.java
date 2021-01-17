package ru.mail.dtraider.crud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuthGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String authGroup;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthGroup() {
        return authGroup;
    }

    public void setAuthGroup(String authGroup) {
        this.authGroup = authGroup;
    }

    @Override
    public String toString() {
        return "AuthGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authGroup='" + authGroup + '\'' +
                '}';
    }
}
