package sendemail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class EnvoyerEmail {

    public static Boolean Envoyer(String to,String subject,String text)
    {

        final String username = "EkriliAgency@gmail.com";
        final String password = "aymenSami";
        //dateNaissance 01/01/1990

        //Get the session object
        Properties props = System.getProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        //compose the message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");

        }
        catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
        return true;
    }
}

