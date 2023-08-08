package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputName;
    private Button button ;
    private TextView loginRedirect;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.editTextR1);
        inputPassword = findViewById(R.id.editTextTextPassword);
        inputName = findViewById(R.id.editTextText2);
        loginRedirect = findViewById(R.id.textView);
        button = findViewById(R.id.button3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(email.isEmpty()) {
                    inputEmail.setError("Thiếu trường Email");
                }
                if(password.isEmpty()) {
                    inputPassword.setError("Thiếu trường mật khẩu");
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()) {
                             Toast.makeText(Register.this,"Đăng ký thành công", Toast.LENGTH_SHORT).show();
                         }
                        }
                    });
                }

            }
        });

     loginRedirect.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(Register.this, MainActivity.class));
         }
     });
    }
}