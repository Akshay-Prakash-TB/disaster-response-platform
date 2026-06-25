package backend.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.notification.entity.Notification;
import backend.notification.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository
            notificationRepository;

    public Notification createNotification(
            Long userId,
            String title,
            String message) {

        Notification notification =
                new Notification();

        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setMessage(message);

        return notificationRepository
                .save(notification);
    }

    public List<Notification>
    getUserNotifications(
            Long userId) {

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(
                        userId);
    }

    public void markAsRead(
            Long id) {

        Notification notification =
                notificationRepository
                        .findById(id)
                        .orElseThrow();

        notification.setRead(true);

        notificationRepository
                .save(notification);
    }
}