package com.ncg.testproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity {

    Button btn_out,validity;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseDatabase firebaseDatabase;
    TextView tv,tv1;
    String enddate,date,demo="2020-04-01";

    @Override
    protected void onStart() {
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.child("login_chk").setValue(1);

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn_out=(Button)findViewById(R.id.logout);
        tv=(TextView)findViewById(R.id.date);
        tv1=(TextView)findViewById(R.id.currdate);
        validity=(Button)findViewById(R.id.valid);
        /*user_chk=(Button)findViewById(R.id.user);*/


        //logout button//
        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                myRef.child("login_chk").setValue(0);
                logout();
                Intent intent=new Intent(Home.this,MainActivity.class);
                startActivity(intent);
            }
        });



        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        tv1.setText(date);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_profile u=dataSnapshot.getValue(user_profile.class);
                enddate=u.getEnd_date();
                tv.setText(enddate);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home.this,databaseError.getCode(),Toast.LENGTH_LONG).show();
            }
        });

        /* Validity Check*/
        validity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");

                try{
                    Date d = sdf.parse(enddate);
                    if(System.currentTimeMillis()>d.getTime())
                    {

                        Toast.makeText(Home.this,"Same",Toast.LENGTH_LONG).show();
                        logout();
                        /*Intent intent=new Intent(Home.this,MainActivity.class);
                        startActivity(intent);*/
                    }
                    else
                    {
                        Toast.makeText(Home.this,"Not Same",Toast.LENGTH_LONG).show();
                    }

                }catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });

     /*  user_chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });*/

    }
    /* public void check()
     {
         if(check_login>1)
         {
             Toast.makeText(Home.this,"Already Login in another device",Toast.LENGTH_LONG).show();
             logout();
             Intent intent=new Intent(Home.this,MainActivity.class);
             startActivity(intent);
         }
     }*/
    public void logout()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.getInstance().signOut();
    }
}

