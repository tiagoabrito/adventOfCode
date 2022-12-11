package year2022.day11

import readInput


fun main() {
    val day = "11"
    val expectedTest1 = 10605L
    val expectedTest2 = 2713310158L


    data class Monkey(
        var items: List<Long>,
        val operType: (Long) -> Long,
        val testDivisor: Long,
        val destinationDivisible: Long,
        val destinationNotDivisible: Long,
        val processed: Long
    ) {
        fun calculateDestinations(worryLevelDecreaser: Long, mmc: Long): Map<Long, List<Long>> {
            return items.map {
                val newWorryLevel = (operType(it) / worryLevelDecreaser) % mmc
                when (newWorryLevel % testDivisor) {
                    0L -> destinationDivisible
                    else -> destinationNotDivisible
                } to newWorryLevel
            }.groupBy({ it.first }, { it.second })
        }
    }


    fun toFunction(it: List<String>): (Long) -> Long {
        return when (it[it.size - 2]) {
            "*" -> { a: Long -> a * (it.last().toLongOrNull() ?: a) }
            "/" -> { a: Long -> a / (it.last().toLongOrNull() ?: a) }
            "+" -> { a: Long -> a + (it.last().toLongOrNull() ?: a) }
            "-" -> { a: Long -> a - (it.last().toLongOrNull() ?: a) }
            else -> { _ -> throw IllegalArgumentException() }
        }
    }

    fun geOriginalState(input: List<String>): Map<Long, Monkey> {
        val originalMonkeys = input.chunked(7).mapIndexed { idx, chunk ->
            idx * 1L to
                    Monkey(
                        items = chunk[1].split(":", ",", " ").mapNotNull { it.toLongOrNull() },
                        operType = toFunction(chunk[2].split(" ")),
                        testDivisor = chunk[3].split(" ").last().toLong(),
                        destinationDivisible = chunk[4].split(" ").last().toLong(),
                        destinationNotDivisible = chunk[5].split(" ").last().toLong(),
                        processed = 0
                    )
        }.toMap()
        return originalMonkeys
    }

    fun getIt(input: List<String>, worryDecrease: Long, rounds: Int): Long {
        val originalMonkeys = geOriginalState(input)

        val mmc = originalMonkeys.map { it.value.testDivisor }.reduce { a, b -> a * b }

        return List(rounds) { List(originalMonkeys.size) { it * 1L } }.asSequence().flatten().fold(originalMonkeys) { acc, i ->
            val destinations = acc[i]?.calculateDestinations(worryDecrease, mmc) ?: throw IllegalArgumentException()

            acc.map { e ->
                e.key to e.value.copy(
                    items = when (e.key) {
                        i -> listOf()
                        else -> e.value.items + (destinations[e.key] ?: listOf())
                    },
                    processed = e.value.processed + when (e.key) {
                        i -> e.value.items.size
                        else -> 0
                    }
                )
            }.toMap()
        }.map { it.value.processed }
            .sortedDescending()
            .take(2)
            .reduce{ a, b -> a * b }
    }

    fun part1(input: List<String>): Long {
        return getIt(input, 3, 20)
    }


    fun part2(input: List<String>): Long {
        return getIt(input, 1, 10000)
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day$day/test")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2022/day$day/input")
    println(part1(input))

    val part2Test = part2(testInput)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    println(part2(input))
}




