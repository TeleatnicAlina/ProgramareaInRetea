import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
class Mailer{
    public static void send(String from,String password,String to,String sub,String msg){
        //Get properties object
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "587");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");//465
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        //get Session
        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException e) {throw new RuntimeException(e);}

    }
}

