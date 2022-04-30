package net.godaa.SpringEcommerce.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "category")
    private String productCategory;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "manufacturer")
    private String productManufacturer;

    @NotEmpty(message = "Product Name is mandatory")
    @Column(name = "name")
    private String productName;

    @NotNull(message = "Please provide some price")
    @Column(name = "price")
    private double productPrice;

    @Column(name = "unit")
    private String unitStock;

    @Transient
    private MultipartFile productImage;
}
