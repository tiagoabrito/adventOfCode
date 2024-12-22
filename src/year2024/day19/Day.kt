package year2024.day19

import year2024.solveIt


fun main() {
    val day = "19"
    val expectedTest1 = 6
    val expectedTest2 = 16L


    fun countPatterns(towel: String, patterns: List<String>): Long {
        val possibleCombinationsToReachSolution = LongArray(towel.length + 1)
        possibleCombinationsToReachSolution[0] = 1;

        return (0..towel.length).fold(possibleCombinationsToReachSolution) { dp, currIdx ->
            if (dp[currIdx] > 0) {
                patterns
                    .filter { currIdx + it.length <= towel.length }
                    .filter { towel.substring(currIdx, currIdx + it.length) == it }
                    .forEach {
                        dp[currIdx + it.length] += dp[currIdx]
                    }
            }
            dp
        }.last()
    }

    fun getPossiblePatterns(input: List<String>): List<Long> {
        val patterns = input[0].split(", ").toList()
        val towels = input.drop(2)

        val possiblePatterns = towels.map { countPatterns(it, patterns) }
        return possiblePatterns
    }

    fun part1(input: List<String>): Int {
        return getPossiblePatterns(input).count { it > 0 }
    }

    fun part2(input: List<String>): Long {
        return getPossiblePatterns(input).sum()
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}