package com.example.telegramagentapi.services;

import com.example.telegramagentapi.dtos.LoginPostDto;
import com.example.telegramagentapi.dtos.RegisterPostDto;
import com.example.telegramagentapi.dtos.RegisterResponseDto;
import com.example.telegramagentapi.dtos.UpdatePasswordDto;
import com.example.telegramagentapi.models.ConfirmationToken;
import com.example.telegramagentapi.models.User;
import com.example.telegramagentapi.repositories.ConfirmationTokenRepository;
import com.example.telegramagentapi.repositories.UserRepository;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    UserRepository userRepository;
    ConfirmationTokenRepository confirmationTokenRepository;
    EmailService emailService;

    public UserServiceImpl(UserRepository userRepository,
                           ConfirmationTokenRepository confirmationTokenRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public RegisterResponseDto register(RegisterPostDto userDTO) {
        Boolean isRegisteredToKeycloak = registerToKeyclaok(userDTO);
        if (isRegisteredToKeycloak) {
            User user = new User();
            user.setUserName(userDTO.getEmail());
            user.setEmail(userDTO.getEmail());
            user.setFullName(userDTO.getName() + " " + userDTO.getSurname());
            user.setPhoneNumber(userDTO.getPhone());
            user.setAgentName(user.getAgentName());
            user.setCompanyName(userDTO.getCompanyName());
            user.setVOEN(userDTO.getVOEN());
            userRepository.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            emailService.sendMail(user.getEmail(), "Complete Registration!",
                    "To confirm your account, please click here : " +
                            "http://localhost:9191/api/v1/users/confirm-account?token="
                            + confirmationToken.getConfirmationToken());
            confirmationTokenRepository.save(confirmationToken);

            return new RegisterResponseDto("Please confirm your email!");
        }
        return null;
    }

    @Override
    public AccessTokenResponse login(LoginPostDto sign) {
        Optional<AccessTokenResponse> tokenResponse = authorize(sign.getEmail(), sign.getPassword());
        if (tokenResponse.isEmpty())
            throw new BadRequestException("User not found");

        return tokenResponse.get();
    }

    private Optional<AccessTokenResponse> authorize(String email, String password) {
        try {
            Map<String, Object> clientCredentials = new HashMap<>();
            clientCredentials.put("secret", clientSecret);
            clientCredentials.put("grant_type", "password");
            Configuration configuration =
                    new Configuration(authServerUrl, realm, clientId, clientCredentials, null);
            AuthzClient authzClient = AuthzClient.create(configuration);
            return Optional.of(authzClient.obtainAccessToken(email, password));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public void confirmAccount(String tokenStr) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(tokenStr);
        if (token != null) {
            System.out.println(token.getUser().getUserName());
            User user = userRepository.findUserByUserName(token.getUser().getUserName());
            userRepository.save(user);
            emailService.sendMail(user.getEmail(), "Congrats", "You are registered successfully!");
        }
    }

    public boolean registerToKeyclaok(RegisterPostDto userDTO) {
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("telegram-admin")
                .username("mubu").password("mubu1905")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        userDTO.setUsername(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getName());
        user.setLastName(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response response = usersResource.create(user);
        keycloak.tokenManager().getAccessToken();
        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(userDTO.getPassword());
            UserResource userResource = usersResource.get(userId);
            userResource.resetPassword(passwordCred);
        }
        return response.getStatus() == 201;
    }

    @Override
    public void updatePassword(UpdatePasswordDto passwordDto) {
        Optional<AccessTokenResponse> authorize = authorize(passwordDto.getEmail(), passwordDto.getOldPassword());
        if (authorize.isEmpty()) {
            throw new BadRequestException("Bele user yoxdu");
        }
        UserResource userResource = find(passwordDto.getEmail());
        UserRepresentation userRepresentation = userResource.toRepresentation();
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(passwordDto.getNewPassword());
        userRepresentation.setCredentials(Arrays.asList(passwordCred));
        userResource.update(userRepresentation);
    }

    private UserResource find(String email) {
        System.out.println(email + "asd");
        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("telegram-admin")
                .username("mubu").password("mubu1905")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
        RealmResource realmResource = keycloak.realm(realm);
        Optional<UserRepresentation> opUserRes = realmResource.users().search(email).stream().findAny();
        if (opUserRes.isEmpty())
            throw new BadRequestException("User not found");
        return realmResource.users().get(opUserRes.get().getId());
    }

//    public void resetPassword(String email) {
//        UserResource userResource = find(email);
//        UserRepresentation userRepresentation = userResource.toRepresentation();
//        User user = userRepository.findUserByUserName(email);
//        if (user != null) {
//            ConfirmationToken confirmationToken = new ConfirmationToken(user);
//            emailService.sendMail(email, "Complete Registration!",
//                    "To confirm your account, please click here : " +
//                            "http://localhost:9191/api/v1/users/reset-password?token="
//                            + confirmationToken.getConfirmationToken());
//        }
//
//
//    }
}
