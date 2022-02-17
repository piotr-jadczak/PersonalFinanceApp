package personal.finance.app.demo.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import personal.finance.app.demo.domain.dto.UserDto;
import personal.finance.app.demo.domain.entity.Role;
import personal.finance.app.demo.domain.entity.User;
import personal.finance.app.demo.repository.RoleRepository;
import personal.finance.app.demo.repository.UserRepository;
import personal.finance.app.demo.service.contract.LoginService;
import personal.finance.app.demo.service.exception.RoleNotFoundException;

import javax.transaction.Transactional;

@Service
@Transactional
public class LoginServiceImp implements LoginService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginServiceImp(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public User registerUser(User userToRegister) {

        userToRegister.setEnabled(false);
        userToRegister.setAccountNonLocked(true);
        userToRegister.setPassword(passwordEncoder
                .encode(userToRegister.getPassword()));
        Role userRole = roleRepository.findByName("customer")
                .orElseThrow(RoleNotFoundException::new);
        userToRegister.setRole(userRole);

        return userRepository.save(userToRegister);
    }

    @Override
    public User mapUser(UserDto userDto) {
        return User.builder().username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail()).build();
    }

}
