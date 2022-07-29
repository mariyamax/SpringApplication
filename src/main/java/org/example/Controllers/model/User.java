package org.example.Controllers.model;

import lombok.Data;
import org.example.Controllers.Enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long ID;
    @Column(name="email", unique = true)
    private String email;
    @Column(name="phoneNumber")
    private String phoneNumber;
    @Column(name="uname")
    private String uname;
    @Column(name="password", length = 1000)
    private String password;
    @Column(name="active")
    private boolean active;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;
    @Column(name="time")
    private LocalDateTime timeOfCreate;
    @Column(name="lastVisit")
    private LocalDateTime lastVisitTime;
    @Column(name="Coins")
    private Integer coins;
    @Column(name="regularVisit")
    private Integer regular;

    @ElementCollection(targetClass = Role.class, fetch =FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER,mappedBy = "user")
    private List<Plant> plants = new ArrayList<>();

    @PrePersist
    private void init(){
        timeOfCreate = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public boolean isAdmin(){
        return roles.contains(Role.ROLE_ADMIN);
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
        return active;
    }
}
