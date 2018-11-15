package com.prajwoladhikari.com.fypnfc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.prajwoladhikari.com.fypnfc.activity.ReadingWritingActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    TextView txtEmail, txtBirthday, txtFriends;
    ImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtBirthday = findViewById(R.id.txtBirthday);
        txtEmail = findViewById(R.id.txtEmail);
        txtFriends = findViewById(R.id.txtFriends);

        ImageView imageView = findViewById(R.id.avatar);
        Profile profile = Profile.getCurrentProfile();
        TextView textView = findViewById(R.id.info);
        textView.setText("Name:" + Profile.getCurrentProfile().getFirstName() +
                Profile.getCurrentProfile().getLastName());
        Button logoutButton = findViewById(R.id.button2);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ReadingWritingActivity.class));
            }
        });

        Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(50, 50)).into(imageView);
        getOtherData();
    }

    public void getOtherData() {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("response", response.toString());
                getData(object);

            }
        });

        //Request Graph API
        Bundle parameters = new Bundle(); //fetch
        parameters.putString("fields", "id,email,birthday,friends");
        request.setParameters(parameters);
        request.executeAsync();

    }


    private void getData(JSONObject object) {
        try {
            new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");

            //  Picasso.with(this).load(profile_picture.toString()).into(imgAvatar);

          txtEmail.setText(object.getString("email"));
          txtBirthday.setText(object.getString("birthday"));
          txtFriends.setText("Friends: "+object.getJSONObject("friends").getJSONObject("summary").getString("total_count"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}