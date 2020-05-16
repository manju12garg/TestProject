package com.ncg.testproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

public class Login extends AppCompatActivity {

    EditText username,password,classid;
    Button login;
    FirebaseAuth firebaseAuth;
    public static int counter;
   /* private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        username=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        classid=(EditText)findViewById(R.id.classid);
        login=(Button)findViewById(R.id.register);

       /* authStateListener=new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser !=null )
                {
                    Intent intent=new Intent(Login.this,Home.class);
                    startActivity(intent);
                }
            }
        };*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=username.getText().toString();
                String pass=password.getText().toString();
                String cid=classid.getText().toString();
                if(email.isEmpty())
                {
                    username.setError("Please enter email ID");
                    username.requestFocus();
                }
                else if(pass.isEmpty())
                {
                    password.setError("Please enter Password");
                    password.requestFocus();
                }
                else if(cid.isEmpty())
                {
                    classid.setError("Please enter your Class ID");
                    classid.requestFocus();
                }
                else if(!(email.isEmpty() && pass.isEmpty()))
                {

                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(Login.this,"Login Error",Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                final DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        user_profile u=dataSnapshot.getValue(user_profile.class);
                                        counter=u.getLogin_chk();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                switch (counter)
                                {
                                    case 0:myRef.child("login_chk").setValue(counter+1);
                                        Intent intent = new Intent(Login.this, Home.class);
                                        startActivity(intent);
                                        break;
                                }
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Login.this,"Error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}


   /* alreadySignedIn() {

       userId = firebaseAuth.getCurrentUser();
        {
            //mDatabase.child("users").child(userId).setValue(user);
            if(
            mDatabase.child("SignedIn").child(userId).observeSingleEvent(of: .value, with: { snap in
                if let dict = snap.value as? [String: Any] {
                    if signedIn = dict["signedIn"] as? Bool {
                        if signedIn {
                            // display an alert telling the user only one device can use
                            // there account at a time
                System.exit(0);
                        }
                    else {
                            // change the screen like normal
                            Intent intent=new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            })
        }
    }*/


