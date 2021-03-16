package com.example.customersfarms.mail;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
class MailContentBuilder {
    private final TemplateEngine templateEngine;

    public String build( String message ) {
        Context context = new Context();
        context.setVariable( "message", message );
        return this.templateEngine.process( "mailTemplate", context );
    }
}
