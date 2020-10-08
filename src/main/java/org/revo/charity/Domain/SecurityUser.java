package org.revo.charity.Domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import org.revo.charity.Controller.View;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SecurityUser implements UserDetails {
    @JsonView(View.BasicUser.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
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
