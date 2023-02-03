package com.example.jobbox.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.jobbox.apple.AppleIDTokenPayload;
import com.example.jobbox.apple.AppleUtil;
import com.example.jobbox.apple.TokenResponse;
import com.example.jobbox.config.exceptions.ExceptionGraphql;
import com.example.jobbox.config.exceptions.GraphqlBadCredentialsException;
import com.example.jobbox.inputModels.*;
import com.example.jobbox.model.*;
import com.example.jobbox.model.enums.ESocialType;
import com.example.jobbox.resultModels.ResetPasswordRequestResult;
import com.example.jobbox.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MutationResolver implements GraphQLMutationResolver {

    private final UserService userService;
    private final ProfileService profileService;
    private final EventService eventService;

    private final TemplateService templateService;
    private final AuthenticationProvider authenticationProvider;
    private  final FileService fileService;
    private final ServService servService;
    private final CategoryService categoryService;
    private final BannerService bannerService;

    private final JobPostService jobPostService;
//    private final AppleUtil appleUtil;

//    @Value("${apple.auth.token.url}")
//    private String APPLE_AUTH_URL;
//    @Value("${apple.aud}")
//    private String CLIENT_ID;



    public Optional<User> registration(CreateUserInput input) {
        Profile profile = new Profile();
        userService.createUser(input, profile);
        return login(input.getEmail(), input.getPassword());
    }

    public Optional<User> registrationAdmin(CreateUserInput input) {
        Profile profile = new Profile();
        userService.createAdmin(input, profile);
        return login(input.getEmail(), input.getPassword());
    }

    @PreAuthorize("isAnonymous()")
    public Optional<User> login(String email, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));

            return userService.getCurrentUser();
        } catch (AuthenticationException ex)
        {
            if (userService.getUserByEmail(email) != null) {
                User user = userService.getUserByEmail(email);

                if (user.getSocialType() != null) {
                    throw new GraphqlBadCredentialsException("User has social type");
                }
            }
            throw new GraphqlBadCredentialsException(ex.getMessage());
        }
    }
    @PreAuthorize("isAnonymous()")
    public Optional<User> socialLogin(CreateUserInput input) {

//        try {

            if (userService.existsUserByEmail(input.getEmail())) {
                return login(input.getEmail(), input.getPassword());// password change ???
            } else {
                return registration(input);
            }

//            Profile profile = new Profile();
//            RestTemplate restTemplate = new RestTemplate();
//            ObjectMapper objectMapper = new ObjectMapper();
//            if (ESocialType.FACEBOOK.equals(input.getSocialType())) {
//                //"https://graph.facebook.com/USER-ID?fields=id,name,email,picture&access_token=ACCESS-TOKEN"
//                String requestUrl = UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/" + input.getPassword() + "?fields=name,email")
//                        .queryParam("access_token", input.getSocialToken()).toUriString();
//                String resultJson = restTemplate.getForObject(requestUrl, String.class);
//                if (resultJson != null) {
//
//                    FacebookLoginDto userInfo = objectMapper.readValue(resultJson, new TypeReference<>() {
//                    });
//
////                    if (userService.existsUserByEmail(userInfo.getEmail())) {
////
////                        return login(userInfo.getEmail(), input.getPassword());
////                    }
//                    profile.setName(userInfo.getName());
////                    input.setEmail(userInfo.getEmail());
////                    input.setPassword(userInfo.getEmail());// ???
//                    userService.createUser(input, profile);
//                }
//            } else if (ESocialType.GOOGLE.equals(input.getSocialType())) {
//
//                //https://www.googleapis.com/oauth2/v1/userinfo?alt=json&access_token=youraccess_token
//                String requestUrl = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/oauth2/v1/userinfo")
//                        .queryParam("access_token", input.getSocialToken()).toUriString();
//                String resultJson = restTemplate.getForObject(requestUrl, String.class);
//                if (resultJson != null) {
//
//                    GoogleLoginDto userInfo = objectMapper.readValue(resultJson, new TypeReference<>() {});
//
////                    if (userService.existsUserByEmail(userInfo.getEmail())) {
////
////                        return login(userInfo.getEmail(), input.getPassword());
////                    }
//                    profile.setName(userInfo.getName());
////                    input.setEmail(userInfo.getEmail());
////                    input.setPassword(userInfo.getEmail()); //???
//                    userService.createUser(input, profile);
//                }
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return login(input.getEmail(), input.getPassword());
    }

    @PreAuthorize("isAnonymous")
    public Optional<User> appleLogin(String authorizationCode) {

        AppleIDTokenPayload idTokenPayload = null;

//        try {
//            RestTemplate restTemplate = new RestTemplate();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED));
//            headers.set("User-Agent", "Mozilla/5.0 Firefox/26.0");
//
//            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//            map.add("client_id", CLIENT_ID);
//
//            String token = appleUtil.generateJWT(CLIENT_ID);
//
//            map.add("client_secret", token);
//            map.add("grant_type", "authorization_code");
//            map.add("code", authorizationCode);  // JWT code we got from iOS
//            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//            String response = restTemplate.postForObject(APPLE_AUTH_URL, request, String.class);
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            TokenResponse tokenResponse = objectMapper.readValue(response, TokenResponse.class);
//
//            String idToken = tokenResponse.getId_token();
//            String payload = idToken.split("\\.")[1];// 0 is header we ignore it for now
//            String decoded = new String(Base64.getDecoder().decode(payload));
//            idTokenPayload = new Gson().fromJson(decoded, AppleIDTokenPayload.class);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (userService.existsUserByEmail(idTokenPayload.getEmail())) {
//            return login(idTokenPayload.getEmail(), idTokenPayload.getSub());
//        }
//
//        Profile profile = new Profile();
//        CreateUserInput input = new CreateUserInput();
//        input.setEmail(idTokenPayload.getEmail());
//        input.setPassword(idTokenPayload.getSub());
//        input.setSocialType(ESocialType.APPLE);
//        input.setSocialToken(idTokenPayload.getSub());
//
//        userService.createUser(input, profile);

        return login(idTokenPayload.getEmail(), idTokenPayload.getSub());

    }

    @PreAuthorize("isAuthenticated()")
    public User updatePassword(UpdatePasswordInput input) {
        return userService.updatePassword(input);
    }

    @PreAuthorize("isAnonymous()")
    public ResetPasswordRequestResult resetPasswordRequest(String email) {
        return userService.resetPasswordRequest(email);
    }
    @PreAuthorize("isAnonymous()")
    public String validateVerificationCode(ValidationCodeInput input) {
        return userService.validateVerificationCode(input);
    }
    @PreAuthorize("isAnonymous()")
    public String resetPassword(String token, String newPassword) {
        return userService.resetPassword(token, newPassword);
    }

    @PreAuthorize("isAuthenticated()")
    public User updateProfile(CreateProfileInput input) {
        User user = userService.getCurrentUser().get();
        user.setOn_boarding_status(1);
        userService.updateUser(user);
        profileService.update(user, input);
        return userService.getCurrentUser().get();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteUser(Long id) {
        userService.removeById(id);
        return "Deleted user by id = " + id;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deactivateUser(Long id) {
        userService.deactivateUser(id);
        return "user deactivated";
    }

    @PreAuthorize("isAuthenticated()")
    public Boolean deleteCurrentUser(String password) {
//        User user = userService.getCurrentUser().orElseThrow(() -> new EntityNotFoundException());

//        if (userService.validatePassword(user.getPassword(), password)) {
//            userService.removeById(user.getId());
//            return true;
//        } else {
//            throw new ExceptionGraphql("Password don't match");
//        }
        User user = userService.getUserByEmail(password);
        if (user != null) {
            userService.removeById(user.getId());
            return true;
        } else {
            throw new ExceptionGraphql("Email don't match");
        }
    }

    @PreAuthorize("isAuthenticated()")
    public Event createEvent(Long templateId, CreateEventInput eventInput) {
        return eventService.createEvent(templateId, eventInput);
    }

    @PreAuthorize("isAuthenticated()")
    public String createDailyEvent(Long templateId, CreateEventInput eventInput) {
        return eventService.createDailyEvent(templateId, eventInput);
    }

    @PreAuthorize("isAuthenticated()")
    public String createWeeklyEvent(Long templateId, CreateEventInput eventInput, List<Integer> dates) {
        return eventService.createWeeklyEvent(templateId, eventInput, dates);
    }

    @PreAuthorize("isAuthenticated()")
    public Event updateEvent(Long id, CreateEventInput eventInput) {
        return eventService.updateEvent(id, eventInput);
    }

    @PreAuthorize("isAuthenticated()")
    public Long deleteEvent(Long id) {
        eventService.deleteById(id);
        return id;
    }

    @PreAuthorize("isAuthenticated()")
    public Template createTemplate(TemplateInput templateInput) {
        return templateService.createTemplate(templateInput);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Template createDefaultTemplate(TemplateInput templateInput) {
        return templateService.createDefaultTemplate(templateInput);
    }

    @PreAuthorize("isAuthenticated()")
    public Template createTemplateFromDefault(Long id) {
        return templateService.createTemplateFromDefault(id);
    }

    @PreAuthorize("isAuthenticated()")
    public Template updateTemplate(Long id, TemplateInput templateInput) {
        return templateService.updateTemplate(id, templateInput);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Template updateDefaultTemplate(Long id, TemplateInput input) {
        return templateService.updateDefaultTemplate(id, input);
    }

    @PreAuthorize("isAuthenticated()")
    public Long deleteTemplate(Long id) {
        templateService.deleteTemplate(id);
        return id;
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Category createCategory(CreateCategoryInput input) {
        File fileImage = fileService.getById(input.getImage_id());
        return categoryService.createCategory(input, fileImage);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Category updateCategory(UpdateCategoryInput input) {
        File fileImage = fileService.getById(input.getImage_id());
        return categoryService.updateCategory(input, fileImage);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteCategory(Long id) {
        categoryService.deleteById(id);
        return "Deleted category by id = " + id;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Service createService(CreateServiceInput input) {
        File fileImage = fileService.getById(input.getImage_id());
        return servService.createService(input, fileImage);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Service updateService(UpdateServiceInput input) {
        File fileImage = fileService.getById(input.getImage_id());
        return servService.updateService(input, fileImage);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteService(Long id) {
        servService.deleteById(id);
        return "Deleted service by id = " + id;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Banner createBanner(CreateBannerInput input) {
        File fileImage = fileService.getById(input.getImage_id());
        return bannerService.createBanner(input, fileImage);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Banner updateBanner(UpdateBannerInput input) {
        File fileImage = fileService.getById(input.getImage_id());
        return bannerService.updateBanner(input, fileImage);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteBanner(Long id) {
        bannerService.deleteById(id);
        return "Deleted banner by id = " + id;
    }


    //    Work with files
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public File addFile(List<Part> parts, DataFetchingEnvironment environment) throws IOException {

        List<Part> attachmentParts = environment.getArgument("files");
        File file = null;

        for (Part part : attachmentParts) {
            MultipartFile mfile = new MockMultipartFile(part.getSubmittedFileName(), part.getSubmittedFileName(), part.getContentType(), part.getInputStream());

            file = fileService.upload(mfile);
        }
        return file;
    }
    public String deleteFile(Long id) {
        fileService.delete(id);
        return "Deleted file by id = " + id;
    }

    public JobPost addJobPost(CreateJobPostInput createJobPostInput){
        return jobPostService.createJobPost(createJobPostInput);
    }

    public JobPost updateJobPost(UpdateJobPostInput updateJobPostInput){
        return jobPostService.updateJobPost(updateJobPostInput);
    }
}
