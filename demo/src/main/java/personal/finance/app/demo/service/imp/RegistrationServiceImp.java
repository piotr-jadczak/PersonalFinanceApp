package personal.finance.app.demo.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.user.Role;
import personal.finance.app.demo.domain.entity.user.User;
import personal.finance.app.demo.domain.entity.user.VerificationToken;
import personal.finance.app.demo.repository.user.RoleRepository;
import personal.finance.app.demo.repository.user.UserRepository;
import personal.finance.app.demo.repository.user.VerificationTokenRepository;
import personal.finance.app.demo.service.contract.EmailService;
import personal.finance.app.demo.service.contract.RegistrationService;
import personal.finance.app.demo.service.exception.RoleNotFoundException;
import personal.finance.app.demo.service.exception.TokenNotActiveException;
import personal.finance.app.demo.service.exception.TokenNotFoundException;
import personal.finance.app.demo.service.exception.UserNotFoundException;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class RegistrationServiceImp implements RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public RegistrationServiceImp(UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  RoleRepository roleRepository,
                                  VerificationTokenRepository verificationTokenRepository,
                                  EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public User mapUser(UserDto userDto) {
        return User.builder().username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail()).build();
    }

    @Override
    public User registerUser(User userToRegister) throws MessagingException {
        User registeredUser = addUser(userToRegister);
        VerificationToken token = generateVerificationToken(registeredUser.getId());
        sendVerificationTokenOnEmail(token.getToken(), registeredUser.getEmail());
        return registeredUser;
    }

    @Override
    public void activateAccount(String tokenLink) {
        VerificationToken token = findVerificationToken(tokenLink);
        enableUserLogin(token);
        deleteToken(token);
    }

    public User addUser(User userToAdd) {

        userToAdd.setEnabled(false);
        userToAdd.setAccountNonLocked(true);
        userToAdd.setPassword(passwordEncoder
                .encode(userToAdd.getPassword()));
        Role userRole = roleRepository.findByName("user")
                .orElseThrow(RoleNotFoundException::new);
        userToAdd.setRole(userRole);

        return userRepository.save(userToAdd);
    }

    private VerificationToken generateVerificationToken(long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        return verificationTokenRepository.save(verificationToken);
    }

    private void sendVerificationTokenOnEmail(String token, String recipientEmail)
            throws MessagingException {

        String emailSubject = "Confirm your registration";
        String domain = "http://localhost:8080/confirm-registration/";
        String link = domain + token;
        String text = "Confirm your registration on SimpleFinanceApp" +
                " by clicking the link below: </br>" +
                "<a href=" + link + "> Activate account </a>" ;

        emailService.sendMail(recipientEmail, emailSubject ,text,true);
    }

    private VerificationToken findVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token)
                .orElseThrow(TokenNotFoundException::new);
    }

    private void deleteToken(VerificationToken token) {
        verificationTokenRepository.delete(token);
    }

    private void enableUserLogin(VerificationToken token) {
        if(token.getUser() == null) {
            throw new UserNotFoundException();
        }
        if(token.getExpiryDate().isBefore(LocalDate.now())) {
            throw new TokenNotActiveException();
        }
        token.getUser().setEnabled(true);
    }

}
