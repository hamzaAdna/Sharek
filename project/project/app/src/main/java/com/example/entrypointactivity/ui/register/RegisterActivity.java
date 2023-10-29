package com.example.entrypointactivity.ui.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.entrypointactivity.R;
import com.example.entrypointactivity.model.Status;
import com.example.entrypointactivity.model.user.User;
import com.example.entrypointactivity.ui.login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel registerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        AppCompatEditText fullName=findViewById(R.id.username);
        AppCompatEditText email=findViewById(R.id.email);
        AppCompatEditText phone=findViewById(R.id.phone);
        AppCompatEditText password=findViewById(R.id.password);
        Button insert=findViewById(R.id.insert);
        TextView textViewGoLogin =findViewById(R.id.textView_go_login);
        getSupportActionBar().setTitle("Register");


        textViewGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User userLogin=new User();
                userLogin.setPassword(password.getText().toString().trim());
                userLogin.setName(fullName.getText().toString().trim());
                userLogin.setPhone(phone.getText().toString().trim());
                userLogin.setEmail(email.getText().toString().trim());
                userLogin.setRole("User");
                registerViewModel.inserUser(userLogin);
            }
        });



        registerViewModel.getStatusInsertUserLogin().observe(this, new Observer<Status>() {
                    @Override
                    public void onChanged(Status status) {

                    }
                });

    }
}