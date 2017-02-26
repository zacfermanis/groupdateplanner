package com.groupdateplanner.planner.service;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.groupdateplanner.planner.domain.Event;
import com.groupdateplanner.planner.domain.User;

import io.github.jhipster.config.JHipsterProperties;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Service for sending e-mails.
 * <p>
 * We use the @Async annotation to send e-mails asynchronously.
 * </p>
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String EVENT = "event";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    @Autowired
    private AmazonSimpleEmailServiceClient amazonSimpleEmailServiceClient;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send e-mail[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Construct an object to contain the recipient address
        Destination destination = new Destination().withToAddresses(to);

        // Create the subject and body of the message
        Content contentSubject = new Content().withData(subject);
        Content textBody = new Content().withData(content);
        Body body = new Body().withHtml(textBody);


        // Create a message with the specified subject and body.
        Message message = new Message().withSubject(contentSubject).withBody(body);

        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest()
            .withSource("support@groupdateplanner.com")
            .withDestination(destination)
            .withMessage(message);

        try {
            log.info("Attempting to send an email through Amazon SES via AWS SDK.");
            Region region = Region.getRegion(Regions.US_EAST_1);
            amazonSimpleEmailServiceClient.setRegion(region);

            amazonSimpleEmailServiceClient.sendEmail(request);
            log.info("Email sent successfully to: " + to);
        } catch (Exception e) {
            log.error("Exception occurred when sending email to: " + to + ". Exception: " + e.getMessage());
        }

        // Prepare message using a Spring helper
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
//            message.setTo(to);
//            //message.setFrom(jHipsterProperties.getMail().getFrom());
//            message.setFrom("support@groupdateplanner.com");
//            message.setSubject(subject);
//            message.setText(content, isHtml);
//            javaMailSender.send(mimeMessage);
//            log.debug("Sent e-mail to User '{}'", to);
//        } catch (Exception e) {
//            log.warn("E-mail could not be sent to user '{}'", to, e);
//        }

    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("activationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendVotingEmail(User user, Event event) {
        log.debug("Sending voting e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(EVENT, event);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("votingEmail", context);
        String subject = messageSource.getMessage("email.voting.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendVotedEmail(User user, Event event) {
        log.debug("Sending voting e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(EVENT, event);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("votedEmail", context);
        String subject = messageSource.getMessage("email.voted.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("creationEmail", context);
        String subject = messageSource.getMessage("email.activation.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process("passwordResetEmail", context);
        String subject = messageSource.getMessage("email.reset.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendSocialRegistrationValidationEmail(User user, String provider) {
        log.debug("Sending social registration validation e-mail to '{}'", user.getEmail());
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable("provider", StringUtils.capitalize(provider));
        String content = templateEngine.process("socialRegistrationValidationEmail", context);
        String subject = messageSource.getMessage("email.social.registration.title", null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }
}
