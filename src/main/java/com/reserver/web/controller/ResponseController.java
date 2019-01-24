package com.reserver.web.controller;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@LineMessageHandler
public class ResponseController {
    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("Message");
        System.out.println("event" + event);
        final String originaMessageText = "やほー!モノマネするよ" + "\r\n" + event.getMessage().getText();
        return new TextMessage(originaMessageText);
    }
}
