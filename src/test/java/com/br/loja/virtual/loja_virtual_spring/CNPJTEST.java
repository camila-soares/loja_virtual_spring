//package com.br.loja.virtual.loja_virtual_spring;
//
//
//import com.sun.mail.smtp.SMTPSSLTransport;
//import com.sun.mail.smtp.SMTPTransport;
//import com.sun.mail.smtp.SMTPTransport.*;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.io.UnsupportedEncodingException;
//import java.util.Properties;
//
//public class CNPJTEST {
//
//    public static final String USER_NAME = "camila_soares_2hotmail.com";
//    public static final String PASSWORD = "Mila@2022";
//
//    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {
//            Properties props = new Properties();
//            /** Parâmetros de conexão com servidor Gmail */
////        props.put("mail.smtp.user", USER_NAME);
////        props.put("mail.smtp.password", PASSWORD);
//        //props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.host", "outlook.office365.com");
//        props.put("mail.smtp.port", "587");
//        //props.put("mail.smtp.socketFactory.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        //props.put("mail.smtps.starttls.enable", "true");
//       // props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        //props.put("mail.smtp.socketFactory.fallback", "false");
//
//        Session session = Session.getInstance(props);
//        String mail = "camilasoares1507@gmail.com";
//        Address[] toUser = InternetAddress.parse(mail);
//MimeMessage message = new MimeMessage(session);
//
//message.setFrom(new InternetAddress(USER_NAME, "TESSSS", "UTF-8"));
//message.setRecipients(Message.RecipientType.TO, toUser);
//message.setSubject( "Hello World!");
//message.setContent("Hello World!", "text/html;charset=utf-8");
//
//Transport transport = session.getTransport("smtp");
//transport.connect(USER_NAME, PASSWORD);
////SMTPTransport smtpTransport = (SMTPTransport) transport;
////smtpTransport
/////smtpTransport.connect("outlook.office365.com", USER_NAME, PASSWORD);;
//Transport.send(message, message.getAllRecipients());
//
//
//
//
//
//
//
//        };
//
//
//
//    }
//
////        boolean isCPF = ValidateCPF.isCPF("081.466.394-02");
////        boolean isCnpj = ValidateCNPJ.isCNPJ("87.375.346/0001-52");
////
////        System.out.println("CPF Valido : " + isCPF);
////        System.out.println("CNPJ Valido : " + isCnpj);
//  //  }
////}
