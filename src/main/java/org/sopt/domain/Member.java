package org.sopt.domain;

import jakarta.persistence.*;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    protected Member() {}

    public Member(String email, String password, String name) {
        validate(name);
        this.email = email;
        this.password = password;
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_NAME);
        }
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
}