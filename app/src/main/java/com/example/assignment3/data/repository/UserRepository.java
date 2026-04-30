package com.example.assignment3.data.repository;

import com.example.assignment3.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class UserRepository {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String dbUrl= "https://fast-mart-b3543-default-rtdb.firebaseio.com/";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance(dbUrl).getReference("Users");

    public Task<Void> registerUser(User user, String password) {
        return mAuth.createUserWithEmailAndPassword(user.email, password)
                .continueWithTask(task -> {
                    String uid = mAuth.getCurrentUser().getUid();
                    user.userId = uid;
                    return mDatabase.child(uid).setValue(user);
                });
    }

    public Task<DataSnapshot> loginUser(String email, String password) {
        return mAuth.signInWithEmailAndPassword(email, password)
                .continueWithTask(task -> mDatabase.child(mAuth.getCurrentUser().getUid()).get());
    }
}