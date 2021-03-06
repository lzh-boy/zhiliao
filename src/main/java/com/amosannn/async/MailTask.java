package com.amosannn.async;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

@Async("taskExecutor")
public class MailTask implements Runnable{

  private String code;
  private String email;
  private JavaMailSender javaMailSender;
  private int operation;

  public MailTask(String code, String email,
      JavaMailSender javaMailSender, int operation) {
    this.code = code;
    this.email = email;
    this.javaMailSender = javaMailSender;
    this.operation = operation;
  }

//  @Value("${spring.mail.username}")
//  private String emailFrom;
//
//  public String getEmailFrom() {
//    return emailFrom;
//  }
//
//  public void setEmailFrom(String emailFrom) {
//    this.emailFrom = emailFrom;
//  }

//  public void sendSimpleMail(String sendTo, String titel, String content) {
//    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//    try {
//      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//      helper.setFrom(emailFrom);
//      helper.setTo(sendTo);
//      helper.setSubject(titel);
//      helper.setText(content, true);
//
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//    javaMailSender.send(mimeMessage);
//  }


  @Override
  public void run() {
    javaMailSender.send(new MimeMessagePreparator() {
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        System.out.println("开始发邮件...");
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom("13250060902@163.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("一封激活邮件");
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head></head><body>");

        if (operation == 1) {
          sb.append("<a href=" + "http://127.0.0.1:8080/zhiliao/" + "activate?code=");
          sb.append(code);
          sb.append(">点击激活</a></body>");
        } else {
          sb.append("是否将您的密码修改为:");
          sb.append(code.substring(0, 8));
          sb.append("，<a href=" + "http://127.0.0.1:8080/zhiliao/" + "verify?code=" + code + ">");
          sb.append("点击是</a></body>");
        }

        mimeMessageHelper.setText(sb.toString(), true);

        System.out.println("结束发邮件...");
      }
    });
  }
}