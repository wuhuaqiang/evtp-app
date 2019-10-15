package com.hhdl.system.vo;

import com.hhdl.system.model.User;
import com.hhdl.system.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends User {
    private List<UserRole> UserRoleList;
}
