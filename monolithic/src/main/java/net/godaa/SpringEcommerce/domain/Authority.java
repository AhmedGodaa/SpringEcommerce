package net.godaa.SpringEcommerce.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;


import java.util.Objects;

@Entity
@Table(name = "authority")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Authority {

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Authority authority = (Authority) o;
        return name != null && Objects.equals(name, authority.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}