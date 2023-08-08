package com.example.todolist;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private Button button, button2;
    private MyAdapter adapter;

    private RecyclerView listView;
    ArrayList<Task> listTask;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://todolist2-99965-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Task");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        listView = findViewById(R.id.riew);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(this));
        button = findViewById(R.id.button);

        listTask = new ArrayList<>();
        adapter = new MyAdapter(listTask,this);
//        adapter = new MyAdapter(Dashboard.this,listTask);
        listView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                listTask = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Task task = dataSnapshot.getValue(Task.class);
                    boolean isexist = false;
                    for(Task etask: listTask) {
                        if(task.getName()== (etask.getName())) {
                            isexist = true;
                        }
                    }
                    if(!isexist) {
                        listTask.add(task);
                    }

                }
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Dashboard.this,"Lấy danh sách task thất bại",Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTask.newInstance().show(getSupportFragmentManager(),"AddTask");
            }
        });
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
    private void EventChangeListener() {

    }


    }


