package com.ncg.testproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/*import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;*/

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.BitSet;

public class Upload_data extends AppCompatActivity {
    EditText n,e,p,s_date,e_date;
    TextView bck_login;
    String name,password,email,sdate,edate;
    Button reg;
    FirebaseAuth firebaseAuth;
    /*    ImageView profilepic;
        FirebaseStorage firebaseStorage;
        private static final int pick_img = 123;
        Uri imagepath;
        StorageReference storageReference;*/
    public static int login_chk=0;


    /*  @Override
      protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
          if(requestCode==pick_img && requestCode== RESULT_OK && data != null && data.getData()!=null)
          {
              imagepath=data.getData();
              try {
                  Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
                  profilepic.setImageBitmap(bitmap);
              } catch (IOException ex) {
                  Toast.makeText(Upload_data.this, "Error in upload :", Toast.LENGTH_SHORT).show();
                  ex.printStackTrace();
              }
          }
          super.onActivityResult(requestCode, resultCode, data);
      }
  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_data);

        n=(EditText)findViewById(R.id.name);
        e=(EditText)findViewById(R.id.email);
        s_date=(EditText)findViewById(R.id.sd);
        e_date=(EditText)findViewById(R.id.ed);
        reg=(Button)findViewById(R.id.submit);
        p=(EditText)findViewById(R.id.password);
        /*  profilepic=(ImageView)findViewById(R.id.propic);*/
        firebaseAuth=FirebaseAuth.getInstance();
        /* firebaseStorage=FirebaseStorage.getInstance();*/

        /*  storageReference=firebaseStorage.getReference();*/


       /* profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),pick_img);

            }
        });*/

        bck_login=(TextView) findViewById(R.id.back_btn);
        bck_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Upload_data.this,MainActivity.class);
                startActivity(intent);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to the database
                    String user_email = e.getText().toString().trim();
                    String user_password = p.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                sendUserData();
                                Toast.makeText(Upload_data.this, "Successfully Registered, Upload complete!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Upload_data.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }

    private Boolean validate(){
        Boolean result = false;

        name = n.getText().toString();
        password = p.getText().toString();
        email = e.getText().toString();
        sdate = s_date.getText().toString();
        edate = e_date.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || sdate.isEmpty() || edate.isEmpty() ){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
      /*  StorageReference img_ref=storageReference.child(firebaseAuth.getUid()).child("Profile_Pic");
        UploadTask uploadTask=img_ref.putFile(imagepath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(Upload_data.this, "Image not Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(Upload_data.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

            }
        });*/
        user_profile userProfile = new user_profile(login_chk,name,email,password,sdate,edate);
        myRef.setValue(userProfile);
    }
}
