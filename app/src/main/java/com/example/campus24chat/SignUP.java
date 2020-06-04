package com.example.campus24chat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUP extends AppCompatActivity{
    TextInputEditText               signinHeadline, signinFirstName,signinLastName,signinUsername,signinHobby1,signinHobby2,signinHobby3;
    TextInputEditText               signinHobby1Color,signinHobby2Color,signinHobby3Color,signinInstituteName,signinAbout;
    TextInputEditText               signinEmail, signinPassword;
    ProgressDialog                  loadingBar;
    Button                          btnRegisterUser;
    private FirebaseAuth            mAuth;
    FirebaseFirestore               dbref;
    Map<String,Object>              mProfile;
    CollectionReference             userCollectionRef;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loadingBar         = new ProgressDialog(this);

        mAuth              = FirebaseAuth.getInstance();
        dbref              = FirebaseFirestore.getInstance();
        userModel          = new UserModel();
//        userCollectionRef  = dbref.collection("Users/"+mAuth.getUid());

        signIn();

    }
    void signIn()
    {
        signinHeadline         = findViewById(R.id.signinHeadline);
        signinFirstName         = findViewById(R.id.signinFirstName);
        signinLastName          = findViewById(R.id.signinLastName);
        signinUsername          = findViewById(R.id.signinUsername);
        signinHobby1            = findViewById(R.id.signinHobby1);
        signinHobby2            = findViewById(R.id.signinHobby2);
        signinHobby3            = findViewById(R.id.signinHobby3);
        signinHobby1Color       = findViewById(R.id.signinHobby1Color);
        signinHobby2Color       = findViewById(R.id.signinHobby2Color);
        signinHobby3Color       = findViewById(R.id.signinHobby3Color);
        btnRegisterUser      = findViewById(R.id.signinbtnStudent);
        signinInstituteName     = findViewById(R.id.signinInstituteName);
        signinAbout             = findViewById(R.id.signinAbout);

        signinEmail             = findViewById(R.id.signinEmail);
        signinPassword          = findViewById(R.id.signinPassword);

        //Set the adapter
        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String headline  = signinHeadline.getText().toString();
                final String firstname  = signinFirstName.getText().toString();
                final String lastname        = signinLastName.getText().toString();
                final String username        = signinUsername.getText().toString();
                final String hobby1        = signinHobby1.getText().toString();
                final String hobby2     = signinHobby2.getText().toString();
                final String hobby3    = signinHobby3.getText().toString();
                final String hobby1Color      = signinHobby1Color.getText().toString();
                final String hobby2Color        = signinHobby2Color.getText().toString();
                final String hobby3Color     = signinHobby3Color.getText().toString();
                final String institute     = signinInstituteName.getText().toString();
                final String about     = signinAbout.getText().toString();

                final String email = signinEmail.getText().toString();
                final String password = signinPassword.getText().toString();
                if(!headline.isEmpty() && !firstname.isEmpty() && !lastname.isEmpty() &&
                        !username.isEmpty() && !hobby1.isEmpty() &&
                        !hobby2.isEmpty() && !hobby3.isEmpty() && !hobby1Color.isEmpty() && !hobby2Color.isEmpty() &&!hobby3Color.isEmpty() &&
                        !institute.isEmpty() &&!about.isEmpty() &&!email.isEmpty() && !password.isEmpty() ) {
                        loadingBar.setTitle("Creating new Account");
                        loadingBar.setMessage("Please wait, while we are creating new account for you...");
                        loadingBar.setCanceledOnTouchOutside(true);
                        loadingBar.show();
                        mAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            userModel.setHeadline(headline);
                                            userModel.setFirstname(firstname);
                                            userModel.setLastname(lastname);
                                            userModel.setUsername(username);
                                            userModel.setProfilepic("http://fetch.me");
                                            userModel.setHobby1(hobby1);
                                            userModel.setHobby2(hobby2);
                                            userModel.setHobby3(hobby3);
                                            userModel.setHobby1Color(hobby1Color);
                                            userModel.setHobby2Color(hobby2Color);
                                            userModel.setHobby3Color(hobby3Color);
                                            userModel.setInstitute(institute);
                                            userModel.setAbout(about);
                                            userModel.setUid(mAuth.getUid());
                                            userCollectionRef  = dbref.collection("Users");
                                            userCollectionRef.document(""+mAuth.getUid()).collection("profile_info")
                                                    .add(userModel)
                                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                        @Override
                                                        public void onSuccess(DocumentReference documentReference) {
                                                            Toast.makeText(SignUP.this, "Data Inserted Successfully!!!", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(SignUP.this, "Data Insertion Failed!!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                            loadingBar.dismiss();
                                            Toast.makeText(SignUP.this, "User Id created Successfully!!!", Toast.LENGTH_SHORT).show();

                                        } else {
                                            loadingBar.dismiss();
                                            Toast.makeText(SignUP.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                }
                else {
                    Snackbar.make(v, "All fields are required.", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

}
