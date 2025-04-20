package warehouse.Service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import warehouse.LogicDTOs.DailyCountsDTO;
import warehouse.Repos.WarehouseMovementRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class DailyCounterService {
    private final WarehouseMovementRepo movementRepo;
    private final SimpMessagingTemplate messagingTemplate;

    private long storedCount = 0;
    private long retrievedCount = 0;

    @PostConstruct
    @Transactional
    public void initializeCounts() {
        try {
            LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            storedCount = movementRepo.countByActionAndTimeOfMovementAfter(0, startOfDay); // STORED = 0
            retrievedCount = movementRepo.countByActionAndTimeOfMovementAfter(1, startOfDay); // RETRIEVED = 1
            log.info("Initialized counts: stored={}, retrieved={}", storedCount, retrievedCount);
            pushCounts();
        } catch (Exception e) {
            log.error("Failed to initialize counts: {}", e.getMessage(), e);
            storedCount = 0;
            retrievedCount = 0;
            pushCounts();
        }
    }

    @Transactional
    public void incrementStoredCount() {
        storedCount++;
        pushCounts();
    }

    @Transactional
    public void incrementRetrievedCount() {
        retrievedCount++;
        pushCounts();
    }

    @Transactional
    public DailyCountsDTO getDailyCounts() {
        DailyCountsDTO dto = new DailyCountsDTO();
        dto.setStoredCount(storedCount);
        dto.setRetrievedCount(retrievedCount);
        return dto;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 12:00 AM daily
    @Transactional
    public void resetCounts() {
        log.info("Resetting daily counts at 12:00 AM");
        storedCount = 0;
        retrievedCount = 0;
        pushCounts();
    }

    private void pushCounts() {
        DailyCountsDTO dto = getDailyCounts();
        messagingTemplate.convertAndSend("/topic/daily-counts", dto);
    }

    @EventListener
    public void handleWebSocketSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        if ("/topic/daily-counts".equals(destination)) {
            pushCounts(); // Send current counts to new subscriber
        }
    }
}