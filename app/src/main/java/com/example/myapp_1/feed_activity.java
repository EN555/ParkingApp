//package com.example.myapp_1;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//
//public class feed_activity extends AppCompatActivity {
//    RecyclerView recyclerView;
//    DatabaseReference database;
//    MyAdapter_users myAdapter;
//    String s1[], s2[];
//    int image[] = {R.drawable.gif};
//    ArrayList<Post> list;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed);
//
//        recyclerView = findViewById(R.id.recycler);
//
//        s1 = getResources().getStringArray(R.array.activty_post);
//        s2 = getResources().getStringArray(R.array.description);
//
//        database = FirebaseDatabase.getInstance().getReference("Post");
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        list = new ArrayList<>();
//
//        MyAdaptor myAdaptor = new MyAdaptor(this, list);
//        recyclerView.setAdapter(myAdaptor);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        database.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Post post = dataSnapshot.getValue(Post.class);
//                    list.add(post);
//                }
//                myAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//
//        });
//
//
//    }
//}