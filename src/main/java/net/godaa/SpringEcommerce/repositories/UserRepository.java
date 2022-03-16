package net.godaa.SpringEcommerce.repositories;

import net.godaa.SpringEcommerce.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByMobile(String mobile);

    User findByPassword(String password);
}
