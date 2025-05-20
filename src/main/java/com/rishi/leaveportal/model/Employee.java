    package com.rishi.leaveportal.model;

    import jakarta.persistence.*;
    import lombok.*;

    import java.util.ArrayList;
    import java.util.List;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name = "employee")
    public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        private String department;

        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private UserInfo user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "manager_id")
        private Employee manager;

        @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
        @Column(nullable = true)
        private List<Employee> subordinates = new ArrayList<>();
    }
