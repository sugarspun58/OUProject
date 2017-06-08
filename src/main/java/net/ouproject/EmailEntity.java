/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author tonyxp
 */
public class EmailEntity {
    
    private static final String JNDI_LOOKUP = "mail/__4spadesnet";
    
    
    
    private String subject;
    private String body;
    private String recipient;
    
    
    private Session emailSession;
    
    //constructor
    
    public EmailEntity() {
        
    }
    
    
    
    public String sendMail() {
        
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            emailSession = (Session) initialContext.lookup(JNDI_LOOKUP);
            
            Message msg = new MimeMessage(emailSession);
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            msg.setSubject(subject);
            msg.setText(body);
            
            Transport.send(msg);
        }
        catch (AddressException e) {
            e.printStackTrace();
            return "failure";
        }
        catch (MessagingException e) {
            e.printStackTrace();
            return "failure";
        }
        catch (NamingException e) {
            e.printStackTrace();
            return "failure";
        }
        
        return "success";
        
    }
    
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    
    public String getSubject() {
        return body;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
}
