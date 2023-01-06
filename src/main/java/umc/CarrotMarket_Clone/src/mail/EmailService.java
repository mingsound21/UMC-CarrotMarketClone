package umc.CarrotMarket_Clone.src.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmailService {
    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;
    // 타임리프를사용하기 위한 객체를 의존성 주입으로 가져온다
    private final SpringTemplateEngine templateEngine;
    private String authNum; //랜덤 인증 코드

    // 8자리 랜덤 인증 코드 생성(소문자, 대문자, 숫자 사용)
    public void createRandomCode(){
        Random random = new Random();
        StringBuffer key = new StringBuffer(); // StringBuffer : 문자열 추가 변경할 때 사용하는 자료형

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3); // random.nextInt(int n) : 0~n 미만의 정수형 난수 반환

            switch (index) {
                case 0 :
                    // key.append() : 문자열 이어 붙이기
                    key.append((char) ((int)random.nextInt(26) + 97)); // 소문자
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65)); // 대문자
                    break;
                case 2:
                    key.append(random.nextInt(9)); // 숫자
                    break;
            }
        }
        authNum = key.toString();
    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createRandomCode(); //인증 코드 생성
        String setFrom = "devmingsound@naver.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String toEmail = email; //받는 사람
        String title = "mingsound.shop 회원가입 인증 번호"; //제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(setFrom); //보내는 이메일
        message.setText(setContext(authNum), "utf-8", "html");

        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        emailSender.send(emailForm);

        return authNum; //인증 코드 반환
    }

    //타임리프를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context(); // Context : thymeleaf꺼로 import
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }
}
