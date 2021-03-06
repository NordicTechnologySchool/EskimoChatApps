package com.example.abduhalimwibowo.waa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class MainActivity extends AppCompatActivity {

    static final String APP_ID = "73362";
    static final String AUTH_KEY = "3EGX9KOPjmqdaSQ";
    static final String AUTH_SECRET = "FY4d8jecAcG66dL";
    static final String ACCOUNT_KEY = "KDSmg5nAfRY8yn-cxM2D";

    Button btnLogin,btnSignup;
    EditText edtUsername,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeFramework();

        btnLogin = (Button)findViewById(R.id.main_btnSignIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();

                QBUser qbUser = new QBUser(username, password);

                QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                    @Override
                    public void onSuccess(QBUser qbUser, Bundle bundle) {
                        Toast.makeText(getBaseContext(), "Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this, ChatDialogsActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("password", password);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        Toast.makeText(getBaseContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSignup = (Button)findViewById(R.id.main_btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        edtUsername = (EditText)findViewById(R.id.main_edtUsername);
        edtPassword = (EditText)findViewById(R.id.main_edtPassword);
    }

    public void initializeFramework() {
        QBSettings.getInstance().init(getApplicationContext(), APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }
}
