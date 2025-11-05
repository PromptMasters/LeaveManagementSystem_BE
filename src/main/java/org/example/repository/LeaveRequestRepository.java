package org.example.repository;

import org.example.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByRequestor_Id(Long requestorId);

    List<LeaveRequest> findByStatus(String status);

    List<LeaveRequest> findByRequestor_Manager_Id(Long managerId);
}
