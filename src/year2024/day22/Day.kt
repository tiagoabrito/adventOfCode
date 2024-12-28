package year2024.day22

import year2024.solveIt

fun main() {
    val day = "22"


    val expectedTest1 = 37327623L
    val expectedTest2 = 23

    val pruneMask = 16777216 - 1
    fun Int.prune() = and(pruneMask)
    infix fun Int.mix(other: Int) = xor(other)

    fun compute(value: Int): Int {
        val res = ((value * 64) mix value).prune()
        val res2 = ((res / 32) mix res).prune()
        return ((res2 * 2048) mix res2).prune()
    }

    fun computeIt(value: Int, times: Int): Long {
        var v = value
        repeat(times) {
            v = compute(v)
        }
        return v.toLong()
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { computeIt(it.toInt(), 2000) }
    }


    fun getBananaSequence(it: String): List<Int> =
        (1..2000).fold(listOf(it.toInt())) { acc: List<Int>, _: Int -> acc + compute(acc.last()) }.map { v -> v % 10 }

    fun part2(input: List<String>): Int {

        val map = input.map { secret ->
            val seq = getBananaSequence(secret)
            val dif = seq.zip(seq.drop(1))

            dif.windowed(4).map { window -> window.map { it.second - it.first } to window.last().second }.distinctBy { it.first }
        }

        val mapValues: Map<List<Int>, Int> = map.flatten().groupBy( { it.first }, {it.second }).mapValues { entry -> entry.value.sum() }

        return mapValues.asSequence().maxBy { it.value }.value


    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test2")

}

