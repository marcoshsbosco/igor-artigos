import java.util.*;
import java.lang.Math;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.IOException;
import java.io.FileWriter;
import java.io.File;

class User {
    int id;
    String name;
    boolean admin;
    ArrayList<Integer> postIds = new ArrayList<Integer>();
    ArrayList<Integer> likedPostIds = new ArrayList<Integer>();

    public User(String name) {
        id = (int) (Math.random() * (1024));
        this.name = name;
        admin = false;
    }

    public Post post(String content) {
        Post post = new Post(this.id, content);
        postIds.add(post.id);

        return post;
    }

    public ImagePost postImage(String content, String imagePath) {
        ImagePost post = new ImagePost(this.id, content, imagePath);
        postIds.add(post.id);

        return post;
    }

    public void like(Post post) {
        likedPostIds.add(post.id);
        post.addLike();
    }

    public void like(ImagePost post) {
        likedPostIds.add(post.id);
        post.addLike();
    }

    public Comment comment(String content, Post post) {
        Comment comment = new Comment(post.id, id, content);

        post.addComment(comment);

        return comment;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void info() {
        System.out.println("\n--- User ---");
        System.out.print("User ID: ");
        System.out.println(id);
        System.out.print("Post IDs: ");
        System.out.println(postIds);
        System.out.print("Liked post IDs: ");
        System.out.println(likedPostIds);
        System.out.println("------------");
    }

    public void save() {
        try {
            File file = new File("./data/users/");
            file.mkdir();
            FileWriter writer = new FileWriter("data/users/" + Integer.toString(id) + ".json");
            JSONObject json = new JSONObject();

            json.put("id", id);
            json.put("name", name);
            json.put("admin", admin);
            json.put("postids", postIds);
            json.put("likedPostids", likedPostIds);

            writer.write(json.toJSONString());
            writer.close();
        } catch (IOException e) {
            System.out.println("IO error.");
            e.printStackTrace();
        }
    }
}
