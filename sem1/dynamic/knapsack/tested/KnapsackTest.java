package tested;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KnapsackTest {
    @Test
    public void test1() {
        int[] prices = { 60, 100, 120 };
        int[] weights = { 1, 2, 3 };
        assertEquals(0, Knapsack.apply(prices, weights, 0));
        assertEquals(60, Knapsack.apply(prices, weights, 1));
        assertEquals(100, Knapsack.apply(prices, weights, 2));
        assertEquals(160, Knapsack.apply(prices, weights, 3));
        assertEquals(180, Knapsack.apply(prices, weights, 4));
        assertEquals(220, Knapsack.apply(prices, weights, 5));
        assertEquals(280, Knapsack.apply(prices, weights, 6));
        for (int strength = 7; strength <= 1000; strength++) {
            assertEquals(280, Knapsack.apply(prices, weights, strength));
        }
    }
}

class Knapsack {

    private Knapsack() {}

    private static int loopImpl(
            final int[] vs,
            final int[] ws,
            final int S
    ) {
        final int N = vs.length;
        int[][] dp = new int[N + 1][S + 1];
        for (int i = 1; i <= N; i++) {
            for (int w = 1; w <= S; w++) {
                if (ws[i - 1] <= w) {
                    dp[i][w] = Math.max(vs[i - 1] + dp[i - 1][w - ws[i - 1]], dp[i - 1][w]);
                    continue;
                }
                dp[i][w] = dp[i - 1][w];
            }
        }
        return dp[N][S];
    }

    public static int apply(
            final int[] prices,
            final int[] weights,
            final int strength
    ) {
        return loopImpl(prices, weights, strength);
    }

}
