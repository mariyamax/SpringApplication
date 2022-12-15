package org.example.Models;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long ID;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "coins")
    private Integer coins;
    @Column(name = "regular_visit")
    private Integer regular;
    @Column(name = "last_visit")
    private LocalDateTime lastVisitTime;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Plants> plants = new ArrayList<>();
    @Column(name = "image_id")
    private Long imageId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("user"));
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
