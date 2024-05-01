package ch.nexusnet.chatmanager.chat;

import ch.nexusnet.chatmanager.chat.exception.ChatNotFoundException;
import ch.nexusnet.chatmanager.chat.exception.ChatWithSameUserException;
import ch.nexusnet.chatmanager.chat.repository.ChatRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepositoryPort chatRepository;

    public ChatServiceImpl(ChatRepositoryPort chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat getChat(String participant1, String participant2) {
        if (Objects.equals(participant1, participant2)) {
            throw new ChatWithSameUserException("Participants must be different. Participant: " + participant1);
        }
        return chatRepository.findChat(participant1, participant2).orElseThrow(() -> new ChatNotFoundException("Chat not found for participants: " + participant1 + " and " + participant2));
    }

    @Override
    public List<Chat> getChatsForParticipant(String participant1) {
        return chatRepository.getChatsForParticipant(participant1);
    }
}
