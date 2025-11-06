package org.example;

import org.example.service.LeaveBalanceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

    private final LeaveBalanceService leaveBalanceService;
    private final JdbcTemplate jdbcTemplate;

    public DemoApplication(LeaveBalanceService leaveBalanceService, JdbcTemplate jdbcTemplate) {
        this.leaveBalanceService = leaveBalanceService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello World");

        // Kiểm tra kết nối DB
        try {
            String dbVersion = jdbcTemplate.queryForObject("SELECT version()", String.class);
            System.out.println("Database connection successful. DB Version: " + dbVersion);
        } catch (Exception e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    public void incrementRemainingDays() {
        leaveBalanceService.incrementAllRemainingDays();
        System.out.println("Incremented remaining days for all users at " + java.time.LocalDateTime.now());
    }

}