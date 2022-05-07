package net.godaa.SpringEcommerce.service;

import net.godaa.SpringEcommerce.domain.Category;
import net.godaa.SpringEcommerce.domain.Role;
import net.godaa.SpringEcommerce.domain.User;
import net.godaa.SpringEcommerce.repository.RoleRepo;
import net.godaa.SpringEcommerce.repository.UserRepo;
import net.godaa.SpringEcommerce.security.jwt.SecurityConstants;
import net.godaa.SpringEcommerce.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static net.godaa.SpringEcommerce.security.jwt.SecurityConstants.ROLE_USER;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(ROLE_USER);
        return userRepo.save(user);
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

    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }


    public void addRoleToUser(String username, String roleName) {
        User user = userRepo.findByUsername(username);
        user.setRole(roleName);


    }

    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }


}
