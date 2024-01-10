package com.hyperface.ems.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;

@Entity
@Table
@Transactional
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;
}
