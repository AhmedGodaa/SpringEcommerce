package net.godaa.SpringEcommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = AUTO)

    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean enabled;
    private String image;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "user")
    @JoinColumn(name = "userId")
    private Cart cart;

    private String role;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", enabled=" + enabled +
                ", image='" + image + '\'' +
                ", cart=" + cart +
                ", role='" + role + '\'' +
                '}';
    }
}
