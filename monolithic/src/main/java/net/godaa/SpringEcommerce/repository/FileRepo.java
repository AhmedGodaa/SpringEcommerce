package net.godaa.SpringEcommerce.repository;

import net.godaa.SpringEcommerce.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<File, String> {

}
