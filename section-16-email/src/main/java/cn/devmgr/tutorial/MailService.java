package cn.devmgr.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") String sender;

    /**
     * 发送一个纯文本的最简单的邮件
     * @param to
     * @param subject
     * @param body
     */
    public void sendEmail(String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    /**
     * 发送HTML格式的邮件
     * @param to
     * @param subject
     * @param body
     * @throws MessagingException
     */
    public void sendHtmlEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setContent(body, "text/html");
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(mimeMessage);
    }

    /**
     * 发送HTML格式，带有附件的邮件
     * @param to
     * @param subject
     * @param body
     * @param attachmentName
     * @param iss
     * @throws MessagingException
     */
    public void sendHtmlEmailWithAttachment(String to, String subject, String body, String attachmentName, InputStreamSource iss) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        // 可以增加多个附件，每个附件调用addAttachement
        helper.addAttachment(attachmentName, iss);

        mailSender.send(mimeMessage);

    }


    private String getMailBodyFromTemplate(String templateName, Map<String, Object> vars){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("email-templates/");     //模板所在目录，相对于当前classloader的classpath。
        resolver.setSuffix(".html");                //模板文件后缀
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        Context context = new Context();
        for(Iterator<String> iterator = vars.keySet().iterator(); iterator.hasNext();){
            String key = iterator.next();
            context.setVariable(key, vars.get(key));
        }

        StringWriter writer = new StringWriter();
        templateEngine.process("example", context, writer);
        return writer.toString();
    }
}
