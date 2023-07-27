package net.godaa.SpringEcommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageURL;
    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    public Category addProduct(Product product) {
        this.products.add(product);
        product.setCategory(this);
        return this;
    }

    public Category removeProduct(Product product) {
        this.products.remove(product);
        product.setCategory(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.setCategory(null));
        }
        if (products != null) {
            products.forEach(i -> i.setCategory(this));
        }
        this.products = products;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


}
