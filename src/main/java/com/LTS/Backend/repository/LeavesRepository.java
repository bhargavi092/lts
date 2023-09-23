package com.LTS.Backend.repository;

import com.LTS.Backend.models.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Array;
import java.util.List;

public interface LeavesRepository extends JpaRepository<Leaves, Long> {

    public List<Leaves> findByUserId(Long userId);
    List<Leaves> findByLeaveStatus(String leaveStatus);

    List<Leaves> findByLeaveStatusIn(List leavesStatus);
}
