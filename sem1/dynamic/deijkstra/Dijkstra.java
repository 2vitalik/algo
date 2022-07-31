import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * @author Mykytchuk Vladislav
 * @link helpful wiki information: <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">...</a>
 */
class Dijkstra {
    /**
     *
     * @param graph weighted graph given as int[][] matrix
     * @return distances from 0 node to 1..n-1 nodes given as int[]
     */
    public static int[] apply(int[][] graph) {
        int n = graph.length;
        int[] weights = new int[n];
        Arrays.fill(weights, -1);
        weights[0] = 0;
        boolean[] visited = new boolean[n];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.addLast(0);
        while (!queue.isEmpty()) {
            int v = queue.pop();
            int minDistance = -1;
            int minU = -1;
            for (int u = 0; u < n; u++) {
                if ((graph[v][u] == -1) || (visited[u])) {
                    continue;
                }
                int currentDistance = weights[v] + graph[v][u];
                if ((weights[u] == -1) || (weights[u] > currentDistance)) {
                    weights[u] = currentDistance;
                }
                if ((minDistance == -1) || (minDistance > currentDistance)) {
                    minDistance = currentDistance;
                    minU = u;
                }
            }
            if (minU != -1) {
                queue.addLast(minU);
            }
            visited[v] = true;
        }
        return weights;
    }

    public static void main(String[] args) {
        DijkstraTest.test();
    }
}

class DijkstraTest {
    /**
     * @link input data of test: <a href="https://upload.wikimedia.org/wikipedia/commons/5/57/Dijkstra_Animation.gif">...</a>
     */
    public static void test() {
        TestChecker testChecker = new TestChecker(Dijkstra.class);
        Object[][] testCases = {
                {
                        new int[][] {
                                { 0, 7, 9, -1, -1, 14 },
                                { 7, 0, 10, 15, -1, -1 },
                                { 9, 10, 0, 11, -1, 2 },
                                { -1, 15, 11, 0, 6, -1 },
                                { -1, -1, -1, 6, 0, 9 },
                                { 14, -1, 2, -1, 9, 0 },},
                        new int[] { 0, 7, 9, 20, 20, 11 },
                        "dijkstra({\n" +
                                "{ 0, 7, 9, -1, -1, 14 },\n" +
                                "{ 7, 0, 10, 15, -1, -1 },\n" +
                                "{ 9, 10, 0, 11, -1, 2 },\n" +
                                "{ -1, 15, 11, 0, 6, -1 },\n" +
                                "{ -1, -1, -1, 6, 0, 9 },\n" +
                                "{ 14, -1, 2, -1, 9, 0 },}) == { 0, 7, 9, 20, 20, 11 }" },
        };
        int passed = 0;
        int error = 0;
        for (Object[] testCase : testCases) {
            try {
                int[] expected = (int[]) testCase[1];
                int[] actual = Dijkstra.apply((int[][]) testCase[0]);
                String testName = (String) testCase[2];
                testChecker.check((Arrays.equals(expected, actual)), testName);
                System.out.printf("%s passed\n", testCase[2]);
                passed++;
            } catch (TestNotPassedException e) {
                System.out.println(e.getMessage());
                error++;
            }
        }
        System.out.printf("Tests: passed: %d, error: %d\n", passed, error);
    }

    class TestNotPassedException extends RuntimeException {
        public TestNotPassedException(String name, Class<?> clazz) {
            super(String.format("Test in class \"%s\" named \"%s\" not passed", clazz.getName(), name));
        }
    }

    class TestChecker {
        private final Class<?> clazz;

        public TestChecker(Class<?> clazz) {
            this.clazz = clazz;
        }

        public void check(boolean condition, String testName) {
            if (!condition) {
                throw new TestNotPassedException(testName, clazz);
            }
        }
    }

}

