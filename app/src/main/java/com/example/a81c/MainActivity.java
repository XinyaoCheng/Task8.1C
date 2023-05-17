package com.example.a81c;


/**
 * Xinyao Cheng
 * SIT305
 * 223122637
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseRef;
    EditText video_link_editView;
    Button play_button, add_playlist_button, my_playlist_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video_link_editView = findViewById(R.id.vedioLink_editView);
        play_button = findViewById(R.id.play_button);
        add_playlist_button = findViewById(R.id.add_playList_button);
        my_playlist_button = findViewById(R.id.my_playList_button);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
                intent.putExtra("video_url",video_link_editView.getText().toString());
                startActivity(intent);

            }
        });

        add_playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get url link
                String newLink = video_link_editView.getText().toString();

                SharedPreferences sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE);
                String login_name = sharedPreferences.getString("login_name", "");
                databaseRef = FirebaseDatabase.getInstance().getReference(login_name);
                String linkId = databaseRef.push().getKey();
                databaseRef.child(linkId).setValue(newLink)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MainActivity.this,"successful to add to your playlist", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("fail to add link", e.getMessage());

                            }
                        });

            }
        });

        my_playlist_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyPlaylistActivity.class));

            }
        });
    }
}

