package backend.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.notification.entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification>
    findByUserIdOrderByCreatedAtDesc(
            Long userId);
}