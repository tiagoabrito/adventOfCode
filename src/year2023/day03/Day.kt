package year2023.day03

import year2023.solveIt

fun main() {
    val day = "03"

    val expectedTest1 = 4361L
    val expectedTest2 = 467835L

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

    fun part1(input: List<String>): Long {
        val parts = getParts(input)
        val mapIndexed = parts.filter { partNumber ->
            input.subList(maxOf(0, partNumber.l - 1), minOf(input.size, partNumber.l + 2)).flatMap { l ->
                    l.substring(
                        maxOf(0, partNumber.c - 1), minOf(l.length, partNumber.c + partNumber.len + 1)
                    ).toList()
                }.any { c -> !c.isDigit() && c != '.' }
        }.map { it.id }
        return mapIndexed.sum().toLong()
    }


    fun part2(input: List<String>): Long {
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
        }.filter { it.size == 2 }.sumOf { it[0].id * it[1].id }.toLong()
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




