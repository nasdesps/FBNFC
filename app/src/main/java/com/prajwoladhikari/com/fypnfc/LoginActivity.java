package com.prajwoladhikari.com.fypnfc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{

    CallbackManager callbackManager;
//    TextView txtEmail, txtBirthday, txtFriends;
    ProgressDialog mDialog;
//    ImageView imgAvatar;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        callbackManager = CallbackManager.Factory.create();

//        txtBirthday = (TextView)findViewById(R.id.txtBirthday);
//        txtEmail = (TextView)findViewById(R.id.txtEmail);
//        txtFriends = (TextView)findViewById(R.id.txtFriends);

//        imgAvatar = (ImageView)findViewById(R.id.avatar);

        final LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email", "user_birthday", "user_friends"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Retrieving data...");
                mDialog.show();

                startActivity(new Intent(LoginActivity.this,HomeActivity.class));

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

//    private void printKeyHash() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.prajwoladhikari.com.fypnfc", PackageManager.GET_SIGNATURES);
//            for (Signature signature:info.signatures)
//            {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }

}
