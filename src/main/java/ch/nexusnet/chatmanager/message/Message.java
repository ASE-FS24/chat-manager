package ch.nexusnet.chatmanager.message;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class Message {

    private String id;

    @NotEmpty
    private String content;

    @NotEmpty
    private String sender;

    @NotEmpty
    private String receiver;

    private String timestamp;
}
