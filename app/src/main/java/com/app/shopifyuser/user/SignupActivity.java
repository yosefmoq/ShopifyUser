package com.app.shopifyuser.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.shopifyuser.R;
import com.app.shopifyuser.model.RigesterRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignupActivity extends AppCompatActivity {

    EditText etUsername, etPhonenumber, etPassword, etConfirmPassword;
    RadioButton rbUser, rbDriver;
    RadioGroup rgType;
    TextView tvSignIn;
    AppCompatButton btnSignup;
    SweetAlertDialog sweetAlertDialog;
    String username, phone, password, confirmPassword;
    int type = 1;
    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        initItems();
        initClicks();

    }

    private void initItems() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("users");
        sweetAlertDialog = new SweetAlertDialog(SignupActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setCancelable(false);
    }

    private void initClicks() {
        tvSignIn.setOnClickListener(v -> {
            finish();
        });
        btnSignup.setOnClickListener(v -> {
            username = etUsername.getText().toString();
            phone = etPhonenumber.getText().toString();
            password = etPassword.getText().toString();
            confirmPassword = etConfirmPassword.getText().toString();

            if (username.equalsIgnoreCase("") || username.length() < 6) {
                etUsername.setError("Please enter valid Username");
                return;
            }
            if (phone.equalsIgnoreCase("")) {
                etPhonenumber.setError("Please enter valid phone number");
                return;
            }
            if (password.equalsIgnoreCase("") || password.length() < 6) {
                etPassword.setError("Please enter a valid password");
            }
            if (!confirmPassword.equalsIgnoreCase(password)) {
                etConfirmPassword.setError("Enter match password");
            }
            doSignup();


        });
    }

    private void doSignup() {
        if (!sweetAlertDialog.isShowing())
            sweetAlertDialog.show();
        RigesterRequest rigesterRequest = new RigesterRequest();
        collectionReference.get().addOnCompleteListener(command -> {
            if (command.isSuccessful()) {
                collectionReference.whereEqualTo("phoneNumber",phone).get().addOnCompleteListener(command1 -> {
                    if(command1.getResult().getDocuments().size()>0){
                        etPhonenumber.setError("Phone number exist");
                        if(sweetAlertDialog.isShowing())
                            sweetAlertDialog.hide();
                    }else {
                        int id = command.getResult().getDocuments()==null?0:command.getResult().getDocuments().size()+1;
                        rigesterRequest.setId(id);
                        rigesterRequest.setPhoneNumber(phone);
                        rigesterRequest.setPassword(password);
                        rigesterRequest.setUsername(username);
                        rigesterRequest.setEmail("");
                        rigesterRequest.setType(rbUser.isChecked()?1:2);
                        collectionReference.document(id+"").set(rigesterRequest).addOnCompleteListener(command2 -> {
                            if(command2.isSuccessful()){
                                sweetAlertDialog.hide();
                                finish();
                            }
                        });
                    }
                });
            }
        });
    }

    private void initViews() {
        etUsername = findViewById(R.id.et_username);
        etPhonenumber = findViewById(R.id.et_phoneNumber);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirmPassword);
        rbUser = findViewById(R.id.rbUser);
        rbDriver = findViewById(R.id.rbDriver);
        rgType = findViewById(R.id.rgSignin);
        tvSignIn = findViewById(R.id.tv_signIn);
        btnSignup = findViewById(R.id.btn_signUp);

    }
}