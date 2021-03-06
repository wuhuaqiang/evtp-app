package com.hhdl.system.vo;

import com.hhdl.system.model.Role;
import com.hhdl.system.model.RoleResources;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVO extends Role {
    private List<RoleResources> roleResourcesList;
}
