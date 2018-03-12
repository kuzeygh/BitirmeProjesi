package com.example.mustafa.switchtab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    EditText email,password;
    FirebaseAdapter firebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.login_etEmail);
        password = (EditText) findViewById(R.id.login_etPassword);
        firebaseAdapter=new FirebaseAdapter();
    }
    public void login(View v){
        firebaseAdapter.uyeGirisYap(email.getText().toString(),password.getText().toString(),this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAdapter.authStateListenerEkle();
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAdapter.authStateListenerTemizle();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,FirstActivity.class);
        startActivity(intent);
    }
}
