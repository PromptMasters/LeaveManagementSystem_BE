package org.example;

import org.example.service.LeaveBalanceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    private final LeaveBalanceService leaveBalanceService;

    public DemoApplication(LeaveBalanceService leaveBalanceService) {
        this.leaveBalanceService = leaveBalanceService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World");
    }

    // @Scheduled(fixedRate = 600000) // Chạy mỗi 5 giây
    @Scheduled(cron = "0 0 0 1 1 *")
    public void incrementRemainingDays() {
        leaveBalanceService.incrementAllRemainingDays();
        System.out.println("Incremented remaining days for all users at " + java.time.LocalDateTime.now());
    }
}
