package com.example.chattogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chattogether.Adapter.ViewPagerAdapter;
import com.example.chattogether.R;
import com.example.chattogether.fragment.Avatars;
import com.example.chattogether.fragment.BackgroundFragment;
import com.example.chattogether.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PhotosActivity extends AppCompatActivity {
//    ArrayList<String> photoList;
//    StorageReference storageReference;
    String userId;
    ImageView btn_back;
    TextView username;
    DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        // get data
        Intent intent = getIntent();
        int avatarId = intent.getIntExtra("avatarId",0);
        int backgroundId = intent.getIntExtra("backgroundId", 0);
        userId = intent.getStringExtra("userId");
        btn_back = findViewById(R.id.back);
        username = findViewById(R.id.username);

        // setLayout
        TabLayout tabLayout  = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // put data to AvatarsFragment
        Bundle bundle = new Bundle();
        bundle.putInt("avatarId", avatarId);

        bundle.putInt("backgroundId",backgroundId);
        bundle.putString("userId",userId);

        Avatars avatars = new Avatars();
        avatars.setArguments(bundle);

        viewPagerAdapter.addFragment(avatars, "Avatar");

        BackgroundFragment backgroundFragment = new BackgroundFragment();
        backgroundFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(backgroundFragment,"Background");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        // set view data
        mRef = FirebaseDatabase.getInstance().getReference("MyUser").child(userId);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user =  snapshot.getValue(User.class);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        //Back to previous page
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}