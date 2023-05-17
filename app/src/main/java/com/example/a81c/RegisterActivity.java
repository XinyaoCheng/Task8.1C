package com.example.a81c;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class RegisterActivity extends AppCompatActivity {



    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText userName_input, password_input1, password_input2;
    Button createAccount_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName_input = findViewById(R.id.user_name_input);
        password_input1 = findViewById(R.id.Password_input_one);
        password_input2 = findViewById(R.id.Password_input_two);

        createAccount_button = findViewById(R.id.create_account_button);




        //create account
        createAccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(createAccount()){
                    Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }

            }
        });
    }

    private boolean createAccount() {
        String user_name = userName_input.getText().toString();
        String password1 = password_input1.getText().toString();
        String password2 = password_input2.getText().toString();

        if(TextUtils.isEmpty(user_name)){
            userName_input.setError("username is requied");
            return false;
        }
        if(TextUtils.isEmpty(password1)){
            password_input1.setError("password is requied");
            return false;
        }
        if(TextUtils.isEmpty(password2)){
            password_input2.setError("confirmed password is requied");
            return false;
        }
        if(!password1.equals(password2)){
            password_input1.setError("please enter two same password");
            password_input2.setError("please enter two same password");
        }else{
            UserModel new_user = new UserModel(user_name);
            firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.createUserWithEmailAndPassword(user_name+"@test.com",password1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                finish();
                            }else{
                                Toast.makeText(RegisterActivity.this, "fail to create an account" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child(user_name).setValue(new_user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.v("不是可以上传用户吗？",databaseReference.child(user_name).toString());
                            Toast.makeText(RegisterActivity.this,"successful to create an account", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("创建用户错误",e.getMessage());
                        }
                    });

        }
        return true;
    }

}