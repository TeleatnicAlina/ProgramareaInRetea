public class SendMailSSL {
    public static Receiver emailReceiver = new Receiver();
    public static void main(String[] args) {
        //from,password,to,subject,message
        String username = "teleatnicalina@gmail.com";
        String password = "apollo11kimnayoung+--@";
        String receiver = "teleatnicalina@gmail.com";
        String subject = "hello javatpoint";
        String message = "How r u?";
        //Mailer.send(username, password, receiver, subject, message);
        //change from, password and to

        String protocol = "imap";
        String host = "imap.gmail.com";

        String port = "993";
        Mailer.send(username, password, receiver, subject, message);
        Receiver.downloadEmails(protocol, host, port, username, password);

    }
}