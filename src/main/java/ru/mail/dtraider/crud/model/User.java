package ru.mail.dtraider.crud.model;

import javax.persistence.*;
import java.util.List;

@Entity
//@NamedQuery(name = "User.findAll", query="select u from User u order by u.id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String firstName;


    private String lastName;


    private int age;


    private String email;


    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @OrderBy("authGroup")
    private List<AuthGroup> authGroupList;

    public User() {
    }

    public User(String firstName, String lastName, int age, String email, String password, List<AuthGroup> authGroupList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.authGroupList = authGroupList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AuthGroup> getAuthGroupList() {
        return authGroupList;
    }

    public void setAuthGroupList(List<AuthGroup> authGroupList) {
        if (this.authGroupList == null) {
            this.authGroupList = authGroupList;
        } else {
            this.authGroupList.retainAll(authGroupList);
            this.authGroupList.addAll(authGroupList);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authGroupList=" + authGroupList +
                '}';
    }
}
