package org.example.pickupanddelivery.service;

import org.example.pickupanddelivery.model.Driver;
import org.example.pickupanddelivery.model.Message;
import org.example.pickupanddelivery.model.Order;
import org.example.pickupanddelivery.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DriverService driverService;

    @Autowired
    private OrderService orderService;

    public Message sendMessageToDriver(Long driverId, String content, String messageType, Long orderId) {
        Driver driver = driverService.getDriverById(driverId);
        Order order = orderId != null ? orderService.getOrderById(orderId) : null;

        Message message = new Message();
        message.setDriver(driver);
        message.setOrder(order);
        message.setContent(content);
        message.setMessageType(messageType);

        return messageRepository.save(message);
    }

    public Message sendReplyToMessage(Long parentMessageId, String replyContent, String messageType) {
        Message parentMessage = messageRepository.findById(parentMessageId).orElse(null);
        if (parentMessage == null) {
            return null;
        }

        Message reply = new Message();
        reply.setDriver(parentMessage.getDriver());
        reply.setOrder(parentMessage.getOrder());
        reply.setContent(replyContent);
        reply.setMessageType(messageType);
        reply.setParentMessage(parentMessage);
        reply.setStatus("READ"); // Replies are automatically marked as read

        // Mark parent message as replied
        parentMessage.setStatus("REPLIED");
        messageRepository.save(parentMessage);

        return messageRepository.save(reply);
    }

    public Message sendDriverReplyToMessage(Long parentMessageId, String replyContent) {
        Message parentMessage = messageRepository.findById(parentMessageId).orElse(null);
        if (parentMessage == null) {
            return null;
        }

        Message reply = new Message();
        reply.setDriver(parentMessage.getDriver());
        reply.setOrder(parentMessage.getOrder());
        reply.setContent(replyContent);
        reply.setMessageType("DRIVER_REPLY");
        reply.setParentMessage(parentMessage);
        reply.setStatus("READ"); // Driver replies are automatically marked as read

        // Mark parent message as replied
        parentMessage.setStatus("REPLIED");
        messageRepository.save(parentMessage);

        return messageRepository.save(reply);
    }

    public Message sendDriverMessageToCoordinator(Long driverId, String content, Long orderId) {
        Driver driver = driverService.getDriverById(driverId);
        Order order = orderId != null ? orderService.getOrderById(orderId) : null;

        Message message = new Message();
        message.setDriver(driver);
        message.setOrder(order);
        message.setContent(content);
        message.setMessageType("DRIVER_MESSAGE");

        return messageRepository.save(message);
    }

    public Message sendIssueMessage(Long driverId, String issueDescription, Long orderId) {
        return sendMessageToDriver(driverId, issueDescription, "ISSUE", orderId);
    }

    public Message sendInfoMessage(Long driverId, String info, Long orderId) {
        return sendMessageToDriver(driverId, info, "INFO", orderId);
    }

    public Message sendUrgentMessage(Long driverId, String urgentMessage, Long orderId) {
        return sendMessageToDriver(driverId, urgentMessage, "URGENT", orderId);
    }

    public List<Message> getMessagesForDriver(Long driverId) {
        return messageRepository.findByDriverId(driverId);
    }

    public List<Message> getUnreadMessagesForDriver(Long driverId) {
        return messageRepository.findByDriverIdAndStatus(driverId, "UNREAD");
    }

    public List<Message> getAllUnreadMessages() {
        return messageRepository.findByStatus("UNREAD");
    }

    public List<Message> getActiveParentMessages() {
        // Get all parent messages that are not resolved
        return messageRepository.findAll().stream()
                .filter(message -> message.getParentMessage() == null)
                .filter(message -> !"RESOLVED".equals(message.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Message> getActiveParentMessagesForDriver(Long driverId) {
        // Get parent messages for a specific driver that are not resolved
        return messageRepository.findByDriverId(driverId).stream()
                .filter(message -> message.getParentMessage() == null)
                .filter(message -> !"RESOLVED".equals(message.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Message> getRepliesForMessage(Long messageId) {
        return messageRepository.findByParentMessageId(messageId);
    }

    public Message markAsRead(Long messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setStatus("READ");
            message.setReadAt(LocalDateTime.now());
            return messageRepository.save(message);
        }
        return null;
    }

    public Message markAsResolved(Long messageId) {
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setStatus("RESOLVED");
            message.setResolvedAt(LocalDateTime.now());
            return messageRepository.save(message);
        }
        return null;
    }

    public List<Message> getMessagesByOrder(Long orderId) {
        return messageRepository.findByOrderId(orderId);
    }

    public long getUnreadMessageCount(Long driverId) {
        return messageRepository.findByDriverIdAndStatus(driverId, "UNREAD").size();
    }

    public long getTotalUnreadMessageCount() {
        return messageRepository.findByStatus("UNREAD").size();
    }
}