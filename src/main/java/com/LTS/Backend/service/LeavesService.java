package com.LTS.Backend.service;

import com.LTS.Backend.models.Leaves;
import com.LTS.Backend.repository.LeavesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LeavesService {

    @Autowired
    private LeavesRepository leavesRepository;

    public Leaves applyLeave(Leaves leave){
        leave.setLeaveStatus("Pending");
        return leavesRepository.save(leave);
    }

    public List<Leaves> getAllUserLeaves(Long userId){
        return leavesRepository.findByUserId(userId);
    }

    public List<Leaves> getAllPendingLeaves(){
        return  leavesRepository.findByLeaveStatus("Pending");
    }

    public List<Leaves> getAllNonPendingLeaves(){
        return  leavesRepository.findByLeaveStatusIn(Arrays.asList("Accepted","Rejected"));
    }

    public Leaves reactToLeaves(Long leaveId,Long managerId,String status , String managerComment){
        Leaves leave = leavesRepository.findById(leaveId).orElse(null);

        if(leave!=null && ("Accepted".equals(status)|| "Rejected".equals(status)) ){
            leave.setLeaveStatus(status);
            leave.setApprovedManagerId(managerId);
            leave.setManagerComment(managerComment);
            return leavesRepository.save(leave);
        }
        else{
            return null;
        }
    }

    public Boolean deletePendingLeave(Long leaveId){
        Leaves leave = leavesRepository.findById(leaveId).orElse(null);

        if(leave!=null && ("Pending".equals(leave.getLeaveStatus()) )  ){
            leavesRepository.delete(leave);
            return true;
        }
        else{
            return false;
        }
    }
}
