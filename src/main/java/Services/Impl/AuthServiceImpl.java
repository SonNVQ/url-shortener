package Services.Impl;

import Constants.Regex;
import DAL.Impl.RoleDAOImpl;
import DAL.Impl.UserDAOImpl;
import DAL.RoleDAO;
import DAL.UserDAO;
import Models.GoogleUser;
import Models.Role;
import Models.User;
import Services.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenson
 */
@Default
public class AuthServiceImpl implements AuthService {

    @Inject
    private UserDAO userDAO;
    
    @Inject
    private RoleDAO roleDAO;
    
    private final BcryptFunction bcrypt;

    public AuthServiceImpl() {
        bcrypt = BcryptFunction.getInstance(Bcrypt.A, 10);
    }

    @Override
    public User login(HttpServletRequest request) throws IllegalArgumentException {
        //Get username and password from request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //Add back username to show in form if login failed
        request.setAttribute("username", username);

        //Validate username using regex
        if (!username.matches(Regex.USERNAME_CHECK)) {
            throw new IllegalArgumentException("USERNAME_FORMAT_INVALID");
        }

        //Validate password using regex
        if (!password.matches(Regex.PASSWORD_CHECK)) {
            throw new IllegalArgumentException("PASSWORD_FORMAT_INVALID");
        }

        //Get user with the corresponding username from database
        User user = userDAO.getUserByUsername(username);
        if (user == null) { //If user does not exist, return null to exit
            return null;
        }

        //Once the user exists, verify the encrypted password
        boolean verified = Password.check(password, user.getPassword()).with(bcrypt);
        if (!verified) { //If password does not match, return null to exit
            return null;
        }

        //Once the password match, get user role
        HashSet<Role> roles = getUserRoles(user);
        if (roles == null) { //If get role failed, means login failed, return null to exit
            return null;
        }

        //Get session
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);     //Add user to session
        session.setAttribute("roles", roles);   //Add role to session

