package com.bupt.covid.service.impl;

import com.bupt.covid.utils.EmailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class TaskService {
    @Async
    public void sendEmailVerifyCode(String verifyCode,String emailAddress) throws MessagingException {
        EmailSender.sendRegisterVerifyCode(verifyCode + "", emailAddress);

    }
}
