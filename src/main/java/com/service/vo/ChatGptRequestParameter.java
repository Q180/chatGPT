package com.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptRequestParameter {

    String model = "gpt-3.5-turbo";

    List<ChatGptMessage> messages = new ArrayList<>();

    public void addMessages(ChatGptMessage message) {
        this.messages.add(message);
    }

}
