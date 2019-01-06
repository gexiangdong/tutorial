package cn.devmgr.tutorial;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TutorialApplicationTests {

	@Autowired MailService mailService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void sendMailTest() throws Exception{
		String body = "hi sir\r\n\r\nkonichiha. \r\n\r\n best wishes.\r\n\r\nsincerely yours,\r\n me";
		mailService.sendEmail("someone@somewhere.com", "邮件主题-Text", body);

		String htmlBody = "hi sir,<br><p>hello.</p><br><br>best wishes.<br>sincerely yours, <br>me";
        mailService.sendHtmlEmail("someone@somewhere.com", "邮件主题-HTML", htmlBody);

		mailService.sendHtmlEmailWithAttachment("someone@somewhere.com", "邮件主题-附件", htmlBody, "附件.yml", new ClassPathResource("/application.yml"));
	}
}
