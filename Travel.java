package AI;

import java.util.*;

public class Travel {
    static Map<String, Integer> timeMap = Map.of(
        "Amogh", 5,
        "Ameya", 10,
        "Grandma", 20,
        "Grandpa", 25
    );

    static class State {
        Set<String> left, right;
        boolean torchLeft;
        int totalTime;
        List<String> steps;

        State(Set<String> left, Set<String> right, boolean torchLeft, int totalTime, List<String> steps) {
            this.left = new HashSet<>(left);
            this.right = new HashSet<>(right);
            this.torchLeft = torchLeft;
            this.totalTime = totalTime;
            this.steps = new ArrayList<>(steps);
        }

        boolean isGoal() {
            return left.isEmpty();
        }

        String encode() {
            List<String> l = new ArrayList<>(left);
            Collections.sort(l);
            return l.toString() + "|" + torchLeft;
        }
    }

    static List<Set<String>> getCombinations(Set<String> people, boolean going) {
        List<Set<String>> result = new ArrayList<>();
        List<String> list = new ArrayList<>(people);

        for (int i = 0; i < list.size(); i++) {
            if (!going) {
                result.add(Set.of(list.get(i))); // one person returns
            } else {
                result.add(Set.of(list.get(i))); // 1 person go
                for (int j = i + 1; j < list.size(); j++) {
                    result.add(Set.of(list.get(i), list.get(j))); // 2 persons go
                }
            }
        }
        return result;
    }

    public static void solve() {
        Set<String> left = new HashSet<>(timeMap.keySet());
        Set<String> right = new HashSet<>();
        Queue<State> queue = new LinkedList<>();
        Map<String, Integer> visited = new HashMap<>();
        int bestTime = Integer.MAX_VALUE;
        List<String> bestSteps = new ArrayList<>();

        queue.add(new State(left, right, true, 0, new ArrayList<>()));

        while (!queue.isEmpty()) {
            State curr = queue.poll();

            if (curr.totalTime > 60) continue;
            if (curr.isGoal()) {
                if (curr.totalTime < bestTime) {
                    bestTime = curr.totalTime;
                    bestSteps = curr.steps;
                }
                continue;
            }

            String key = curr.encode();
            if (visited.containsKey(key) && visited.get(key) <= curr.totalTime) continue;
            visited.put(key, curr.totalTime);

            if (curr.torchLeft) {
                
                for (Set<String> move : getCombinations(curr.left, true)) {
                    Set<String> newLeft = new HashSet<>(curr.left);
                    Set<String> newRight = new HashSet<>(curr.right);
                    newLeft.removeAll(move);
                    newRight.addAll(move);

                    int time = move.stream().mapToInt(p -> timeMap.get(p)).max().getAsInt();
                    List<String> newSteps = new ArrayList<>(curr.steps);
                    newSteps.add("➡️  Cross: " + move + " (" + time + " min)");

                    queue.add(new State(newLeft, newRight, false, curr.totalTime + time, newSteps));
                }
            } else {
               
                for (Set<String> move : getCombinations(curr.right, false)) {
                    Set<String> newLeft = new HashSet<>(curr.left);
                    Set<String> newRight = new HashSet<>(curr.right);
                    newRight.removeAll(move);
                    newLeft.addAll(move);

                    int time = timeMap.get(move.iterator().next());
                    List<String> newSteps = new ArrayList<>(curr.steps);
                    newSteps.add("⬅️  Return: " + move + " (" + time + " min)");

                    queue.add(new State(newLeft, newRight, true, curr.totalTime + time, newSteps));
                }
            }
        }

        if (bestSteps.isEmpty()) {
            System.out.println("❌ No solution within 60 minutes.");
        } else {
            System.out.println("✅ Optimal solution found in " + bestTime + " minutes:");
            for (String step : bestSteps) {
                System.out.println(step);
            }
        }
    }

    public static void main(String[] args) {
    
        solve();
    }
}
