package org.example.service.impl;

import org.example.model.LeaveBalance;
import org.example.repository.LeaveBalanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeaveBalanceService implements LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;

    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
    }

    public Optional<LeaveBalance> getByUserId(Long userId) {
        return leaveBalanceRepository.findByUser_Id(userId);
    }
}
