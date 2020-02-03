package ca;

import ca.model.Pair;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        Pair pair = new Pair(2, 3);
        Pair pairr = new Pair(4, 5);

        Map<Pair, Integer> map = new HashMap<>();

        map.put(pair, 1);
        map.put(pairr, 2);
        System.out.println(map.get(new Pair(2, 3)));
        System.out.println(map.get(new Pair(4, 5)));
        System.out.println(map.get(new Pair(6, 7)));
    }
}
