package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<Task> arrayList;
    Context context;

    Button btnDelete;
    public MyAdapter(ArrayList<Task> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.taskentity, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

     Task task = arrayList.get(position);
     holder.name.setText(task.getName());
     holder.date.setText(task.getDate());
     holder.status.setText(task.getStatus());

     holder.btnDelete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             final int currentPosition = position;

             AlertDialog.Builder builder = new AlertDialog.Builder(holder.btnDelete.getContext());
             builder.setTitle("Bạn có chắc muốn xóa không?");

             builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Log.d("DeleteButton", "Xóa button clicked for task: " + task.getName());
                     FirebaseDatabase.getInstance("https://todolist2-99965-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Task").child(task.getName()).removeValue();
                     Toast.makeText(context, "Xóa thành công",Toast.LENGTH_LONG).show();
                     arrayList.remove(currentPosition);
                     notifyDataSetChanged();

                 }
             });

             builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Toast.makeText(context, "Hủy xoá",Toast.LENGTH_LONG).show();
                 }
             });
             AlertDialog alertDialog = builder.create();
             alertDialog.show();
//             FirebaseDatabase.getInstance("https://todolist2-99965-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Task").child(task.getName()).removeValue();
//             Toast.makeText(context, "Xóa thành công",Toast.LENGTH_LONG).show();

             notifyDataSetChanged();
         }
     });

     if(holder.status.getContext().toString().equals("Đã xong")) {
         holder.btnDone.setEnabled(false);
     }
     holder.btnDone.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             final int currentPosition = position;
             DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://todolist2-99965-default-rtdb.asia-southeast1.firebasedatabase.app/")
                     .getReference().child("Task").child(task.getName());

             databaseReference.child("status").setValue("Đã xong")
                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             Log.d("UpdateStatus", "Status updated in database");
                         }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             Log.e("UpdateStatus", "Failed to update status in database", e);
                         }
                     });
             arrayList.get(currentPosition).setStatus("Đã xong");
             holder.btnDone.setEnabled(false);
         }
     });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, status;
        Button btnDelete, btnDone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textname);
            date = itemView.findViewById(R.id.textdate);
            status = itemView.findViewById(R.id.textstatus);

            btnDelete = itemView.findViewById(R.id.deleteButton);
            btnDone = itemView.findViewById(R.id.buttonDone);
        }
    }
}
