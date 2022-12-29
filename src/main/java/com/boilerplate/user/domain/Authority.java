package com.boilerplate.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Authority implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @Column(length = 50)
    private String name;
}
