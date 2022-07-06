package ukm.com.academiccalendar.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

import ukm.com.academiccalendar.MainPageActivity;
import ukm.com.academiccalendar.R;

public class RegisterActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,15}" +               //at least 6 characters, not more than 15 character
                    "$");

    private static final Pattern MATRIC_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{7,8}" +               //at least 7 characters, not more than 8
                    "$");

    private Button CreateAccountButton;
    private EditText InputMatric, InputName, InputPhone, InputPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputMatric = (EditText) findViewById(R.id.register_matric_input);
        InputName = (EditText) findViewById(R.id.register_name_input);
        InputPhone = (EditText) findViewById(R.id.register_phone_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }
    private boolean validateMatric() {
        String matricInput = InputMatric.getText().toString().trim();
        if (matricInput.isEmpty()) {
            InputMatric.setError("Field can't be empty");
            return false;
        } else if (!MATRIC_PATTERN.matcher(matricInput).matches()) {
            InputMatric.setError("Invalid Matric Format");
            return false;
        } else {
            InputMatric.setError(null);
            return true;
        }
    }

        private boolean validateName() {
            String usernameInput = InputName.getText().toString().trim();
            if (usernameInput.isEmpty()) {
                InputName.setError("Field can't be empty");
                return false;
            } else if (usernameInput.length() > 30) {
                InputName.setError("Username exceed the limit");
                return false;
            } else {
                InputName.setError(null);
                return true;
            }
        }

    private boolean validateNumber() {
        String phoneInput = InputPhone.getText().toString().trim();
        if (phoneInput.isEmpty()) {
            InputPhone.setError("Field can't be empty");
            return false;
        } else {
            InputPhone.setError(null);
            return true;
        }
    }




    private boolean validatePassword() {
        String passwordInput = InputPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            InputPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            InputPassword.setError("Password too weak");
            return false;
        } else {
            InputPassword.setError(null);
            return true;
        }
    }


    private void CreateAccount(){
        String matric = InputMatric.getText().toString();
        String name = InputName.getText().toString();
        String phone = InputPhone.getText().toString();
        String password = InputPassword.getText().toString();



        if(!validateMatric()|!validateName()|!validateNumber()|!validatePassword()){
            return;
        }


            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(matric, name, phone, password);


    }
    private void ValidateEmail(final String matric, final String name,final String phone, final String password){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                if(!(dataSnapshot.child("Users").child(matric).exists()) ){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("matric", matric);
                    userdataMap.put("name", name);
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", password);

                    RootRef.child("Users").child(matric).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Congratulations, your account has been created", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(RegisterActivity.this, matric+" already exist.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Please try again using another matric.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainPageActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
