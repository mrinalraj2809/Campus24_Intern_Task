package com.example.campus24chat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.example.campus24chat.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class SignUP extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    TextInputEditText               txtnmStudent,txtusnStudent,txtpnoStudent,txtemailStudent,txtpwdStudent,txtcnfpwdStudent;
    TextInputEditText               signinnameTeacher,signinUniqueIdTeacher,signinpnoTeacher,signInSpecialisationTeacher,signinbranchTeacher,signinDesignationTeacher,signinemailTeacher,signinpwdTeacher,signinpwdcnfrmTeacher;

    ProgressDialog                  loadingBar;
    AutoCompleteTextView            acTxtsemStudent,acTxtdepStudent,acTxtSectionStudent;
    Button                          requestPermissionTeacher;
    Button                          requestPermissionAdmin;
    Button                          btnRegisterStudent;
    ViewStub                        stubStudent;
    ViewStub                        stubTeacher;
    ViewStub                        stubAdmin;
    ViewStub                        stubSuperAdmin;
    private FirebaseAuth            mAuth;
    FirebaseFirestore               dbref;
    Map<String,Object>              mProfile;
    Map<String,Object>              mProfile;
    Map<String,Object>              mProfile;
    Map<String,Object>              mProfile;


    //long maxid=111;
    Spinner spinnerUserType;
    MemberStudent                   memberStudent;

    String                          departmentArr[] = {"CSE","ISE","MECH","ECE","EEE","TLE","BIO","CHEM","ARCHI"};
    String                          sectionArr[] = {"A","B","C","D","E","F","G","H","I","J"};
    String                          semesterArr[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    //    MemberAdmin memberAdmin;
//    MemberSuperAdmin superAdmin
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loadingBar                  = new ProgressDialog(this);

        mAuth                       = FirebaseAuth.getInstance();
        dbref                       = FirebaseFirestore.getInstance();
        memberStudent               = new MemberStudent();

        signIn();

    }
    void signIn()
    {
        ArrayAdapter<String> adapterDepartment = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, departmentArr);
        ArrayAdapter<String> adapterSemester = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, semesterArr);
        ArrayAdapter<String> adapterSection = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, sectionArr);
        //Find TextView control

        txtnmStudent            = findViewById(R.id.signinnameStudent);
        txtusnStudent           = findViewById(R.id.signinusnStudent);
        txtpnoStudent           = findViewById(R.id.signinpnoStudent);
        acTxtsemStudent         = (AutoCompleteTextView)findViewById(R.id.signinsemStudent);
        acTxtdepStudent         = (AutoCompleteTextView)findViewById(R.id.signinbranchStudent);
        acTxtSectionStudent     = (AutoCompleteTextView)findViewById(R.id.signinSectionStudent);
        txtemailStudent         = findViewById(R.id.signinemailStudent);
        txtpwdStudent           = findViewById(R.id.signinpwdStudent);
        txtcnfpwdStudent        = findViewById(R.id.signinpwdcnfrmStudent);
        btnRegisterStudent      = findViewById(R.id.signinbtnStudent);
        //Set the number of characters the user must type before the drop down list is shown
        acTxtSectionStudent.setThreshold(1);
        acTxtdepStudent.setThreshold(1);
        acTxtsemStudent.setThreshold(1);
        //Set the adapter
        acTxtdepStudent.setAdapter(adapterDepartment);
        acTxtsemStudent.setAdapter(adapterSemester);
        acTxtSectionStudent.setAdapter(adapterSection);
        btnRegisterStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name       = txtnmStudent.getText().toString();
                final String usn        = txtusnStudent.getText().toString();
                final String pno        = txtpnoStudent.getText().toString();
                final String sem        = acTxtsemStudent.getText().toString();
                final String branch     = acTxtdepStudent.getText().toString();
                final String section    = acTxtSectionStudent.getText().toString();
                final String email      = txtemailStudent.getText().toString();
                final String pwd        = txtpwdStudent.getText().toString();
                final String cnfpwd     = txtcnfpwdStudent.getText().toString();
                int Deptflag=0;
                int Semflag=0;
                int Sectionflag=0;
                for(String s:departmentArr)
                {
                    if(s.equals(branch)){ Deptflag =1; break;}
                }
                if(Deptflag == 0){
                    Toast.makeText(SignUP.this, "Choose Department from the Option!!!", Toast.LENGTH_SHORT).show();
                }
                for(String s:sectionArr)
                {
                    if(s.equals(section)){Sectionflag =1; break;}
                }
                if(Sectionflag == 0){
                    Toast.makeText(SignUP.this, "Choose Section from the Option!!!", Toast.LENGTH_SHORT).show();
                }
                for(String s:semesterArr)
                {
                    if(s.equals(sem)){Semflag =1; break;}
                }
                if(Semflag == 0){
                    Toast.makeText(SignUP.this, "Choose Semester from the Option!!!", Toast.LENGTH_SHORT).show();
                }

                if(!name.isEmpty() && !usn.isEmpty() && !pno.isEmpty() && Sectionflag ==1 && Semflag == 1 && Deptflag == 1 &&
                        !sem.isEmpty() && !branch.isEmpty() &&
                        !email.isEmpty() && !pwd.isEmpty() && !cnfpwd.isEmpty() && pwd.equals(cnfpwd)) {
                    if (cnfpwd.equals(pwd)) {
                        loadingBar.setTitle("Creating new Account");
                        loadingBar.setMessage("Please wait, while we are creating new account for you...");
                        loadingBar.setCanceledOnTouchOutside(true);
                        loadingBar.show();
                        mAuth.createUserWithEmailAndPassword(email, pwd)
                                .addOnCompleteListener(SignUP.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            memberStudent.setStudent_name(name);
                                            memberStudent.setStudent_USN(usn);
                                            memberStudent.setStudent_phone_number(pno);
                                            memberStudent.setStudent_semester(sem);
                                            memberStudent.setStudent_section(section);
                                            memberStudent.setStudent_branch(branch);
                                            memberStudent.setStudent_email(email);
                                            memberStudent.setUser_type("LoginStudent");// login type is student
                                            //we encrypt and store password
                                            memberStudent.setStudent_password(""+pwd);
                                            memberStudent.setStudent_user_Id(mAuth.getUid());
                                            long time = System.currentTimeMillis();
                                            // fills all the colleges groups into the users' node while signup.



                                            loadingBar.dismiss();
                                            Toast.makeText(SignUP.this, "Data Inserted Successfully!!!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        } else {
                                            loadingBar.dismiss();
                                            Toast.makeText(SignUP.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                    } else {
                        Toast.makeText(SignUP.this, "Password Not Confirmed", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Snackbar.make(v, "All fields are required.", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

}
