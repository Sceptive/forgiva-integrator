package com.sceptive.forgiva.integrator.core;

import com.sceptive.forgiva.integrator.exceptions.NotInitializedException;
import com.sceptive.forgiva.integrator.logging.Warning;
import org.simplejavamail.MailException;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.EmailPopulatingBuilder;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.internal.MailerRegularBuilderImpl;

public class Mailer {

    /* ***************************************************************************

    STATIC METHODS

    *************************************************************************** */
    private static Mailer instance;

    /*
        Returns instance if not initialized statically
     */
    public static Mailer get_instance() {
        if (instance == null) {
            try {
                instance = new Mailer();
            } catch (NotInitializedException ex) {
                return null;
            }
        }

        return instance;
    }

    /*
       Constructs mailer object
    */
    public Mailer() throws NotInitializedException {
        if (!initialize()) {
            throw new NotInitializedException();
        }
    }

    private boolean initialize() {
        return true;
    }

    public void send_email(String _to_name,
                           String _to_email,
                           String _subject,
                           String _plain_text_content,
                           String _html_content) {

        EmailPopulatingBuilder epb = EmailBuilder.startingBlank()
                                                 .from(Configuration.runtime.smtp_server_from)
                                                 .to(_to_name, _to_email)
                                                 .withSubject(_subject);
        if (_plain_text_content != null) {
            epb = epb.withPlainText(_plain_text_content);
        }

        if (_html_content != null) {
            epb = epb.withHTMLText(_html_content);

        }
        Email email = epb.buildEmail();

        MailerRegularBuilderImpl mb = MailerBuilder
                .withSMTPServer(Configuration.runtime.smtp_server_host,
                                Configuration.runtime.smtp_server_port,
                                Configuration.runtime.smtp_server_username,
                                Configuration.runtime.smtp_server_password)
                .withTransportStrategy(
                        Configuration.runtime.smtp_server_header.equalsIgnoreCase(Constants.SMTP_SERVER_TYPE_PLAIN) ?
                        TransportStrategy.SMTP :
                        Configuration.runtime.smtp_server_header.equalsIgnoreCase(Constants.SMTP_SERVER_TYPE_SECURE) ?
                        TransportStrategy.SMTPS : TransportStrategy.SMTP_TLS);
        if (Configuration.runtime.smtp_server_proxy_host != null) {
            mb = mb.withProxy(Configuration.runtime.smtp_server_proxy_host,
                              Configuration.runtime.smtp_server_proxy_port,
                              Configuration.runtime.smtp_server_proxy_username,
                              Configuration.runtime.smtp_server_proxy_password);
        }


        try {
            mb.buildMailer().sendMail(email);
        } catch (MailException e) {
            // handle the exception
            Warning.get_instance().print(e);
        }



    }

}
