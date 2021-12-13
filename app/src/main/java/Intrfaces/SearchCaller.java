package Intrfaces;

import android.graphics.Bitmap;

import utils.Post;

import java.util.ArrayList;

public interface SearchCaller {

    void gotSearchResults(ArrayList<Post> posts, ArrayList<Bitmap> photos);

}
