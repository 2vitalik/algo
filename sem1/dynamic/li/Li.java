import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * @author Mykytchuk Vladislav
 */
public class Li {

    private Li() {}

    /**
     *
     * @param destination index of vertex which was marked as T
     * @param distances int array which is calculated during Li wave algorithm
     * @param adjustmentMatrix adjustment matrix of given graph
     * @return vertexes in path between S and T
     */
    private static int[] retrievePath(int destination, int[] distances, int[][] adjustmentMatrix) {
        int n = adjustmentMatrix.length;
        int currentDistance = distances[destination];
        int currentV = destination;
        int[] path = new int[currentDistance - 1];
        for (int step = currentDistance - 1; step > 0; step--) {
            for (int u = 0; u < n; u++) {
                if ((adjustmentMatrix[u][currentV] == 1) && (distances[u] == step)) {
                    path[step - 1] = u;
                    currentV = u;
                    break;
                }
            }
        }
        return path;
    }

    /**
     *
     * @param adjustmentMatrix adjustment matrix of graph
     * @param start index of vertex, marked as S
     * @param destination index of vertex, marked as T
     * @return path from start to destination
     */
    public static int[] apply(int[][] adjustmentMatrix, int start, int destination) {
        int n = adjustmentMatrix.length;
        int[] distances = new int[n];
        Arrays.fill(distances, -1);
        distances[start] = 0;
        Queue<Integer> wave = new ArrayDeque<>();
        wave.add(start);
        int currentDistance = 1;
        while (!wave.isEmpty()) {
            Queue<Integer> nextWave = new ArrayDeque<>();
            while (!wave.isEmpty()) {
                int u = wave.remove();
                for (int v = 0; v < u; v++) {
                    if ((distances[v] != -1) || (adjustmentMatrix[u][v] == -1)) {
                        continue;
                    }
                    distances[v] = currentDistance;
                    if (v == destination) {
                        return retrievePath(destination, distances, adjustmentMatrix);
                    }
                    nextWave.add(v);
                }
                for (int v = u + 1; v < n; v++) {
                    if ((distances[v] != -1) || (adjustmentMatrix[u][v] == -1)) {
                        continue;
                    }
                    distances[v] = currentDistance;
                    if (v == destination) {
                        return retrievePath(destination, distances, adjustmentMatrix);
                    }
                    nextWave.add(v);
                }
            }
            currentDistance++;
            wave = nextWave;
        }
        return null;
    }

    public static void main(String[] args) {
        LiTest.test();
    }
}

class LiTest {
    /**
     *
     * <p>testCases[0] - adjustmentMatrix</p>
     * <p>testCases[1] - start</p>
     * <p>testCases[2] - destination</p>
     * <p>testCases[3] - expected result</p>
     * <p>testCases[4] - name of test</p>
     */
    public static void test() {
        TestChecker testChecker = new TestChecker(Li.class);

        Object[][] testCases = {
                {
                        new int[][] {
                                { 0, -1, 1, -1, 1, -1 },
                                { -1, 0, -1, 1, -1, 1 },
                                { 1, -1, 0, -1, -1, -1 },
                                { 1, -1, 1, 0, -1, -1 },
                                { -1, -1, -1, -1, 0, -1 },
                                { -1, -1, -1, -1, -1, 0 },}, 1, 4,
                        new int[] { 3, 0 },
                        "li({\n" +
                                "{ 0, -1, 1, -1, 1, -1 },\n" +
                                "{ -1, 0, -1, 1, -1, 1 },\n" +
                                "{ 1, -1, 0, -1, -1, -1 },\n" +
                                "{ 1, -1, 1, 0, -1, -1 },\n" +
                                "{ -1, -1, -1, -1, 0, -1 },\n" +
                                "{ -1, -1, -1, -1, -1, 0 },}, 1, 4) == { 3, 0 }" },
                {
                        new int[][] {
                                { 0, -1, 1, -1, 1, -1 },
                                { -1, 0, -1, 1, -1, 1 },
                                { 1, -1, 0, -1, -1, -1 },
                                { 1, -1, 1, 0, -1, -1 },
                                { -1, -1, -1, -1, 0, -1 },
                                { -1, -1, -1, -1, -1, 0 },}, 1, 3,
                        new int[] { },
                        "li({\n" +
                                "{ 0, -1, 1, -1, 1, -1 },\n" +
                                "{ -1, 0, -1, 1, -1, 1 },\n" +
                                "{ 1, -1, 0, -1, -1, -1 },\n" +
                                "{ 1, -1, 1, 0, -1, -1 },\n" +
                                "{ -1, -1, -1, -1, 0, -1 },\n" +
                                "{ -1, -1, -1, -1, -1, 0 },}, 1, 3) == { }" },
                {
                        new int[][] {
                                { 0, -1, 1, -1, 1, -1 },
                                { -1, 0, -1, 1, -1, 1 },
                                { 1, -1, 0, -1, -1, -1 },
                                { 1, -1, 1, 0, -1, -1 },
                                { -1, -1, -1, -1, 0, -1 },
                                { -1, -1, -1, -1, -1, 0 },}, 5, 2,
                        null,
                        "li({\n" +
                                "{ 0, -1, 1, -1, 1, -1 },\n" +
                                "{ -1, 0, -1, 1, -1, 1 },\n" +
                                "{ 1, -1, 0, -1, -1, -1 },\n" +
                                "{ 1, -1, 1, 0, -1, -1 },\n" +
                                "{ -1, -1, -1, -1, 0, -1 },\n" +
                                "{ -1, -1, -1, -1, -1, 0 },}, 5, 2) == null" },
        };
        int passed = 0;
        int error = 0;
        for (Object[] testCase : testCases) {
            try {
                int[] expected = (int[]) testCase[3];
                int[] actual = Li.apply((int[][]) testCase[0], (int) testCase[1], (int) testCase[2]);
                String testName = (String) testCase[4];
                testChecker.check((Arrays.equals(expected, actual)), testName);
                System.out.printf("%s passed\n",testName);
                passed++;
            } catch (TestNotPassedException e) {
                System.out.println(e.getMessage());
                error++;
            }
        }
        System.out.printf("Tests: passed: %d, error: %d\n", passed, error);
    }

    private static class TestNotPassedException extends RuntimeException {
        public TestNotPassedException(String name, Class<?> clazz) {
            super(String.format("Test in class \"%s\" named \"%s\" not passed", clazz.getName(), name));
        }
    }

    private static class TestChecker {
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
