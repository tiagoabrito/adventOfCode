package year2023.day03

import readInput

fun main() {
    val day = "03"

    val expectedTest1 = 4361
    val expected = 527364
    val expectedTest2 = 467835

    data class Piece(val l:Int, val c:Int, val len:Int, val id:Int)


    fun getParts(input: List<String>) = input.flatMapIndexed { lNumber, line ->
        line.mapIndexedNotNull { index, c ->
            if (c.isDigit() && (index == 0 || !line[index - 1].isDigit())) {
                val number = line.substring(index).takeWhile { it.isDigit() }
                Piece(lNumber, index, number.length, number.toInt())
            } else
                null
        }
    }

    fun part1(input: List<String>): Int {
        val parts = getParts(input)
        val mapIndexed = parts.filter { partNumber ->
            input.subList(maxOf(0, partNumber.l - 1), minOf(input.size, partNumber.l + 2)).flatMap { l ->
                    l.substring(
                        maxOf(0, partNumber.c - 1), minOf(l.length, partNumber.c + partNumber.len + 1)
                    ).toList()
                }.any { c -> !c.isDigit() && c != '.' }
        }.map { it.id }
        return mapIndexed.sum()
    }


    fun part2(input: List<String>): Int {
        val parts = getParts(input)
        val asterisks = input.flatMapIndexed { l, line ->
            line.mapIndexedNotNull { c, v ->
                when (v == '*') {
                    true -> l to c
                    false -> null
                }
            }.toList()
        }
        return asterisks.map { a ->
            parts.filter { it.l in (a.first - 1)..(a.first + 1) }.filter { it.c in (a.second - it.len)..(a.second + 1) }
        }.filter { it.size == 2 }.sumOf { it[0].id * it[1].id }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2023/day$day/test")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }

    val input = readInput("year2023/day$day/input")
    val message = part1(input)
    println(message)
    println(message - expected)
    check(message == expected) { "expected $expected but was $message" }

    val part2Test = part2(testInput)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    println(part2(input))
}




