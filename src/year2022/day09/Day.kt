package year2022.day09

import java.util.function.Predicate
import kotlin.math.abs
import readInput


fun main() {
    val day = "09"
    val expectedTest1 = 13
    val expectedTest2 = 1

    data class Point(val x: Int, val y: Int) {
        fun isNear(point: Point): Boolean = abs(x - point.x) <= 1 && abs(y - point.y) <= 1

        fun move(dir: Char, conditional: Predicate<Point> = Predicate<Point> { false }): Point {
            return when {
                conditional.test(this) -> this
                else -> when (dir) {
                    'R' -> Point(x + 1, y)
                    'L' -> Point(x - 1, y)
                    'U' -> Point(x, y + 1)
                    'D' -> Point(x, y - 1)
                    else -> throw IllegalArgumentException("invalid dir")
                }
            }
        }

        fun nearer(a: Int, b: Int) =
            when {
                a < b -> a + 1
                a == b -> a
                else -> a - 1
            }

        fun moveNear(conditional: Point) = when {
            this.isNear(conditional) -> this
            else -> Point(nearer(x, conditional.x), nearer(y, conditional.y))
        }
    }


    data class MapIndex(
        val head: List<Point>,
        val tail: Point,
        val visited: Set<Point>,
    )


    fun asMoveSequence(input: List<String>) = input.map { it.split(" ") }.flatMap { l -> l[0].repeat(l[1].toInt()).asSequence() }

    fun part1(input: List<String>): Int {
        val map = asMoveSequence(input).fold(MapIndex(listOf(Point(0, 0)), Point(0, 0), setOf())) { acc, mov ->
            val newHead = listOf(acc.head.last().move(mov))
            val newTail = acc.tail.moveNear(newHead.last())
            MapIndex(newHead, newTail, acc.visited + newTail)
        }
        return map.visited.size
    }

//    0 0 0 0 0 0 0 0 0 1 2

    fun part2(input: List<String>): Int {
        val map = asMoveSequence(input).fold(MapIndex(List(10){Point(0, 0)}, Point(0, 0), setOf())) { acc, mov ->
            val newHead = acc.head.take(9).foldRight(listOf(acc.head.last().move(mov))){
                    i, positions -> listOf( i.moveNear(positions.first())) + positions
            }.takeLast(10)
            val newTail = newHead.first()
            MapIndex(newHead, newTail, acc.visited + newTail)
        }
        return map.visited.size
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day$day/test")
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1)
    { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2022/day$day/input")
    println(part1(input))

    val part2Test = part2(testInput)
    check(part2Test == expectedTest2)
    { "expected $expectedTest2 but was $part2Test" }
    println(part2(input))
}




