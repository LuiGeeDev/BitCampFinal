package kr.or.bit.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import kr.or.bit.model.Member;

@Service
public class MailService {
  @Autowired
  private JavaMailSenderImpl mailSender;
  @Autowired
  private TemplateEngine emailTemplateEngine;

  public void sendNewTeacherEmail(String to, String name) throws MessagingException {
    Context context = new Context();
    context.setVariable("name", name);

    String from = "mail@bitcamp.com";
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject("[티밍] 강사 계정 생성 안내");
    String htmlContent = emailTemplateEngine.process("mail", context);
    helper.setText(htmlContent, true);

    mailSender.send(message);
  }
  
  public void sendNewPasswordEmail(int newPassword, Member member) throws MessagingException {
    Context context = new Context();
    context.setVariable("member", member);
    
    String from = "mail@bitcamp.com";
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
    helper.setFrom(from);
    helper.setTo(member.getEmail());
    helper.setSubject("[티밍] 임시 비밀번호 발급");
    String htmlContent = emailTemplateEngine.process("passwordMail", context);
    helper.setText(htmlContent, true);

    mailSender.send(message);
  }
}
