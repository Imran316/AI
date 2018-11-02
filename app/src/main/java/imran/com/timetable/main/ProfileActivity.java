package imran.com.timetable.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import imran.com.timetable.R;
import imran.com.timetable.login.EditProfileActivity;
import imran.com.timetable.login.LoginActivity;
import imran.com.timetable.login.UserProfile;

public class ProfileActivity extends AppCompatActivity {
    CardView Timetable, Profile;
    FloatingActionButton Home, EditProfile, Logout;

    TextView Name, Username,RollNo, Year, Batch;


    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        setUIViews();
        BottomNav();
        getUserData();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
    }

    private void setUIViews(){
        Name = (TextView)findViewById(R.id.tvName);
        Username = (TextView)findViewById(R.id.tvUsername);
        RollNo = (TextView)findViewById(R.id.tvRollNo);
        Year = (TextView)findViewById(R.id.tvYear);
        Batch = (TextView)findViewById(R.id.tvBatch);
    }

    public void BottomNav(){
        Timetable = (CardView)findViewById(R.id.cvTimetable);
        Profile = (CardView)findViewById(R.id.cvProfile);
        Home = (FloatingActionButton)findViewById(R.id.fabHome);
        Logout = (FloatingActionButton)findViewById(R.id.fabLogout);

        EditProfile = (FloatingActionButton)findViewById(R.id.fabEditProfile);

        Timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TimetableActivity.class));
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }


    private void getUserData(){
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                Name.setText(userProfile.getName());
                Username.setText(userProfile.getUsername());
                RollNo.setText(userProfile.getRollNo());
                Year.setText(userProfile.getYear());
                Batch.setText(userProfile.getBatch());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
