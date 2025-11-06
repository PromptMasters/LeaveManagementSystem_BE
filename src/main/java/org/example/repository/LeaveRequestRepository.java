package org.example.repository;

import org.example.model.LeaveRequest;
import org.example.Enum.Status;
import org.example.Enum.LeaveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    @Query("""
                SELECT lr FROM LeaveRequest lr
                WHERE
                    (COALESCE(:keyword, '') = '' OR
                     LOWER(lr.requestor.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                     LOWER(lr.requestor.department) LIKE LOWER(CONCAT('%', :keyword, '%')))
                AND (:status IS NULL OR lr.status = :status)
                AND (:leaveType IS NULL OR lr.leaveType = :leaveType)
            """)
    Page<LeaveRequest> searchRequests(
            @Param("keyword") String keyword,
            @Param("status") Status status,
            @Param("leaveType") LeaveType leaveType,
            Pageable pageable);

    List<LeaveRequest> findByRequestor_Id(Long requestorId);

    @Query("""
                SELECT CASE WHEN COUNT(lr) > 0 THEN true ELSE false END
                FROM LeaveRequest lr
                WHERE lr.requestor.id = :userId
                  AND lr.status IN :statuses
                  AND (
                      (lr.startDate <= :endDate AND lr.endDate >= :startDate)
                  )
            """)
    boolean existsByRequestor_IdAndStatusInAndDateOverlap(
            @Param("userId") Long userId,
            @Param("statuses") List<Status> statuses,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

}
