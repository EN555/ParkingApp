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
import java.util.LinkedList;
import java.util.List;
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
     * @param post - post to upload
     * @param calledFrom - where the method was called, to notify when finished
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


    /**
     *
     * @param calledFrom - where the method was called, to notify when finished
     * @param values - values to search by
     */
    public static void search(SearchCaller calledFrom, Map<SearchFields, String> values){

            DataBase.getReference("Posts").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    List<Post> allPosts = new LinkedList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        allPosts.add(snapshot1.getValue(Post.class));
                    }


//                    Map<String, Post> allPosts = (Map<String, Post>) snapshot.getValue();   // get all posts

                    ArrayList<Post> posts = new ArrayList<>();

                    // go through all the posts and search according to the given values
                    for(Post p : allPosts){
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

