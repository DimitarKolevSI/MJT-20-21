package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(final String[] args) {
        Map<Integer,List<Integer>> map = new HashMap<>();
        map.put(1,new ArrayList<>());
        map.get(1).add(12);
        map.get(1).add(13);
        map.get(1).add(14);
        map.get(1).add(15);
        map.get(1).add(16);
        System.out.println(map.get(1));
    }
}
