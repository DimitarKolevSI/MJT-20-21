package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class EvilSocialInatorTest {

    SocialMediaInator evilSocialInator;

    @Before
    public void setUp() {
        evilSocialInator = new EvilSocialInator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForRegisterWhenPassingNull() {
        evilSocialInator.register(null);
    }

    @Test(expected = UsernameAlreadyExistsException.class)
    public void testForAddingTheSameUsernameTwice() {
        String username = "dkolev";
        evilSocialInator.register(username);
        evilSocialInator.register(username);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForPublishingPostWithUsernameNull() {
        evilSocialInator.publishPost(null, LocalDateTime.now(), "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForPublishingPostWithLocalDateTimeNull() {
        evilSocialInator.publishPost("dkolev", null, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForPublishingPostWithDescriptionNull() {
        evilSocialInator.publishPost("dkolev", LocalDateTime.now(), null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testForPublishingPostWhenThereIsNoSuchUser() {
        evilSocialInator.register("dkolev");

        evilSocialInator.publishPost("dimitar", LocalDateTime.now(), "description");
    }

    @Test
    public void testForPublishingPostWhenEverythingIsPresent() {
        evilSocialInator.register("dkolev");
        String id = evilSocialInator.publishPost("dkolev", LocalDateTime.now(), "First post!");

        assertEquals("dkolev-0", id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForLikingPostWhenUsernameIsNull() {
        evilSocialInator.like(null, "id");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForLikingPostWhenIdIsNull() {
        evilSocialInator.like("dkolev", null);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testForLikingPostWhenThereIsNoSuchUser() {
        evilSocialInator.like("dkolev", "id");
    }

    @Test(expected = ContentNotFoundException.class)
    public void testForLikingPostWhenThereIsNoSuchContent() {
        evilSocialInator.register("dkolev");
        evilSocialInator.like("dkolev", "id");
    }

    @Test
    public void testForLikingPostWhenEverything() {
        evilSocialInator.register("dkolev");
        String id = evilSocialInator.publishPost("dkolev",LocalDateTime.now(),"First post");

        assertEquals(1,evilSocialInator.getActivityLog("dkolev").size());

        evilSocialInator.like("dkolev",id);

        assertEquals(2,evilSocialInator.getActivityLog("dkolev").size());
    }


}