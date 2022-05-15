package net.godaa.SpringEcommerce.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
