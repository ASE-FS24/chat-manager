package ch.nexusnet.chatmanager.message;

import ch.nexusnet.chatmanager.chat.Chat;
import ch.nexusnet.chatmanager.chat.repository.ChatRepositoryPort;
import ch.nexusnet.chatmanager.chat.exception.ChatWithSameUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{

    private final ChatRepositoryPort chatRepository;

    @Autowired
    public MessageServiceImpl(ChatRepositoryPort chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat sendMessage(Message message) {
        if (Objects.equals(message.getSenderId(), message.getReceiverId())) {
            throw new ChatWithSameUserException("Message cannot be sent to self. Sender: " + message.getSenderId());
        }

        message.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        message.setId(message.getSenderId() + message.getReceiverId() + message.getTimestamp());

        Optional<Chat> chat = chatRepository.findChat(message.getSenderId(), message.getReceiverId());
        if (chat.isPresent()) {
            chat.get().addMessage(message);
            return chatRepository.saveChat(chat.get());
        } else {
            Chat newChat = new Chat();
            newChat.setParticipant1(message.getSenderId());
            newChat.setParticipant2(message.getReceiverId());
            newChat.addMessage(message);
            return chatRepository.saveChat(newChat);
        }
    }
}
