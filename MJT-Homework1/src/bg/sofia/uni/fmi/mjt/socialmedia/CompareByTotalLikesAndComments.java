package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;

import java.util.Comparator;

public class CompareByTotalLikesAndComments implements Comparator<Content> {

    @Override
    public int compare(Content o1, Content o2) {
        return (o1.getNumberOfComments() + o1.getNumberOfLikes()) - (o2.getNumberOfComments()) + o2.getNumberOfLikes();
    }
}
