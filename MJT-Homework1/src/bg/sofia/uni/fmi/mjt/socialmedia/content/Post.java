package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.time.LocalDateTime;

public class Post extends AbstractContent {

    public Post(String creatorsUsername, String description, LocalDateTime publicationDate) {
        super(creatorsUsername, description, publicationDate);
    }
}
