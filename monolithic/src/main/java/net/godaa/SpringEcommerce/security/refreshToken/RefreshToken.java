package net.godaa.SpringEcommerce.security.refreshToken;

import jakarta.persistence.*;
import lombok.*;
import net.godaa.SpringEcommerce.domain.User;


import java.time.Instant;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private Instant expiryDate;
}
