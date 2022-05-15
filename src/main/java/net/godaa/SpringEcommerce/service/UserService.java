package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.Authority;
import net.godaa.SpringEcommerce.domain.Customer;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.exceptions.EmailAlreadyUsedException;
import net.godaa.SpringEcommerce.exceptions.InvalidPasswordException;
import net.godaa.SpringEcommerce.exceptions.UsernameAlreadyUsedException;
import net.godaa.SpringEcommerce.repository.AuthorityRepo;
import net.godaa.SpringEcommerce.repository.CustomerRepo;
import net.godaa.SpringEcommerce.repository.UserRepo;
import net.godaa.SpringEcommerce.security.SecurityUtil;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import net.godaa.SpringEcommerce.service.dto.UserDTO;
import net.godaa.SpringEcommerce.utils.FileUploadUtil;
import net.godaa.SpringEcommerce.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {


    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthorityRepo authorityRepo;

    @Autowired
    CustomerRepo customerRepo;


    @Autowired
    PasswordEncoder passwordEncoder;


//    Register User


    public User createUser(User userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername().toLowerCase());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setImage(userRequest.getImage());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setActivated(true);
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail().toLowerCase());
        }
        if (userRequest.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            for (Authority authority : userRequest
                    .getAuthorities()) {
                Optional<Authority> byId = authorityRepo.findById(authority.getName());
                if (byId.isPresent()) {
                    Authority authority1 = byId.get();
                    authorities.add(authority1);
                }
            }
            user.setAuthorities(authorities);


        }
        userRepo.save(user);
        log.debug("User Created {}", user);
        return user;
    }

    public Optional<UserDTO> updateUser(UserDTO userRequest) {

//        Getting Optional of User with id from userRepo
        return Optional.of(userRepo.findById(userRequest.getId()))
//                Checking if user is present
                .filter(Optional::isPresent)
//                Getting user from Optional
                .map(Optional::get)
//                Handling the existing user
                .map(user -> {
                    user.setUsername(userRequest.getUsername().toLowerCase());
                    user.setFirstName(userRequest.getFirstName());
                    user.setLastName(userRequest.getLastName());
                    if (userRequest.getEmail() != null) {
                        user.setEmail(userRequest.getEmail());
                    }
                    user.setImage(userRequest.getImage());
                    user.setActivated(userRequest.isActivated());
//                    <  ------ Setting Authorities -------  >
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
//                    Getting Authorities from userRequest
                    userRequest.
                            getAuthorities()
                            .stream()
                            .map(authorityRepo::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAuthorities::add);
                    user.setAuthorities(managedAuthorities);
//                               <  ------ -------  >
                    return user;

                })
                .map(_user -> new UserDTO(_user));


    }


    //      Edit Logged In Details ex: change name after login
    public void editUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        SecurityUtil
                .getCurrentUserLogin()
                .flatMap(userRepo::findByUsername)
                .ifPresent(user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
//                    Email defined as NotNull in Entity if email = null error occurred
                    if (email != null) {
                        user.setEmail(email.toLowerCase());
                    }
                    user.setImage(imageUrl);
                    log.debug("Changed Information for User: {}", user);

                });

    }

    public void deleteUser(String username) {
        userRepo.findByUsername(username).
                ifPresent(
                        user -> {
                            userRepo.delete(user);
                            log.debug("Deleted User: {}", user);
                        });
    }

    //   Register User
//    1 - Check if there is an account with username or email
//    2 - Delete if there is account with same information and not activated
//    3 - If  Activated delete it and create new one

    public User registerUser(UserDTO userRequest, String password) {
        userRepo.findByUsername(userRequest.getUsername().toLowerCase())
                .ifPresent(user -> {
                    boolean removed = removeNonActiveUser(user);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException();
                    }
                });
        userRepo.findByEmail(userRequest.getEmail().toLowerCase())
                .ifPresent(user -> {
                    boolean removed = removeNonActiveUser(user);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                });
        User user = new User();
        user.setUsername(userRequest.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail().toLowerCase());
        }
        user.setImage(userRequest.getImage());
        user.setActivated(false);
        user.setActivationKey(RandomUtil.generateActivationKey());
//        Add Authorities To User
        Set<Authority> authorities = new HashSet<>();
        authorityRepo.findById(SecurityConstants.ROLE_USER).ifPresent(authorities::add);
        user.setAuthorities(authorities);
        userRepo.save(user);
//        Set Customer
        Customer customer = new Customer();
        customer.setGender(userRequest.getGender());
        customer.setPhone(userRequest.getPhone());
        customer.setAddressLine1(userRequest.getAddressLine1());
        customer.setAddressLine2(userRequest.getAddressLine2());
        customer.setCity(userRequest.getCity());
        customer.setCountry(userRequest.getCountry());
        customer.setUser(user);
        customerRepo.save(customer);
        log.debug("Created Information for User: {}", user);
        return user;


    }


    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepo.findAll().stream().map(Authority::getName).collect(Collectors.toList());

    }

    public void changePassword(String currentPassword, String newPassword) {
        SecurityUtil
                .getCurrentUserLogin()
                .flatMap(username -> userRepo.findByUsername(username))
//                userRepo::findByUsername  =  username -> userRepo.findByUsername(username)
                .ifPresent(user -> {
                    String password = user.getPassword();
                    if (!passwordEncoder.matches(currentPassword, password)) {
                        throw new InvalidPasswordException();
                    }
                    user.setPassword(passwordEncoder.encode(newPassword));
                    log.debug("Changed password for User: {}", user);
                });


    }

    public boolean removeNonActiveUser(User user) {
        if (user.isActivated()) {
            return false;
        } else {
            userRepo.delete(user);
            userRepo.flush();
            return true;
        }


    }

    //     Not activated users should be automatically deleted after 3 days.
//     This is scheduled to get fired everyday, at 01:00 (am).
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepo
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(
                        user -> {
                            log.debug("Deleting not activated user {}", user.getUsername());
                            userRepo.delete(user);
                        }
                );
    }


    public Optional<User> activateUser(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepo
                .findOneByActivationKey(key)
                .map(
                        user -> {
//                           Activate user
                            user.setActivated(true);
                            user.setActivationKey(null);
                            log.debug("Activated user: {}", user);
                            return user;
                        });
    }


    public User addImageToUser(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename()).toLowerCase().replaceAll(" ", "_");
            user.setImage(fileName);
            String uploadDir = "user-photos/" + user.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            return user;
        } else {
            return user;

        }
    }


    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtil.getCurrentUserLogin().flatMap(userRepo::findOneWithAuthoritiesByLogin);
    }

}
