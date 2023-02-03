package com.example.jobbox.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.jobbox.config.JWTUserDetails;
import com.example.jobbox.config.exceptions.BadTokenException;
import com.example.jobbox.config.exceptions.GraphqlBadCredentialsException;
import com.example.jobbox.config.exceptions.TimeExpiredException;
import com.example.jobbox.config.exceptions.UserAlreadyExistsException;
import com.example.jobbox.config.exceptions.UserNotFoundException;
import com.example.jobbox.config.exceptions.ValidateVerificationCodeException;
import com.example.jobbox.inputModels.CreateUserInput;
import com.example.jobbox.inputModels.UpdatePasswordInput;
import com.example.jobbox.inputModels.ValidationCodeInput;
import com.example.jobbox.model.Profile;
import com.example.jobbox.model.Role;
import com.example.jobbox.model.User;
import com.example.jobbox.model.enums.ERole;
import com.example.jobbox.model.enums.ESort;
import com.example.jobbox.repository.ProfileRepository;
import com.example.jobbox.repository.RoleRepository;
import com.example.jobbox.repository.TemplateRepository;
import com.example.jobbox.repository.UserRepository;
import com.example.jobbox.resultModels.ResetPasswordRequestResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

@Service
public class UserService implements UserDetailsService{
    private final UserRepository userRepository;
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ProfileRepository profileRepository;
//    private final JavaMailSender mailSender;

    private final TemplateRepository templateRepository;

    @Value("${jwt.issuer}")
    private String JWT_ISSUER;
//    @Value("${spring.mail.username}")
//    private String FROM_ADDRESS;

    @Autowired
    public UserService(UserRepository userRepository,RoleRepository roleRepository, Algorithm algorithm, JWTVerifier verifier, PasswordEncoder passwordEncoder, TemplateRepository templateRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.algorithm = algorithm;
        this.verifier = verifier;
        this.passwordEncoder = passwordEncoder;
//        this.mailSender = mailSender;
        this.profileRepository = profileRepository;
        //Шаблоны
        this.templateRepository = templateRepository;
    }


    @Transactional
    public User createUser(CreateUserInput input, Profile profile) {
        if (!existsUserByEmail(input.getEmail())) {
            final User user = new User();
            user.setEmail(input.getEmail());
            user.setPassword(passwordEncoder.encode(input.getPassword()));
            user.setSocialToken(input.getSocialToken());
            user.setSocialType(input.getSocialType());
            user.setActive(true);
            user.setOn_boarding_status(0);
            user.setCreated_at(LocalDateTime.now());

            Set<Role> roles = new HashSet<>();

            Role userRole = roleRepository.findByName(ERole.ROLE_USER);

            if (userRole == null) {
                userRole = new Role(ERole.ROLE_USER);
                userRole.setCreated_at(LocalDateTime.now());
                roleRepository.save(userRole);
                userRole = roleRepository.findByName(ERole.ROLE_USER);

            }
            roles.add(userRole);
            user.setRoles(roles);
            profile.setCreated_at(LocalDateTime.now());
            user.setProfile(profile);

            profile.setUser(user);

            //Не нужно
//            Set<Template> templatesSet = new HashSet<>();
//            templatesSet.addAll(templateRepository.getTemplateByIsDefault().stream().map(templateDefault -> templateRepository.saveAndFlush(TemplateUtil.toTemplate(templateDefault))).collect(Collectors.toList()));
//            user.setTemplates(templatesSet);
//            HashSet<User> users = new HashSet<>();
//            users.add(user);
//            templatesSet.stream().forEach(template -> template.setUsers(users));
            //Не нужно

            return userRepository.saveAndFlush(user);
        } else {
            throw new UserAlreadyExistsException(input.getEmail());
        }
    }

    @Transactional
    public User createAdmin(CreateUserInput input, Profile profile) {
        if (!existsUserByEmail(input.getEmail())) {
            final User user = new User();
            user.setEmail(input.getEmail());
            user.setPassword(passwordEncoder.encode(input.getPassword()));
            user.setActive(true);
            user.setOn_boarding_status(0);
            user.setCreated_at(LocalDateTime.now());

            Set<Role> roles = new HashSet<>();

            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
            Role userRole = roleRepository.findByName(ERole.ROLE_USER);

            if (adminRole == null) {
                adminRole = new Role(ERole.ROLE_ADMIN);
                adminRole.setCreated_at(LocalDateTime.now());
                roleRepository.save(adminRole);
                adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
            }
            if (userRole == null) {
                userRole = new Role(ERole.ROLE_USER);
                userRole.setCreated_at(LocalDateTime.now());
                roleRepository.save(userRole);
                userRole = roleRepository.findByName(ERole.ROLE_USER);
            }

            roles.add(adminRole);
            roles.add(userRole);
            user.setRoles(roles);
            profile.setCreated_at(LocalDateTime.now());
            user.setProfile(profile);

            profile.setUser(user);
            return userRepository.saveAndFlush(user);
        } else {
            throw new UserAlreadyExistsException(input.getEmail());
        }
    }

