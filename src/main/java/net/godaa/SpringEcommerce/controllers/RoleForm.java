package net.godaa.SpringEcommerce.controllers;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.godaa.SpringEcommerce.models.ERole;

@Data
@NoArgsConstructor
class RoleForm {
    private String username;
    private ERole role;


}
