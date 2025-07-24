package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    
    private MessageRepository messageRepository;

    @Autowired
    public MessageService (MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message create(Message message){
        if(message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 225){
            throw new IllegalArgumentException("Invalid MessageText");
        }
        if(messageRepository.findById(message.getPostedBy()).isEmpty()){
            throw new IllegalArgumentException("Not Found");
        }
        return messageRepository.save(message); 
        
    }

    public List<Message> getMessagesList(){
        return (List<Message>) messageRepository.findAll();
    }

    public Message getMessagesById(Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            return messageOptional.get();
        }
        return null;
    }

    public Integer deleteMessages(Integer messageId){
        if(messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;

        }
        throw new IllegalArgumentException("Not Found");
      
       
    }
    public Integer updateMessage(Integer messageId , String newMessagText){
        if(newMessagText.isBlank() || newMessagText.length() > 225){
            throw new IllegalArgumentException("Invalid MessageText");
        }
        Message message = messageRepository.findById(messageId)
        .orElseThrow(() -> new IllegalArgumentException("Not Found"));
        message.setMessageText(newMessagText);
        messageRepository.save(message);
        return 1;
    }
    public List<Message> getMessageByUserId(Integer accountId){
        return (List<Message>) messageRepository.findByPostedBy(accountId);
    }
}
