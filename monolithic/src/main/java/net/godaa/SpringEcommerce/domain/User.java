package net.godaa.SpringEcommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Table(name = "user",
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
    private UUID id;
    @NotNull
    @Pattern(regexp = SecurityConstants.LOGIN_REGEX)
    @Column(unique = true, nullable = false)
    private String username;
    @Email
    @NonNull
    @Column(unique = true)
    private String email;
    //    @JsonIgnore
    @NotNull
    @JsonIgnore
    private String password;

    private String firstname;

    private String lastname;
    @JsonIgnore
    private String activationKey;

    private boolean activated = false;

    private String image;

    //    private String role;
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
//    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();


}
