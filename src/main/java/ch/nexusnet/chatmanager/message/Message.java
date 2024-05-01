package ch.nexusnet.chatmanager.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String id;
    private String content;
    private String senderId;
    private String receiverId;
    private String timestamp;
}
