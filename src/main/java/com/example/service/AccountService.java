package com.example.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    
    private AccountRepository accountRepository;

    @Autowired
    public AccountService (AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account ){

        if(account.getUsername() == null || account.getUsername().isBlank() || account.getUsername().length() < 4){
           throw new IllegalArgumentException("Invalid Username");
        }
        if(accountRepository.findByUsername(account.getUsername()).isPresent()){
            throw new IllegalArgumentException("Already Exists");
        }

        return accountRepository.save(account);      
    }   

    public Account login(String username, String password){

        Optional<Account> accountLogin = accountRepository.findByUsernameAndPassword(username, password);

        if(accountLogin.isEmpty()){
            throw new IllegalArgumentException("Invalid Username and Password");

        }
       return accountLogin.get();
        
    }

}
