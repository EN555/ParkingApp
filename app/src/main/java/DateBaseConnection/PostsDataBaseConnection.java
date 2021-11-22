package DateBaseConnection;

import androidx.annotation.NonNull;

import com.example.myapp_1.SearchFields;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import Intrfaces.PostUploader;
import Intrfaces.SearchCaller;
import utils.Post;
import utils.Utils;

public class PostsDataBaseConnection {

    private static FirebaseAuth DataBaseAuth = FirebaseAuth.getInstance();
    private static FirebaseDatabase DataBase = FirebaseDatabase.getInstance();

    /**
     * upload a new post to the data base
     * @param post
     */
    public static void uploadPost(PostUploader calledFrom, Post post) {
        DataBase.getReference("Posts").child("" + post.hashCode()).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                  calledFrom.uploaded(task.isSuccessful());
            }
        });
    }

//
//    /**
//     * getAllPostsByCity
//     * @param
//     */
//    public static void getAllPostsByCity(String city) {
//        String c = "ariel";
//        System.out.println(DataBase.getReference("Posts").get());
//
//    }

    public static void search(SearchCaller calledFrom, Map<SearchFields, String> values){

            DataBase.getReference("posts").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map<String, Post> allPosts = (Map<String, Post>) snapshot.getValue();

                    ArrayList<Post> posts = new ArrayList<>();

                    for(Post p : allPosts.values()){
                        boolean b = true;
                        for(Map.Entry<SearchFields, String> entry : values.entrySet()){
                            switch (entry.getKey()){
                                case CITY:
                                    b &= p.getCity().equalsIgnoreCase(values.get(SearchFields.CITY));
                                    break;
                                case STREET:
                                    b &= p.getStreet().equalsIgnoreCase(values.get(SearchFields.STREET));
                                    break;
                                case DATEFROM:
                                    b &= Utils.CompareDateStrings(p.getDataFrom(), values.get(SearchFields.DATEFROM)) <= 0;
                                    break;
                                case DATETO:
                                    b &= Utils.CompareDateStrings(p.getDateTo(), values.get(SearchFields.DATETO)) >= 0;
                                    break;
                                case MAXPRICSE:
                                    b &= p.getPrice() < Integer.parseInt(values.get(SearchFields.MAXPRICSE));
                                    break;

                            }
                        }
                        if (b){ posts.add(p); }
                    }

                    calledFrom.gotSearchResults(posts);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
    }



}

