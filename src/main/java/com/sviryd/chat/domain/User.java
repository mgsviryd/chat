package com.sviryd.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sviryd.chat.converter.LocalDateTimeToTimestampConverter;
import com.sviryd.chat.domain.type.Male;
import com.sviryd.chat.domain.type.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@ToString(of = {"id", "username", "male"})
@EqualsAndHashCode(of = {"username", "male"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"authorMessages"})
@Entity
@Table(name = "usr", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;

    @NonNull
    @JsonView(Views.Username.class)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    @JsonView(Views.Male.class)
    private Male male;

    @CreationTimestamp
    @Convert(converter = LocalDateTimeToTimestampConverter.class)
    @JsonView(Views.CreationLDT.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationLDT = LocalDateTime.now();

    private String password;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    @JsonView(Views.Enabled.class)
    private boolean enabled;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> authorMessages = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return enabled;
    }

}
