package com.example.a81c;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class MyPlaylistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter playListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);
        recyclerView = findViewById(R.id.recyclerView);
        SharedPreferences sharedPreferences = getSharedPreferences("my_pref", MODE_PRIVATE);
        String login_name = sharedPreferences.getString("login_name", "");
        Query query = FirebaseDatabase.getInstance().getReference(login_name);
        FirebaseRecyclerOptions<String> options=
                new FirebaseRecyclerOptions.Builder<String>()
                        .setQuery(query, new SnapshotParser<String>() {
                            @NonNull
                            @Override
                            public String parseSnapshot(@NonNull DataSnapshot snapshot) {
                                String link = snapshot.getValue().toString();
                                Log.v("link 数组里都有啥", snapshot.getValue().toString());
                                return link;
                            }
                        })
                        .build();
        playListAdapter = new FirebaseRecyclerAdapter<String, MyViewHolder>(options){
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.link_item, parent, false);

                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull String model) {

                holder.video_item.setText(model);
            }
        };
        recyclerView.setAdapter(playListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView video_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            video_item = itemView.findViewById(R.id.link_item_textView);

        }
    }
    @Override
    public void onStart() {
        super.onStart();
        playListAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        playListAdapter.stopListening();
    }

}