package tested

import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Integer.max

class KnapsackTest {
        @Test
        fun test1() {
                val prices = intArrayOf(60, 100, 120)
                val weights = intArrayOf(1, 2, 3)
                assertEquals(0, knapsack(prices, weights, 0))
                assertEquals(60, knapsack(prices, weights, 1))
                assertEquals(100, knapsack(prices, weights, 2))
                assertEquals(160, knapsack(prices, weights, 3))
                assertEquals(180, knapsack(prices, weights, 4))
                assertEquals(220, knapsack(prices, weights, 5))
                assertEquals(280, knapsack(prices, weights, 6))
                for (strength in 7..1000) {
                        assertEquals(280, knapsack(prices, weights, strength))
                }
        }
}

fun knapsack(
        vs: IntArray,
        ws: IntArray,
        s: Int,
): Int {
        val n = vs.size
        val dp = Array(n + 1) { Array(s + 1) { 0 } }
        for (i in 1..n) {
                for (w in 1..s) {
                        if (ws[i - 1] <= w) {
                                dp[i][w] = max(vs[i - 1] + dp[i - 1][w - ws[i - 1]], dp[i - 1][w])
                                continue
                        }
                        dp[i][w] = dp[i - 1][w]
                }
        }
        return dp[n][s]
}