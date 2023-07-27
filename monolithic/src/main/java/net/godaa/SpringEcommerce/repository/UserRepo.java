package net.godaa.SpringEcommerce.repository;

import net.godaa.SpringEcommerce.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepo extends JpaRepository<User, UUID> {



    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findOneByActivationKey(String activationKey);

//    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findAllByIdNotNullAndActivatedIsTrue();


}
