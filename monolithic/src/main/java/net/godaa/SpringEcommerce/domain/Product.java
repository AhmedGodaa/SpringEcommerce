package net.godaa.SpringEcommerce.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import static jakarta.persistence.GenerationType.AUTO;


@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    @NotNull
    private double price;

    @Column(name = "unit")
    private String unitStock;

    @Transient
    private MultipartFile productImage;

    @ManyToOne(optional = false)
    @NonNull
    @JoinColumn(name = "category_id")
    private Category category;


}
