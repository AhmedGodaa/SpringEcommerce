package net.godaa.SpringEcommerce.repository;


import net.godaa.SpringEcommerce.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorityRepo extends JpaRepository<Authority, String> {


}
