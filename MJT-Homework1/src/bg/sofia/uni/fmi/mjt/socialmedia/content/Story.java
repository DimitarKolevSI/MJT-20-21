package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.time.LocalDateTime;

public class Story extends AbstractContent {
    public Story(String creatorsUsername, String description, LocalDateTime publicationDate) {
        super(creatorsUsername, description, publicationDate);
    }
}
