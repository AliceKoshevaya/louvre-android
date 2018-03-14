package ua.nure.koshova.louvre.controller;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import ua.nure.koshova.louvre.exception.RegisterException;


public class RegisterController {

    private static final String CODE_OK = "OK";

    public void registerUser(String login,
                             String email,
                             String pass,
                             Integer age,
                             String country,
                             String sexUser) throws RegisterException {

        BackendlessUser user = new BackendlessUser();

        user.setPassword(pass);
        user.setEmail(email);
        user.setProperty("age", age);
        user.setProperty("country", country);
        user.setProperty("login", login);
        user.setProperty("sex", sexUser);

//        try {
//            user = Backendless.UserService.register(user);
//        } catch (BackendlessException exception) {
//            throw new RegisterException(exception.getCode());
//            // an error has occurred, the error code can be retrieved with fault.getCode()
//        }

        final String[] responseCode = { null };

        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            public void handleResponse(BackendlessUser registeredUser) {
                System.out.println("FUCK");
                System.out.println("FUCK");

                responseCode[0] = CODE_OK;
            }

            public void handleFault(BackendlessFault fault) {
                System.out.println("FUCK");
                System.out.println("FUCK");

                responseCode[0] = fault.getCode();
            }
        });

        while (responseCode[0] == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Still null");
            }
        }

        // we've got the result
        if (!responseCode[0].equals(CODE_OK)) {
            throw new RegisterException(responseCode[0]);
        }
    }
}



