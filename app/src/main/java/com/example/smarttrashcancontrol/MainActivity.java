package com.example.smarttrashcancontrol;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static com.example.smarttrashcancontrol.App.PEMBERITAHUAN;


public class MainActivity extends AppCompatActivity {
//    TextView hasil;
//    Button on;
//    Button off;
    public static TextView jarak, status, ip;
    FirebaseDatabase database;
    DatabaseReference myRef, myReff, myRefff;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("tag", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.d("tokennya ni", token);
                    }
                });
        ip = findViewById(R.id.ip);
        jarak = findViewById(R.id.jarak);
        status = findViewById(R.id.status);

//        hasil = findViewById(R.id.hasil);
//        on = findViewById(R.id.on);
//        off = findViewById(R.id.off);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("capacity");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                status.setText(dataSnapshot.getValue().toString());
                onPemberitahuan();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myReff = database.getReference("distance");

        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jarak.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRefff = database.getReference("ip");

        myRefff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ip.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        on.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myRef.setValue("terbuka");
//            }
//        });
//        off.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myRef.setValue("tertutup");
//            }
//        });

    }
    private void onPemberitahuan(){
        Notification notification = new NotificationCompat.Builder(this, PEMBERITAHUAN)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Smart Trash Can Control")
                .setContentText(status.getText().toString())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        if (status.getText().toString().equals("Belum Penuh")){
            notificationManagerCompat.cancelAll();
        }else {
            notificationManagerCompat.notify(1, notification);
        }
    }
}
