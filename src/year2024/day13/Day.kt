package year2024.day13

import year2024.solveIt


fun main() {
    val day = "13"

    val expectedTest1 = 480L
    val expectedTest2 = 875318608908L

    val possibleValues: List<Pair<Int, Int>> = (0..100).flatMap { a -> (0..100).map { a to it } }

    fun getMinimumCostToWin(xa:Int, ya:Int, xb:Int, yb:Int, prizeX: Int, prizeY:Int): Int {
        return possibleValues
          .filter { (a, b) ->
            ((a * xa) + (b * xb) == prizeX) &&
                    ((a * ya + b * yb) == prizeY) }.minOfOrNull { (a, b) -> 3 * a + 1 * b }?:0
    }

    fun getGamePlays(input: List<String>) =
        input.flatMap { line -> line.split(",").map { lp -> lp.filter { it.isDigit() } }.filterNot { it.isEmpty() }.map { it.toInt() } }.chunked(6)

    fun part1(input: List<String>): Long {
        return getGamePlays(input).sumOf { getMinimumCostToWin(it[0], it[1], it[2], it[3], it[4], it[5]) }.toLong()
    }


    fun getMinimumCostToWin(xa: Int, ya: Int, xb: Int, yb: Int, prizeX: Long, prizeY: Long): Long {
        val a = (prizeY * xb - prizeX * yb) / (ya * xb - xa * yb)
        val b = (prizeX * ya - prizeY * xa) / (ya * xb - xa * yb)

        if (a >= 0 && b >= 0 && (a * xa + b * xb == prizeX) && (a * ya + b * yb == prizeY)) {
            return 3 * a + b
        }

        return 0
    }


    fun part2(input: List<String>): Long {
        val conversionError = 10000000000000
        return getGamePlays(input).sumOf { getMinimumCostToWin(it[0], it[1], it[2], it[3], conversionError + it[4], conversionError + it[5]) }.toLong()
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
    //>26148
}




