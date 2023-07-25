import java.util.HashMap;

public class hw6 {
    public static void main(String[] args) {
        HomeWork mySet = new HomeWork();
        mySet.add(123);
        mySet.add(234);
        mySet.add(345);
        mySet.add(456);
        mySet.add(456);
        mySet.add(456);

        System.out.println(mySet.toString());
        System.out.println(mySet.elemIndex(0));
        System.out.println(mySet.isEmpty());
        System.out.println(mySet.remove(456));
    }
}

class HomeWork {
    private static HashMap<Integer, Object> newSet = new HashMap<>();
    private static Object OBJ = new Object();

    public boolean add(Integer num) {
        return newSet.put(num, OBJ) == null;
    }

    public String toString() {
        return newSet.keySet().toString();
    }

    public int elemIndex(int index) {
        return (Integer) newSet.keySet().toArray()[index];
    }

    public boolean isEmpty() {
        return newSet.isEmpty();
    }

    public boolean remove(Integer num) {
        return newSet.remove(num) == OBJ;
    }
}
