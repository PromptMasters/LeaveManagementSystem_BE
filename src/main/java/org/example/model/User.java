package org.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String role;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @Column(nullable = false, length = 255)
    private String department;

    @OneToMany(mappedBy = "requestor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LeaveRequest> leaveRequests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LeaveBalance> leaveBalances;

    // Getters & Setters
}
