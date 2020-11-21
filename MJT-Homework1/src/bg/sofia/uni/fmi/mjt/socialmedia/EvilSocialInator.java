package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Story;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.NoUsersException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.*;

public class EvilSocialInator implements SocialMediaInator {

    private Set<String> users;
    private Map<String, Content> contents;
    private Map<String, List<String>> activityByUser;

    EvilSocialInator() {
        users = new HashSet<>();
        activityByUser = new LinkedHashMap<>();
        contents = new LinkedHashMap<>();
    }

    /**
     * Registers a new user in the platform.
     *
     * @param username
     * @throws IllegalArgumentException       If {@code username} is null
     * @throws UsernameAlreadyExistsException If there is already a user with {@code username}
     *                                        registered in the platform
     */

    @Override
    public void register(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        } else if (users.contains(username)) {
            throw new UsernameAlreadyExistsException("There is already a user with this username");
        } else {
            users.add(username);
            activityByUser.put(username, new ArrayList<>());
        }
    }

    /**
     * Publishes a post with {@code description}.
     * -> A post expires in 30 days after it was published
     * -> The description of the post may contain arbitrary number of mentions (i.e @someuser) and hash-tags
     * (i.e #programming)
     * -> If a non-existing user is mentioned in the description, the actual mention does not have any effect
     * -> The tags and mentions are always separated with at least one space from the other words in the description
     * -> The id of each post is generated as follows: [username]-[auto-incremented integer starting from 0]
     *
     * @param username
     * @param publishedOn
     * @param description
     * @return The id of the newly created post
     * @throws IllegalArgumentException  If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     */

    @Override
    public String publishPost(String username, LocalDateTime publishedOn, String description) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        } else if (publishedOn == null) {
            throw new IllegalArgumentException("Published on date cannot be null!");
        } else if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        } else if (!users.contains(username)) {
            throw new UsernameNotFoundException("There is no such user in the system!");
        } else {
            Post newPost = new Post(username, description, publishedOn);
            contents.put(newPost.getId(), newPost);
            String log = String.format("%s Created a post with id %s", getFormatedDate(publishedOn), newPost.getId());
            activityByUser.get(username).add(log);
            return newPost.getId();
        }
    }

    /**
     * Publishes a story with {@code description}.
     * -> A story expires in 24 hours after it was published
     * -> The description of the story may contain arbitrary number of mentions (i.e @someuser) and tags
     * (i.e #programming)
     * -> If a non-existing user is mentioned in the description, the actual mention does not have any effect
     * -> The tags and mentions are always separated with at least one space from the other words in the description
     * -> The id of each story is generated as follows: [username]-[auto-incremented integer starting from 0]
     *
     * @param username
     * @param publishedOn
     * @param description
     * @return The id of the newly created story
     * @throws IllegalArgumentException  If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     */

    @Override
    public String publishStory(String username, LocalDateTime publishedOn, String description) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        } else if (publishedOn == null) {
            throw new IllegalArgumentException("Published on date cannot be null!");
        } else if (description == null) {
            throw new IllegalArgumentException("Description cannot be null");
        } else if (!users.contains(username)) {
            throw new UsernameNotFoundException("There is no such user in the system!");
        } else {
            Story newStory = new Story(username, description, publishedOn);
            contents.put(newStory.getId(), newStory);
            String log = String.format("%s Created a story with id %s", getFormatedDate(publishedOn), newStory.getId());
            activityByUser.get(username).add(log);
            return newStory.getId();
        }
    }

    /**
     * Likes a content with id {@code id}.
     *
     * @param username The name of the user who liked the content
     * @param id       The id of the content
     * @throws IllegalArgumentException  If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     * @throws ContentNotFoundException  If there is no content with id {@code id} in the platform
     */

    @Override
    public void like(String username, String id) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        } else if (id == null) {
            throw new IllegalArgumentException("Id cannot be null!");
        } else if (!users.contains(username)) {
            throw new UsernameNotFoundException("There is no user with this username in the platform!");
        } else if (!contents.containsKey(id)) {
            throw new ContentNotFoundException("There is no content with this id in the platform!");
        } else {
            contents.get(id).like(username);
            String log = String.format("%s Liked a content with id %s", getFormatedDate(LocalDateTime.now()), id);
            activityByUser.get(username).add(log);
        }
    }

    /**
     * Comments on a content with id {@code id}.
     *
     * @param username The name of the user who commented the content
     * @param text     The actual comment
     * @param id       The id of the content
     * @throws IllegalArgumentException  If any of the parameters is null
     * @throws UsernameNotFoundException If a user with {@code username} does not exist in the platform
     * @throws ContentNotFoundException  If there is no content with id {@code id} in the platform
     */

    @Override
    public void comment(String username, String text, String id) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        } else if (text == null) {
            throw new IllegalArgumentException("Text cannot be null!");
        } else if (id == null) {
            throw new IllegalArgumentException("ID cannot be null!");
        } else if (!users.contains(username)) {
            throw new UsernameNotFoundException("There is no user with this username in the platform!");
        } else if (!contents.containsKey(id)) {
            throw new ContentNotFoundException("There is no content with this id in the platform!");
        } else {
            contents.get(id).comment(username, text);
            String log = String.format("%s Commented \"%s\" on a content with id %s", getFormatedDate(LocalDateTime.now()), text, id);
            activityByUser.get(username).add(log);
        }
    }

    /**
     * Returns the {@code n} most popular content on the platform.
     * -> The popularity of a content is calculated by the total number of likes and comments
     * -> If there is no content in the platform, an empty Collection should be returned
     * -> If the total number of posts and stories is less than {@code n} return as many as available
     * -> The returned Collection should not contain expired content
     *
     * @param n The number of content to be returned
     * @return Unmodifiable collection of Content sorted by popularity in descending order
     * @throws IllegalArgumentException If {@code n} is a negative number
     */

    @Override
    public Collection<Content> getNMostPopularContent(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be negative number");
        }
        if (contents.isEmpty()) {
            return Collections.emptyList();
        }
        List<Content> contents = new ArrayList<>();
        for (Content content : this.contents.values()) {
            if (content.isActive()) {
                contents.add(content);
            }
        }
        Collections.sort(contents, new CompareByTotalLikesAndComments());
        if (n >= contents.size()) {
            return contents;
        } else {
            return contents.subList(0, n);
        }
    }

    /**
     * Returns the {@code n} most recent content of user {@code username}.
     * -> If the given user does not have any content, an empty Collection should be returned.
     * -> If the total number of posts and stories is less than {@code n} return as many as available
     * -> The returned Collection should not contain expired content
     *
     * @param username
     * @param n        The number of content to be returned
     * @return Unmodifiable collection of Content sorted by popularity
     * @throws IllegalArgumentException  If {@code username} is null or {@code n} is a negative number
     * @throws UsernameNotFoundException if a user with {@code username} does not exist in the platform
     */

    @Override
    public Collection<Content> getNMostRecentContent(String username, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be negative!");
        } else if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        } else if (!users.contains(username)) {
            throw new UsernameNotFoundException("There is no user with this username in the platform!");
        } else {
            List<Content> list = new ArrayList<>();
            for (Content content : contents.values()) {
                if (content.equals(username) && content.isActive()) {
                    list.add(content);
                }
            }
            if (n >= list.size()) {
                return list;
            } else {
                Collections.reverse(list);
                return list.subList(0, n);
            }
        }
    }

    /**
     * Returns the username of the most popular user.
     * -> This is the user which was mentioned most times in stories and posts
     *
     * @throws NoUsersException if there are currently no users in the platform
     */

    @Override
    public String getMostPopularUser() {
        if (users.isEmpty()) {
            throw new NoUsersException("There are no users in the platform.");
        } else {
            Map<String, Integer> frequencyByUsername = new HashMap<>();
            for (Content content : contents.values()) {
                for (String mention : content.getMentions()) {
                    String username = mention.substring(1);
                    if (!users.contains(username)) {
                        int frequency = frequencyByUsername.containsKey(username) ? frequencyByUsername.get(username) : 0;
                        frequencyByUsername.put(username, frequency + 1);
                    }
                }
            }
            int max = -1;
            String mostPopular = "";
            for (String username : frequencyByUsername.keySet()) {
                int temp = frequencyByUsername.get(username);
                if (temp > max) {
                    max = temp;
                    mostPopular = username;
                }
            }
            return mostPopular;
        }
    }

    /**
     * Returns all posts and stories containing the tag {@code tag} in their description.
     * -> If there are no posts or stories with the given tag in the platform, an empty Collection should be returned
     * -> Note that {@code tag} should start with '#'
     * -> The returned Collection should not contain expired content
     *
     * @param tag
     * @return Unmodifiable collection of Content
     * @throws IllegalArgumentException If {@code tag} is null
     */

    @Override
    public Collection<Content> findContentByTag(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tag cannot be null");
        } else if (!tag.startsWith("#")) {
            throw new IllegalArgumentException("Tag should start with #");
        } else if (contents.size() == 0) {
            return Collections.unmodifiableCollection(Collections.emptyList());
        } else {
            List<Content> list = new ArrayList<>();
            for (Content content : contents.values()) {
                if (content.getTags().contains(tag)) {
                    list.add(content);
                }
            }
            return Collections.unmodifiableCollection(list);
        }
    }

    /**
     * Returns the activity log of user {@code username}. It contains a history of all activities of a given user.
     * -> The activity log is maintained in reversed chronological order (i.e newest events first).
     * -> It has the following format:
     * HH:mm:ss dd.mm.yy: Commented "[text]" on a content with id [id]
     * HH:mm:ss dd.mm.yy: Liked a content with id [id]
     * HH:mm:ss dd.mm.yy: Created a post with id [id]
     * HH:mm:ss dd.mm.yy: Created a story with id [id]
     * -> HH:mm:ss dd.mm.yy is a time format
     * -> If the given user does not have any activity on the platform, an empty List should be returned
     *
     * @param username
     * @return List of activities in the above format
     * @throws IllegalArgumentException  If {@code username} is null
     * @throws UsernameNotFoundException if a user with {@code username} does not exist in the platform
     */

    @Override
    public List<String> getActivityLog(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null!");
        } else if (!users.contains(username)) {
            throw new UsernameNotFoundException("There is no user with this username in the platform");
        } else {
            List<String> temp = activityByUser.get(username);
            Collections.reverse(temp);
            return temp;
        }
    }

    private String getFormatedDate(LocalDateTime ldt) {
        return String.format("%02d:%02d:%02d %02d.%02d.%d:", ldt.getHour(), ldt.getMinute(), ldt.getSecond(),
                ldt.getDayOfMonth(), ldt.getMonthValue(), ldt.getYear());
    }
}
