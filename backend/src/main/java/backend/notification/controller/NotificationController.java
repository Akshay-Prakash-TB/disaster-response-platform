package backend.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.notification.entity.Notification;
import backend.notification.service.NotificationService;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService
            notificationService;

    @GetMapping("/{userId}")
    public List<Notification>
    getNotifications(
            @PathVariable Long userId) {

        return notificationService
                .getUserNotifications(
                        userId);
    }

    @PutMapping("/read/{id}")
    public void markAsRead(
            @PathVariable Long id) {

        notificationService
                .markAsRead(id);
    }
}