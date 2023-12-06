package year2022.day03

import readInput

fun main() {
    fun toValue(it: MutableList<Char>): Int {
        return it.distinct().sumOf { if (it > 'a') it - 'a' + 1 else it - 'A' + 27 }
    }

    fun repeated(a: Pair<CharSequence, CharSequence>): MutableList<Char> {
        val myList = a.first.toMutableList()
         myList.retainAll(a.second.toList())
        return myList
    }

    fun part1(input: List<String>): Int {
        return input.map { it.substring(0, it.length / 2 ) to it.substring(it.length / 2) }.map { repeated(it) }.sumOf { toValue(it) }
    }




    fun part2(input: List<String>):Int {
        return input.chunked(3).map {
            repeated(repeated(it[0] to it[1]).joinToString() to it[2])
        }.sumOf { toValue(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    println(part1(input))



    println(part2(input))
}

