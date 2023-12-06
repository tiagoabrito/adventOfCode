package year2021.day3

import readInput

fun main() {


    fun maxFrequency(eachCount: Map<Char, Int>): Char {
        return eachCount.entries.maxByOrNull { it.value }!!.key
    }

    fun minFrequency(eachCount: Map<Char, Int>): Char {
        return eachCount.entries.minByOrNull { it.value }!!.key
    }

    fun part1(input: List<CharSequence>): Int {


        val values = input.flatMap { it.indices.map { idx -> idx to it[idx] } }
            .groupBy({ it.first }, { it.second })
            .values
        val a = values.map { maxFrequency(it.groupingBy { it }.eachCount()) }.joinToString("").let { Integer.parseInt(it, 2) }
        val b = values.map { minFrequency(it.groupingBy { it }.eachCount()) }.joinToString("").let { Integer.parseInt(it, 2) }
        return a * b
    }


    fun extracted(input: List<String>): Int {
        var f = input
        for (i in 0..input[0].length) {
            f = f.groupBy { it[i] }.toSortedMap(comparator = {a,b -> b-a}).maxByOrNull { entry -> entry.value.size }?.value ?: emptyList()
            if (f.size == 1)
                break
        }
        return Integer.parseInt(f[0], 2)
    }

    fun extracted2(input: List<String>): Int {
        var f = input
        for (i in 0..input[0].length) {
            f = f.groupBy { it[i] }.toSortedMap(comparator = {a,b -> a-b}).minByOrNull { entry -> entry.value.size }?.value ?: emptyList()
            if (f.size == 1)
                break
        }
        return Integer.parseInt(f[0], 2)
    }


    fun part2(input: List<String>): Int {
        return extracted(input) * extracted2(input)


    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2021/Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 198)

    val input = readInput("year2021/Day03")
    println(part1(input))

    check(part2(testInput) == 230)
    println(part2(input))
}
