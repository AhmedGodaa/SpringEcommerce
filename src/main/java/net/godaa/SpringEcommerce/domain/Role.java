package net.godaa.SpringEcommerce.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}