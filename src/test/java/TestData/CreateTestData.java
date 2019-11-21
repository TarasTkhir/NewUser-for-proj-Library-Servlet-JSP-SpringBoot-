package TestData;

import com.taras.user.demo.domain.ConfirmationToken;
import com.taras.user.demo.domain.User;
import com.taras.user.demo.repository.ConfirmactionTokenRepository;
import com.taras.user.demo.repository.UserRepository;

import java.time.LocalDate;

public class CreateTestData {

    public static void generateTestUsers(UserRepository userRepository){

        userRepository.save(new User(1,"Vasua","Pupkin",false, LocalDate.parse("1212-12-12"),"root","root","root@gmail.com", User.Role.USER));
        userRepository.save(new User(2,"Taras","Tkhir",true, LocalDate.parse("1212-12-12"),"Taras","Taras","Taras@gmail.com", User.Role.USER));
        userRepository.save(new User(3,"Test","Test",false, LocalDate.parse("1212-12-12"),"Test","Test","Test@gmail.com", User.Role.USER));
        System.out.println(userRepository.findAll());

    }

    public static void generateTestToken(ConfirmactionTokenRepository confirmactionTokenRepository, UserRepository userRepository){
        ConfirmationToken confirmationToken = new ConfirmationToken(userRepository.findAll().get(0));
        confirmationToken.setConfirmationToken("f7c7081a-b38e-11e9-a2a3-2a2ae2dbcce4");
        confirmactionTokenRepository.save(confirmationToken);

    }
}
