package year2023.day18

import year2023.solveIt


fun main() {
    val day = "18"

    val expectedTest1 = 62L
    val expectedTest2 = 952408144115L

    data class Coordinate(val x: Long, val y: Long) {
        fun add(other: Coordinate): Coordinate = Coordinate(x + other.x, y + other.y)
    }

    fun move(len: Long, direction: Char) = when (direction) {

        'U', '3' -> Coordinate(0, len * -1)
        'D', '1' -> Coordinate(0, len)
        'L', '2' -> Coordinate(len * -1, 0)
        'R', '0' -> Coordinate(len, 0)
        else -> throw Exception()
    }

    fun shoeLace(v: List<Coordinate>) = v.zipWithNext { a, b -> a.x * b.y - a.y * b.x }.sum() / 2L

    fun part1(input: List<String>): Long {
        var perimeter = 0L
        val v = input.scan(Coordinate(0, 0)) { acc, s ->
            val (dir, sz) = s.split(" ")
            val m = sz.toLong()
            perimeter += m
            val move = move(m, dir[0])
            acc.add(move)
        }
        return shoeLace(v) + perimeter / 2 + 1
    }


    fun part2(input: List<String>): Long {
        var perimeter = 0L
        val v = input.scan(Coordinate(0, 0)) { acc, s ->
            val color = s.split(" ")[2]
            val m = color.substring(2, 7).toLong(radix = 16)
            perimeter += m
            val move = move(m, color[7])
            acc.add(move)
        }
        return shoeLace(v) + perimeter / 2 + 1
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




