package com.experianhealth.ciam.forgerock.service;

import com.experianhealth.ciam.forgerock.model.Role;
import com.experianhealth.ciam.forgerock.model.RoleMember;

import java.util.List;

public interface ManagedRoleService extends GeneralForgeRockIDMService<Role> {
    List<RoleMember> getRoleMembers(String token, String roleId);
}
