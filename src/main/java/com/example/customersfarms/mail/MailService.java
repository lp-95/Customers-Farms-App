package com.example.customersfarms.mail;

import com.example.customersfarms.model.EmailNotification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void send( EmailNotification emailNotification ) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper( mimeMessage );
            messageHelper.setFrom( "testaddress@email.com" );
            messageHelper.setTo( emailNotification.getEmail() );
            messageHelper.setSubject( emailNotification.getSubject() );
            messageHelper.setText( this.mailContentBuilder.build( emailNotification.getText() ) );
        };
        try {
            this.javaMailSender.send( messagePreparator );
            log.info( "E-mail sent successful" );
        } catch ( MailException ex ) {
            log.info( "E-mail sent wrong" );
            throw new RuntimeException( "E-mail sent wrong" );
        }
    }
}
