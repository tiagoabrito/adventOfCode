package year2024.day04

import year2024.solveIt

data class Coordinate(val y: Int, val x: Int)

enum class Direction {
    RIGHT, LEFT, UP, DOWN, UL, UR, DL, DR
}

fun main() {
    val day = "04"

    val expectedTest1 = 18
    val expectedTest2 = 9


    fun boundsRight(start: Coordinate, matrix: List<String>, word: String) = start.x + word.length <= matrix[start.y].length
    fun boundsLeft(start: Coordinate, word: String) = word.length <= start.x + 1
    fun boundsUp(start: Coordinate, word: String) = word.length <= start.y + 1
    fun boundsDown(start: Coordinate, matrix: List<String>, word: String) = matrix.size - word.length >= start.y

    fun isXmas(matrix: List<String>, start: Coordinate, direction: Direction): Boolean {
        val word = "XMAS"
        return when (direction) {
            Direction.RIGHT -> word.indices.all { matrix[start.y][start.x + it] == word[it] }
            Direction.LEFT -> word.indices.all { matrix[start.y][start.x - it] == word[it] }
            Direction.UP -> word.indices.all { matrix[start.y - it][start.x] == word[it] }
            Direction.DOWN -> word.indices.all { matrix[start.y + it][start.x] == word[it] }
            Direction.UL -> word.indices.all { matrix[start.y - it][start.x - it] == word[it] }
            Direction.UR -> word.indices.all { matrix[start.y - it][start.x + it] == word[it] }
            Direction.DL -> word.indices.all { matrix[start.y + it][start.x - it] == word[it] }
            Direction.DR -> word.indices.all { matrix[start.y + it][start.x + it] == word[it] }
        }
    }

    fun checkBound(matrix: List<String>, start: Coordinate, direction: Direction): Boolean {
        val word = "XMAS"
        return when (direction) {
            Direction.RIGHT -> boundsRight(start, matrix, word)
            Direction.LEFT -> boundsLeft(start, word)
            Direction.UP -> boundsUp(start, word)
            Direction.DOWN -> boundsDown(start, matrix, word)
            Direction.UL -> boundsUp(start, word) && boundsLeft(start, word)
            Direction.UR -> boundsUp(start, word) && boundsRight(start, matrix, word)
            Direction.DL -> boundsDown(start, matrix, word) && boundsLeft(start, word)
            Direction.DR -> boundsDown(start, matrix, word) && boundsRight(start, matrix, word)
        }
    }

    fun part1(input: List<String>): Int {
        val coordinates: List<Coordinate> = input.indices.flatMap { i -> input[i].indices.map { Coordinate(i, it) } }

        val possibleStarts = coordinates.flatMap { c -> Direction.values().map { c to it } }
        val boundedStarts = possibleStarts.filter { checkBound(input, it.first, it.second) }
        return boundedStarts.count { isXmas(input, it.first, it.second) }
    }


    fun checkDiagonalDescending(input: List<String>, it: Coordinate) =
        input[it.y - 1][it.x - 1] == 'M' && input[it.y + 1][it.x + 1] == 'S' || input[it.y - 1][it.x - 1] == 'S' && input[it.y + 1][it.x + 1] == 'M'

    fun checkDiagonalAscending(input: List<String>, it: Coordinate) =
        input[it.y + 1][it.x - 1] == 'M' && input[it.y - 1][it.x + 1] == 'S' || input[it.y + 1][it.x - 1] == 'S' && input[it.y - 1][it.x + 1] == 'M'

    fun part2(input: List<String>): Int {
        val coordinates = (1 until input.size - 1).flatMap { l -> (1 until input[l].length - 1).map { Coordinate(l, it) } }

        return coordinates.filter { input[it.y][it.x] == 'A' }.count { checkDiagonalDescending(input, it) && checkDiagonalAscending(input, it) }
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test2")
}