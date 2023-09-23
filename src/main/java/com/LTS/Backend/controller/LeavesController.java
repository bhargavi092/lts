package com.LTS.Backend.controller;

import com.LTS.Backend.dto.ReactToLeaveRequestDTO;
import com.LTS.Backend.exception.CustomErrorResponse;
import com.LTS.Backend.models.Leaves;
import com.LTS.Backend.models.User;
import com.LTS.Backend.repository.UserRepository;
import com.LTS.Backend.service.LeavesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeavesController {

    @Autowired
    private LeavesService leavesService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/employee/apply-leave/{email}")
    public ResponseEntity<?> applyLeave(@PathVariable String email ,@RequestBody Leaves leave){
        try{

            User user = userRepository.findByEmail(email);
            if( user!= null){
                Long userId = user.getId();
                leave.setUserId(userId);
                Leaves appliedLeave = leavesService.applyLeave(leave);
                return ResponseEntity.ok(appliedLeave);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

        }
        catch (Exception e){
            String errorMessage = "An error occurred during leave apply:"+ e.getMessage();
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping("/employee/track-leaves/{userId}")
    public ResponseEntity<?> getAllUserLeaves(@PathVariable Long userId){
        try{
            List<Leaves> leaves = leavesService.getAllUserLeaves(userId);
            if(leaves!= null){
                return ResponseEntity.ok(leaves);
            }
            else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
            }
        }
        catch (Exception e){
            String errorMessage = "An error occurred during leave tracking:"+ e.getMessage();
            CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping("/manager/new-requests")
    public ResponseEntity<?> getAllPendingLeaves(){
        List<Leaves> pendingLeaves = leavesService.getAllPendingLeaves();

        if(!pendingLeaves.isEmpty()){
            return ResponseEntity.ok(pendingLeaves);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data");
        }
    }

    @GetMapping("/manager/overview")
    public ResponseEntity<?> getAllPastLeaves(){
        List<Leaves> pastLeaves = leavesService.getAllNonPendingLeaves();
        if(pastLeaves!=null){
            return ResponseEntity.ok(pastLeaves);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No past leaves");
        }
    }


    @PostMapping("/manager/react-to-leave/{leaveId}")
    public ResponseEntity<?> reactToLeave(@PathVariable Long leaveId ,
                                          @RequestBody ReactToLeaveRequestDTO reactToLeaveRequestDTO){

        Leaves updatedLeave = leavesService.reactToLeaves(leaveId,
                reactToLeaveRequestDTO.getManagerId(),reactToLeaveRequestDTO.getStatus(),
                reactToLeaveRequestDTO.getManagerComments()
        );

        if(updatedLeave!=null){
            return ResponseEntity.ok(updatedLeave);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/employee/delete-leave/{leaveId}")
    public ResponseEntity<?> deletePendingLeave(@PathVariable Long leaveId) {
        boolean isDeleted = leavesService.deletePendingLeave(leaveId);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
