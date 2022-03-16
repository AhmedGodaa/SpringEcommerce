package net.godaa.SpringEcommerce.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    private String email;

    @Column(name = "password")
    @Length(min = 8, message = "*Your password must have at least 8 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;
//
//    private String firstName;
//    private String lastName;

    @NotNull
    private String mobile;
//
//    private int emailStatus;
//    private int mobileStatus;
//
//    @CreationTimestamp
//    private Date createdAt;
//
//    @UpdateTimestamp
//    private Date updatedAt;


//    private int otp;
//    private String gender;
//    private Date date;
//    private String webToken;
//    private String androidToken;
//    private String iosToken;
//    private String rememberToken;


//    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
//    private List<UserAddress> userAddresses;


//    public User(Long id, List<UserAddress> userAddresses) {
//        this.id = id;
//        this.userAddresses = userAddresses;
//    }
}
