package net.godaa.SpringEcommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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
public class User  {
    // Elements that need to be added to constructor and have Relationship


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

    @OneToOne(cascade = {CascadeType.ALL},mappedBy = "user")
    @JoinColumn(name = "userId")
    private Cart cart;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();

    public User(Long id, String username, String email, String password, Collection<Role> roles,Cart cart) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.cart = cart;
    }
}
