package AI;

import java.util.*;

public class Rabbit {
    static final String START = "EEERWWW";
    static final String GOAL = "WWWREEE";

    public static void main(String[] args) {
        System.out.println("🐇 Rabbit Leap Problem (BFS Solution)");
        solveRabbitLeap();
    }

    static void solveRabbitLeap() {
        Queue<String> queue = new LinkedList<>();
        Map<String, String> parentMap = new HashMap<>();

        queue.add(START);
        parentMap.put(START, null);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            if (current.equals(GOAL)) {
                System.out.println("✅ Solution Found!\n");
                printPath(current, parentMap);
                return;
            }

            for (String next : getNextStates(current)) {
                if (!parentMap.containsKey(next)) {
                    queue.add(next);
                    parentMap.put(next, current);
                }
            }
        }

        System.out.println("❌ No solution found.");
    }

    static void printPath(String state, Map<String, String> parentMap) {
        List<String> path = new ArrayList<>();
        while (state != null) {
            path.add(state);
            state = parentMap.get(state);
        }
        Collections.reverse(path);
        for (String s : path) {
            System.out.println(s);
        }
    }

    static List<String> getNextStates(String state) {
        List<String> nextStates = new ArrayList<>();
        char[] arr = state.toCharArray();
        int empty = state.indexOf('R');

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 'E') {
              
                if (i + 1 == empty) {
                    nextStates.add(swap(arr, i, empty));
                }
               
                if (i + 2 == empty && arr[i + 1] != 'R') {
                    nextStates.add(swap(arr, i, empty));
                }
            } else if (arr[i] == 'W') {
                
                if (i - 1 == empty) {
                    nextStates.add(swap(arr, i, empty));
                }
                
                if (i - 2 == empty && arr[i - 1] != 'R') {
                    nextStates.add(swap(arr, i, empty));
                }
            }
        }
        return nextStates;
    }

    static String swap(char[] arr, int i, int j) {
        char[] newArr = arr.clone();
        char temp = newArr[i];
        newArr[i] = newArr[j];
        newArr[j] = temp;
        return new String(newArr);
    }
}