    @Transactional
    public User updateUser(User user) {
        user.setUpdated_at(LocalDateTime.now());
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public User updatePassword(UpdatePasswordInput input) {
        User user = getCurrentUser().orElseThrow(() -> new EntityNotFoundException());
        if (passwordEncoder.matches(input.getOriginalPassword(), user.getPassword())) {
            user.setUpdated_at(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(input.getNewPassword()));
        } else {
            throw new GraphqlBadCredentialsException(user.getEmail());
        }
        return user;
    }

    public ResetPasswordRequestResult resetPasswordRequest(String email) {

        if (!existsUserByEmail(email)) {
            throw new EntityNotFoundException();
        }
        Random random = new Random();
        Integer resetCode= 100000 + random.nextInt(999999 - 100000);
        String expireTime = String.valueOf(LocalDateTime.now().plusMinutes(10));

//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setFrom(FROM_ADDRESS);
//        message.setSubject("Код верификации");
//        message.setText("Здравствуйте.\n" +
//                "Поступил запрос на смену пароля от вашего аккаунта. Для завершения сброса пароля, введите код верификации.\n " +
//                "Если вы не запрашивали сброс пароля, то проигнорируйте это письмо.\n" +
//                "\n" +
//                "Код верификации: " + resetCode);
//
//        mailSender.send(message);

        String str = email + resetCode + expireTime;
        Integer hashCode = str.hashCode();

        return new ResetPasswordRequestResult(hashCode, expireTime);
    }

    public String validateVerificationCode(ValidationCodeInput input) {
        if (!existsUserByEmail(input.getEmail())) {
            throw new EntityNotFoundException();
        }
        LocalDateTime expTime = LocalDateTime.parse(input.getExpirationTime());
        if (LocalDateTime.now().isAfter(expTime)) {
            throw new TimeExpiredException();
        }

        String str = input.getEmail() + input.getVerificationCode() + input.getExpirationTime();
        Integer hashCode = str.hashCode();

        if (!hashCode.equals(input.getHashCode())) {
            throw new ValidateVerificationCodeException();
        }

        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(1, ChronoUnit.HOURS);

        return JWT
                .create()
                .withIssuer(JWT_ISSUER)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(input.getEmail())
                .withClaim("type", "password_reset")
                .sign(algorithm);
    }
    @Transactional
    public String resetPassword(String token, String newPassword) {
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        if (!decodedJWT.getSubject().isEmpty()) {
            User user = userRepository.findByEmail(decodedJWT.getSubject()).orElseThrow(() -> new EntityNotFoundException());

            user.setUpdated_at(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(newPassword));
            return "Password has been changed";
        } else {
            throw new BadTokenException("Token is invalid or expired");
        }
    }

    public Boolean validatePassword(String password, String anotherPassword) {
        return passwordEncoder.matches(anotherPassword, password);
    }

    @Transactional
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }

    public void deactivateUser(Long id) {
        User user = getUser(id);
        user.setActive(false);
        updateUser(user);
    }


    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> getAllUsersExcludeAdmin() {
        return userRepository.findAll();
    } //надо будет потом удалить

    public List<User> getAllByPaging(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)){
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<User> pagedResult = userRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public JWTUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(user -> getUserDetails(user, getToken(user)))
                .orElseThrow(() -> new UsernameNotFoundException("Username or password didnt match"));
    }

    public Optional<Role> getRole(Long id) {
        return roleRepository.findById(id);
    }

    public List<Role> getAllRoles(Integer pageNo, Integer pageSize, String sortBy, ESort sort) {
        Pageable paging;
        if (sort.equals(ESort.ASC)) {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        } else {
            paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        Page<Role> pagedResult = roleRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch(JWTVerificationException ex) {
            return Optional.empty();
        }
    }
    @Transactional
    public JWTUserDetails loadUserByToken(String token) {
        return getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(userRepository::findByEmail)
                .map(user -> getUserDetails(user, token))
                .orElseThrow(() -> new BadTokenException("Token is invalid or expired"));
    }

    @Transactional
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findByEmail)
                .orElse(null));
    }

    private JWTUserDetails getUserDetails(User user, String token) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return JWTUserDetails
                .builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .token(token)
                .build();
    }
    @Transactional
    public String getToken(User user) {
//        final Duration tokenExpiration = Duration.ofHours(4);
        final Period tokenExpiration = Period.ofDays(7);

        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(tokenExpiration);
        return JWT
                .create()
                .withIssuer(JWT_ISSUER)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(user.getEmail())
                .sign(algorithm);
    }

    public boolean isAdmin() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .stream()
                .flatMap(Collection::stream)
                .map(GrantedAuthority::getAuthority)
                .anyMatch(ERole.ROLE_ADMIN::equals);
    }

    public boolean isAuthenticated() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(not(this::isAnonymous))
                .isPresent();
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

    @Transactional
    public Set<User> getUsersByPhoneNumber(List<String> phoneNumbers){
        Set<User> invitedUsers = new HashSet<>();
        List<Profile> profiles = profileRepository.getProfilesByPhoneNumber(phoneNumbers);
        for(Profile p : profiles){
            invitedUsers.add(p.getUser());
        }
        return  invitedUsers;
    }
}