package com.LTS.Backend.dto;

import lombok.Data;

@Data
public class ReactToLeaveRequestDTO {
    private Long managerId;
    private String status;
    private String managerComments;
}
