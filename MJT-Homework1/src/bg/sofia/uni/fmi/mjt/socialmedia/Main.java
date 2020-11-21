package bg.sofia.uni.fmi.mjt.socialmedia;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class Main {

    public static void main(final String[] args) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(1, new ArrayList<>());
        map.get(1).add(12);
        map.get(1).add(13);
        map.get(1).add(14);
        map.get(1).add(15);
        map.get(1).add(16);
        System.out.println(map.get(1));
    }
}
