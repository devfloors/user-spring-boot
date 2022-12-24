package com.boilerplate.login.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class UserEntity {
    @Id
    private Integer id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;

}
