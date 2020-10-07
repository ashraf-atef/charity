package org.revo.charity.Domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.revo.charity.Controller.View;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SecurityUser implements UserDetails {
    @JsonView(View.BasicUser.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonView(View.BasicUser.class)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
