package com.LTS.Backend.models;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.Manager;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "leaves")
public class Leaves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "approved_manager_id")
    private User approvedManager;

    private String leaveStatus;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String managerComment;

}
