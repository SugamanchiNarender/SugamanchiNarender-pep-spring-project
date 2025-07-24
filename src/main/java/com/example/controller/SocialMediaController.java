package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    
    public SocialMediaController(AccountService accountService , MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account>register(@RequestBody Account account){

        try {
            Account registerAccount = accountService.register(account);
            return ResponseEntity.ok(registerAccount);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){

        try {
            Account accountLogin = accountService.login(account.getUsername(), account.getPassword());
             return ResponseEntity.ok(accountLogin);
        } catch (IllegalArgumentException e) {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    @PostMapping("/messages")
    public ResponseEntity<Message> create(@RequestBody Message message){
        try {
             Message messageCreate = messageService.create(message);
             return ResponseEntity.ok(messageCreate);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getList(){
        return ResponseEntity.ok().body(messageService.getMessagesList()); 
        
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getById(@PathVariable Integer messageId){
        Message getMessage = messageService.getMessagesById(messageId);

        return ResponseEntity.ok(getMessage);

    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId){ 
        try {
            Integer deleteMessage = messageService.deleteMessages(messageId);
            return ResponseEntity.ok(deleteMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(null);
        }
        
    }
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId , @RequestBody Message message){
        try {
             Integer upadte = messageService.updateMessage(messageId, message.getMessageText());
             return ResponseEntity.ok(upadte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessageByUser(@PathVariable Integer accountId){
        return ResponseEntity.ok().body(messageService.getMessageByUserId(accountId));
    }


}
