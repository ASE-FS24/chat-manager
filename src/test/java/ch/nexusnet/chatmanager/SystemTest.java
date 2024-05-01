package ch.nexusnet.chatmanager;

import ch.nexusnet.chatmanager.aws.dynamodb.repositories.ChatCrudRepository;
import ch.nexusnet.chatmanager.message.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SystemTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ChatCrudRepository chatCrudRepository;

    @BeforeEach
    public void setup() {
        chatCrudRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        chatCrudRepository.deleteAll();
    }

    private String toJson(Object obj) throws Exception {
        return objectMapper.writeValueAsString(obj);
    }


    @Test
    void systemTest() throws Exception {
        String user1 = "user1";
        String user2 = "user2";

        mockMvc.perform(get("/chats/" + user1 + "/" + user2))
                .andExpect(status().isNotFound());

        Message message = new Message();
        message.setSenderId(user1);
        message.setReceiverId(user2);
        message.setContent("Hello");

        mockMvc.perform(post("/messages")
                .contentType("application/json")
                .content(toJson(message)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/chats/" + user1 + "/" + user2)).andExpect(status().isOk());

        mockMvc.perform(get("/chats/" + user2 + "/" + user1)).andExpect(status().isOk());

        message = new Message();
        message.setSenderId(user1);
        message.setReceiverId(user2);
        message.setContent("I am user1");

        mockMvc.perform(post("/messages")
                .contentType("application/json")
                .content(toJson(message)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/chats/" + user1 + "/" + user2)).andExpect(status().isOk());

        message = new Message();
        message.setSenderId(user2);
        message.setReceiverId(user1);
        message.setContent("Hello, I am user2");

        mockMvc.perform(post("/messages")
                .contentType("application/json")
                .content(toJson(message)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/chats/" + user1 + "/" + user2)).andExpect(status().isOk());
    }
}
