package br.com.sigo.indtexbr.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AwsMailService {

	static final String FROMNAME = "SIGO - IndTexBR";
	static final int PORT = 587;

	@Value("${aws.mail.host}")
	private String host;

	@Value("${aws.mail.username}")
	private String username;
	
	@Value("${aws.mail.password}")
	private String password;

	@Value("${aws.mail.from}")
	private String from;

	public void sendMail(String to, String subject, String texto) throws Exception {
		String body = String.join(System.getProperty("line.separator"), "<h1>Notificação de alteração de normas</h1>",
				"<p>" + texto + "</a>.");

		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from, FROMNAME));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(subject);
		msg.setContent(body, "text/html");

		Transport transport = session.getTransport();

		try {
			log.debug("Sending...");

			transport.connect(host, username, password);

			transport.sendMessage(msg, msg.getAllRecipients());
			log.debug("Email sent!");
		} catch (Exception ex) {
			log.error("The email was not sent.");
			log.error("Error message: {}", ex.getMessage());
			throw ex;
		} finally {
			transport.close();
		}
	}

}
