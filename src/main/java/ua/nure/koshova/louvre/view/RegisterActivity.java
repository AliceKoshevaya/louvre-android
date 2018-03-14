package ua.nure.koshova.louvre.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import ua.nure.koshova.louvre.R;
import ua.nure.koshova.louvre.controller.RegisterController;
import ua.nure.koshova.louvre.exception.RegisterException;
import ua.nure.koshova.louvre.model.UserSex;

public class RegisterActivity extends AppCompatActivity {

    private RegisterController registerController = new RegisterController();

    private UserSex sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ua.nure.koshova.louvre.R.layout.activity_main);
    }

    public void sendMessage(View view) {
        EditText loginEt = (EditText) findViewById(R.id.login);
        EditText emailEt = (EditText) findViewById(R.id.email);
        EditText passwordEt = (EditText) findViewById(R.id.password);
        EditText ageEt = (EditText) findViewById(R.id.age);
        EditText countryEt = (EditText) findViewById(R.id.country);

        String login = loginEt.getText().toString();
        String email = emailEt.getText().toString();
        String pass = passwordEt.getText().toString();
        String country = countryEt.getText().toString();
        String age = ageEt.getText().toString();

        if (login.isEmpty()) {
            loginEt.setError("Login is required");
            return;
        }
        if (email.isEmpty()) {
            emailEt.setError("Email is required");
            return;
        }
        if (pass.length() < 4) {
            if (pass.isEmpty()) {
                passwordEt.setError("Password is required");
            } else {
                passwordEt.setError("Password length must be 4 or more");
            }
            return;
        }
        if (age.isEmpty()) {
            ageEt.setError("Age is required");
            return;
        }
        if (country.isEmpty()) {
            countryEt.setError("County is required");
            return;
        }
        if (sex == null) {
            makeToast("Choose your sex");
            return;
        }
        int ageInt = Integer.parseInt(age);
        if (ageInt < 5) {
            ageEt.setError("You must be older than 5");
            return;
        }

        try {
            registerController.registerUser(
                    login,
                    email,
                    pass,
                    ageInt,
                    country,
                    sex.toString()

            );
        } catch (RegisterException e) {
            makeToast(e.getMessage());
            return;
        }

        makeToast("Email confirmation required");
        finish();
    }

    public void onRadioButtonClicked(View view) {
        sex = null;
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.male:
                if (checked) {
                    sex = UserSex.MALE;
                }
                break;
            case R.id.female:
                if (checked) {
                    sex = UserSex.FEMALE;
                }
                break;
        }
    }

    private void makeToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}