        //Everything is done, return logged in user
        return user;
    }

    @Override
    public User register(User user) throws IllegalArgumentException {
        String isValidUser = validateUserInfo(user);
        if (!isValidUser.equals("OK")) {
            throw new IllegalArgumentException(isValidUser);
        }
        if (userDAO.isUsernameExisted(user.getUsername())) {
            throw new IllegalArgumentException("USERNAME_IS_EXISTED");
        }
        if (userDAO.isEmailExisted(user.getEmail()) || userDAO.isEmailExisted(user.getGoogleEmail())) {
            throw new IllegalArgumentException("EMAIL_IS_EXISTED");
        }
        String hashedPassword = Password.hash(user.getPassword()).with(bcrypt).getResult();
        user.setPassword(hashedPassword);
        User addedUser = userDAO.addUser(user);
        if (addedUser == null) {
            return null;
        }
        User addedUserWithRole = userDAO.addUserRole(addedUser, Role.USER);
        if (addedUserWithRole == null) {
            return null;
        }
        return addedUserWithRole;
    }

    private String validateUserInfo(User user) {
        if (user.getUsername() == null || !user.getUsername().matches(Regex.USERNAME_CHECK)) {
            return "USERNAME_FORMAT_INVALID";
        }
        if (!user.getPassword().matches(Regex.PASSWORD_CHECK)) {
            return "PASSWORD_FORMAT_INVALID";
        }
        if (!user.getEmail().matches(Regex.EMAIL_CHECK)) {
            return "EMAIL_FORMAT_INVALID";
        }
        if (!user.getLastName().matches(Regex.NON_EMPTY_STRING_CHECK)) {
            return "LASTNAME_IS_EMPTY";
        }
        return "OK";
    }

    @Override
    public Integer getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("user") == null) {
            return null;
        }
        User user = (User) session.getAttribute("user");
        return user.getId();
    }

    public HashSet<Role> getUserRoles(User user) throws IllegalAccessError {
        //Try to get all roles
        HashSet<Role> roles = userDAO.getRoles(user);
        if (roles == null || roles.isEmpty()) { //If doesn't have role yet, add role USER
            try {
                userDAO.addUserRole(user, Role.USER);
                roles = userDAO.getRoles(user);
            } catch (Exception ex) {
                return null;
            }
        }
        return roles;
    }

    @Override
    public boolean checkRole(HttpServletRequest request, Role role) {
        HttpSession session = request.getSession();
        if (session == null) {
            return false;
        }
        HashSet<Role> roles = (HashSet<Role>) session.getAttribute("roles");
        return roles.contains(role);
    }

    @Override
    public boolean isGuest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return !(session != null && session.getAttribute("user") != null);
    }

    @Override
    public boolean isUser(HttpServletRequest request) {
        return checkRole(request, Role.USER);
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        return checkRole(request, Role.ADMIN);
    }

    public GoogleUser getGoogleUserFromGoogleLoginRequest(HttpServletRequest request) {
        //Validate csrf
        String requestCsrfToken = request.getParameter("g_csrf_token");
        if (requestCsrfToken == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; ++i) {
            if (cookies[i].getName().equals("g_csrf_token")) {
                if (!cookies[i].getValue().equals(requestCsrfToken)) {
                    return null;
                }
                break;
            }
        }

        //Get token from HTTP POST request from google
        String idTokenString = request.getParameter("credential");

        //Validate token and get information
        HttpTransport transport = new ApacheHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(Constants.Security.GOOGLE_CLIENT_ID))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException | IOException ex) {
            Logger.getLogger(AuthServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (idToken == null) {
            return null;
        }

        Payload payload = idToken.getPayload();

        // Get profile information from payload
        // String userId = payload.getSubject();
        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        //Create google user based on getted info
        GoogleUser googleUser = GoogleUser.builder()
                .email(email)
                .emailVerifired(emailVerified)
                .name(name)
                .pictureUrl(pictureUrl)
                .locale(locale)
                .familyName(familyName)
                .givenName(givenName)
                .build();

        return googleUser;
    }

    @Override
    public User googleLogin(HttpServletRequest request) throws IllegalArgumentException {
        //Get google user after logged in with google
        GoogleUser loggedInGoogleUser = getGoogleUserFromGoogleLoginRequest(request);
        if (loggedInGoogleUser == null) { //If logged in fail, return null to exit
            return null;
        }

        //Get user by google email from logged in google user
        User user = userDAO.getUserByGoogleEmail(loggedInGoogleUser.getEmail());

        if (user == null) { //If user not found, register a new account
            //Check if is mail is existed
            if (userDAO.isEmailExisted(loggedInGoogleUser.getEmail())) {
                throw new IllegalArgumentException("REGISTER_FAILED__EMAIL_IS_EXISTED");
            }

            //If everything is ok, crete a new user based on google user info
            User newUser = User.builder()
                    .email(loggedInGoogleUser.getEmail())
                    .googleEmail(loggedInGoogleUser.getEmail())
                    .firstName(loggedInGoogleUser.getFamilyName())
                    .lastName(loggedInGoogleUser.getGivenName())
                    .build();

            //Add new user to database
            User registedUser = userDAO.addUser(newUser);
            if (registedUser == null) { //If add fail, throw exception
                throw new IllegalArgumentException("REGISTER_FAILED");
            }

            //Add role for new user
            User addedUserWithRole = userDAO.addUserRole(registedUser, Role.USER);
            if (addedUserWithRole == null) { //If add fail, throw exception
                throw new IllegalArgumentException("REGISTER_FAILED");
            }

            user = registedUser; //set user to registed user to continue process
        }

        //If user existed, get user role
        HashSet<Role> roles = getUserRoles(user);
        if (roles == null) { //If get role failed, means login failed, return null to exit
            return null;
        }

        //Get session
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);     //Add user to session
        session.setAttribute("roles", roles);   //Add role to session

        return user;
    }

}
