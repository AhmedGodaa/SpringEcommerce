package net.godaa.SpringEcommerce.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.godaa.SpringEcommerce.Customer.Customer;
import org.springframework.web.multipart.MultipartFile;

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
public class User {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean enabled;

    @OneToOne(mappedBy = "user")
    private Customer customer;

    @Transient
    private MultipartFile productImage;

@ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
private Collection<Role> roles = new ArrayList<>();





//    public void addRole(Role role) {
////       We need to add role to user && user to roles
//
////  1-     We added  Set<Tutorial> tutorials in class Tag the tutorial
//        roles.add(role);
////  2-     We added  Set<Tutorial> tutorials in class Tag the tutorial
//        tag.getTutorials().add(this);
//    }



}
