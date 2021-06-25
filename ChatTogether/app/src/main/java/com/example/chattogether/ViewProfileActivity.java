package com.example.chattogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chattogether.models.User;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;
import org.jetbrains.annotations.NotNull;

public class ViewProfileActivity extends AppCompatActivity {

    String userId;
    ImageView avatar, btn_back, background;
    TextView fullname, username
            , email, location, live_in
            , education, relationship;
//    FirebaseUser firebaseUser;
    DatabaseReference mRef;
    Button message, photos;
    String imgUrl, bgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent i = getIntent();
        userId = i.getStringExtra("userId");

        avatar = findViewById(R.id.avatar);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        location = findViewById(R.id.location);
        btn_back = findViewById(R.id.back);
        live_in = findViewById(R.id.live);
        education = findViewById(R.id.education);
        relationship = findViewById(R.id.relationship);
        background = findViewById(R.id.background);
        message = findViewById(R.id.message);
        photos = findViewById(R.id.photos);

        mRef = FirebaseDatabase.getInstance().getReference("MyUser").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user.getImageUrl().equals("default"))
                    avatar.setImageResource(R.drawable.send);
                else
                    Glide.with(getApplicationContext()).load(user.getImageUrl()).into(avatar);

                username.setText("@"+user.getUsername());
                email.setText(user.getEmail());
                fullname.setText(user.getFullname());
                location.setText(user.getLocation());
                live_in.setText(user.getLive_in());
                education.setText(user.getEducation());
                relationship.setText(user.getRelationship());
                if(user.getBackground().equals("default"))
                    background.setImageResource(R.drawable.bg);
                else
                    Glide.with(getApplicationContext()).load(user.getBackground()).into(background);
                imgUrl = user.getImageUrl();
                bgUrl = user.getBackground();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // btn_message is clicked
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this, MessageActivity.class);
                intent.putExtra("imgUrl", imgUrl);
                intent.putExtra("userID",userId);
                startActivity(intent);
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this, ViewImage.class);
                intent.putExtra("src",imgUrl);
                startActivity(intent);
            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewProfileActivity.this, ViewImage.class);
                intent.putExtra("src",bgUrl);
                startActivity(intent);
            }
        });




    }
}