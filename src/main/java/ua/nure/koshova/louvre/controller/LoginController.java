package ua.nure.koshova.louvre.controller;

import com.backendless.Backendless;
import com.backendless.exceptions.BackendlessException;

import ua.nure.koshova.louvre.exception.LoginException;
import ua.nure.koshova.louvre.exception.NotConfirmedEmailException;

/**
 * @author Alexander Shylin
 */

public class LoginController {

    private static final String ERROR_CODE_EMAIL_CONFIRM_REQUIRED = "3087";

    public void loginUser(String login, String pass)
            throws NotConfirmedEmailException, LoginException {

        try {
            Backendless.UserService.login(login, pass);
        } catch (BackendlessException exception) {
            String code = exception.getCode();

            if (code.equals(ERROR_CODE_EMAIL_CONFIRM_REQUIRED)) {
                throw new NotConfirmedEmailException();
            }
            throw new LoginException();
        }
    }
}





