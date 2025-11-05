package org.example.service;

import org.example.model.LeaveBalance;
import org.example.repository.LeaveBalanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LeaveBalanceService {
    public LeaveBalanceService(LeaveBalanceRepository leaveBalanceRepository);

    public Optional<LeaveBalance> getByUserId(Long userId);
}
