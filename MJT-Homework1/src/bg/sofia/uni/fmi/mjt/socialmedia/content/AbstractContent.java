package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public abstract class AbstractContent implements Content {

    static long idCounter = 0;
    private final String creatorsUsername;
    private final String id;
    private String description;
    private List<Comment> comments;
    private List<String> likes;
    private LocalDateTime publicationDate;

    AbstractContent(String creatorsUsername, String description, LocalDateTime publicationDate) {
        this.creatorsUsername = creatorsUsername;
        this.publicationDate = publicationDate;
        this.description = description;
        comments = new ArrayList<>();
        this.id = this.generateId();
        likes = new ArrayList<>();
    }

    @Override
    public int getNumberOfLikes() {
        return likes.size();
    }

    @Override
    public int getNumberOfComments() {
        return comments.size();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Collection<String> getTags() {
        return getWordsStartingWithFromDescription("#");
    }

    @Override
    public Collection<String> getMentions() {
        return getWordsStartingWithFromDescription("@");
    }

    private List<String> getWordsStartingWithFromDescription(String symbol) {
        List<String> temp = new ArrayList<>();
        String strippedDescription = this.description.replaceAll("\\s{2,}", " ").trim();
        String[] words = strippedDescription.split(" ");
        for (String word : words) {
            if (word.startsWith(symbol)) {
                temp.add(word);
            }
        }
        return temp;
    }

    private String generateId() {
        return String.format("%s-%s", creatorsUsername, idCounter++);
    }

    public void commentContent(String text, String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null!");
        }
        comments.add(new Comment(text, username));
    }

    public void likeContent(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        }
        likes.add(username);
    }

    @Override
    public boolean isActive() {
        LocalDateTime today = LocalDateTime.now();
        long difference = Duration.between(this.publicationDate, today).toDays();
        return difference < 30;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractContent that = (AbstractContent) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void like(String username) {
        likes.add(username);
    }

    public void comment(String username, String text) {
        comments.add(new Comment(text, username));
    }

    public String getCreatorsUsername() {
        return this.creatorsUsername;
    }
}
