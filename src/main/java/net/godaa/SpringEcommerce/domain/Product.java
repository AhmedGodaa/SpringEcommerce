package net.godaa.SpringEcommerce.domain;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;


@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    private String productName;

    private double productPrice;

    @Column(name = "unit")
    private String unitStock;

    @Transient
    private MultipartFile productImage;




    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


}
