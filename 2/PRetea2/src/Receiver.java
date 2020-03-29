import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;



public class Receiver {
    private static  Properties getServerProperties(String protocol, String host, String port) {

        Properties prop = new Properties();
        // server setting

        prop.put(String.format("mail.%s.host", protocol), host);
        prop.put(String.format("mail.%s.port", protocol), port);

        // SSL setting
        prop.setProperty(String.format("mail.%s.socketFactory.class", protocol), "javax.net.ssl.SSLSocketFactory");
        prop.setProperty(String.format("mail.%s.socketFactory.fallback", protocol), "false");
        prop.setProperty(String.format("mail.%s.socketFactory.port", protocol), port);



        return prop;

    }



    public  static void downloadEmails(String protocol, String host, String port, final String username, final String password) {

        Properties prop = getServerProperties(protocol, host, port);

        Session session = Session.getInstance(prop);


        try {

            Store store = session.getStore(protocol);
            store.connect(username, password);

            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            Message[] messages = folderInbox.getMessages();
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:/UTM/samplefile.txt"));

            for (int i = 0; i < 20; i++) {

                Message msg = messages[i];

                Address[] fromAddress = msg.getFrom();

                String from = fromAddress[0].toString();

                String subject = msg.getSubject();

                String toList = parseAddresses(msg.getRecipients(Message.RecipientType.TO));

                String ccList = parseAddresses(msg.getRecipients(Message.RecipientType.CC));

                String sentDate = msg.getSentDate().toString();

                String messageContent = "";



                Object content = msg.getContent();

                if (content instanceof MimeMultipart) {

                    MimeMultipart multipart = (MimeMultipart) content;

                    if (multipart.getCount() > 0) {

                        BodyPart part = multipart.getBodyPart(0);

                        content = part.getContent();

                        messageContent = content.toString();

                    }

                }

                if (content != null) {

                    messageContent = content.toString();

                }

                writer.write("*********************************************Message " + (i + 1) + ":**********************************************8");
                writer.write("\n --------------From: " + from);
                writer.write("\n --------------To: " + toList);
                writer.write("\n --------------CC: " + ccList);
                writer.write("\n --------------Subject: " + subject);
                writer.write("\n --------------Sent Date: " + sentDate);
                writer.write("\n -------------Message: " + messageContent);

            }

writer.close();

       
            folderInbox.close(false);

            store.close();

        } catch (NoSuchProviderException ex) {

            System.out.println("No provider for protocol: " + protocol);

            ex.printStackTrace();

        } catch (MessagingException ex) {

            System.out.println("Could not connect to the message store");

            ex.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }



    //Returns a list of addresses in String format separated by comma

    private static String parseAddresses(Address[] address) {

        StringBuilder listAddress = new StringBuilder();

        if (address != null) {

            for (Address value : address) {

                listAddress.append(value.toString()).append(", ");
            }
        }

        if (listAddress.length() > 1) {

            listAddress = new StringBuilder(listAddress.substring(0, listAddress.length() - 2));
        }
        return listAddress.toString();
    }

}

