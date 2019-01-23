package com.example.babaets.simplegallery.helpers;

import com.example.babaets.simplegallery.models.Image;

import java.util.ArrayList;

public class ModelGenerator {
    public static ArrayList<Image> getImageList() {
        ArrayList<Image> imageList = new ArrayList<>();
        imageList.add(new Image(
                "https://via.placeholder.com/600/92c952",
                "accusamus beatae ad facilis cum similique qui sunt"));
        imageList.add(new Image(
                "https://via.placeholder.com/600/771796",
                "reprehenderit est deserunt velit ipsam"));
        imageList.add(new Image(
                "https://cdn-tn.fishki.net/20/preview/2183408.jpg",
                "rat horizontal"));
        imageList.add(new Image(
                "https://via.placeholder.com/600/24f355",
                "officia porro iure quia iusto qui ipsa ut modi"));
        imageList.add(new Image(
                "https://i.pinimg.com/originals/6a/4b/b9/6a4bb9ea2f995ea4652b9303ceb1f613.jpg",
                "rat vertical"));
        imageList.add(new Image(
                "https://via.placeholder.com/600/d32776",
                "culpa odio esse rerum omnis laboriosam voluptate repudiandae"));
        imageList.add(new Image(
                "https://via.placeholder.com/600/f66b97",
                "natus nisi omnis corporis facere molestiae rerum in"));
        imageList.add(new Image(
                "https://via.placeholder.com/600/8985dc",
                "assumenda voluptatem laboriosam enim consequatur veniam placeat reiciendis error"));
        return imageList;
    }
}
