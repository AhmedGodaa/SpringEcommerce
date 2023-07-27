package net.godaa.SpringEcommerce.repository;

import net.godaa.SpringEcommerce.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {


}
