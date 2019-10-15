package com.hhdl.system.model;

import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Xiaoyue Xiao
 */
@Getter
@ToString
public class CustomUserDetails extends UserDO implements Serializable {

    private static final long serialVersionUID = 1702923242319850756L;

    private final boolean enabled;
    private final boolean accountNonExpired;
    private final boolean credentialsNonExpired;
    private final boolean accountNonLocked;

    public CustomUserDetails(UserDO user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked) {
        if (user != null
                && !StringUtils.isBlank(user.getUsername())
                && !StringUtils.isBlank(user.getPassword())) {
            setId(user.getId());
            setUsername(user.getUsername());
            setPassword(user.getPassword());
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

}
