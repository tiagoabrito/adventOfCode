package year2024.day14

import year2024.solveIt

fun main() {
    val day = "14"

    val expectedTest1 = 21L
    val expectedTest2 = 0L

    fun readPart(l: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val parts = l.split(" ")
        val position = parts[0].substringAfter("p=").split(",").map { it.toInt() } // Extract and parse p values
        val velocity = parts[1].substringAfter("v=").split(",").map { it.toInt() }
        return (position[1] to position[0]) to (velocity[1] to velocity[0])
    }

    fun getQuadrants(input: List<String>, boundsX: Int, boundsY: Int, seconds: Int): Map<Int, List<Pair<Int, Int>>> {
        val middleX = boundsX / 2
        val middleY = boundsY / 2
        return input.map { l ->
            val (p,v) = readPart(l)
            val endY = ((v.first * seconds + p.first) % boundsY + boundsY) % boundsY
            val endX = ((v.second * seconds + p.second) % boundsX + boundsX) % boundsX
            endY to endX
        }.groupBy {
            when {
                it.second < middleX && it.first < middleY -> 1
                it.second > middleX && it.first < middleY -> 2
                it.second < middleX && it.first > middleY -> 3
                it.second > middleX && it.first > middleY -> 4
                else -> 0
            }
        }
    }

    fun part1(input: List<String>): Long {
        val quadrants = getQuadrants(input, 101, 103, 100)
        return quadrants.filter { it.key != 0 }.map { it.value.size }.fold(1) { acc, i -> acc * i }
    }




    fun part2(input: List<String>): Long {
        val boundsX = 101
        val boundsY = 103

        val parts: List<Pair<Pair<Int, Int>, Pair<Int, Int>>> = input.map { l -> readPart(l) }

        (0..10000).forEach { s ->
            val currentPositions = parts.map { (p, v) ->
                val endY = ((v.first * s + p.first) % boundsY + boundsY) % boundsY
                val endX = ((v.second * s + p.second) % boundsX + boundsX) % boundsX
                endY to endX
            }.toSet()

            val mapIndexed: List<String> = List(boundsY) { y ->
                List(boundsX) { x ->
                    if (currentPositions.contains(y to x)) {
                        'X'
                    } else
                        '.'
                }.joinToString("")
            }

            if (mapIndexed.any { it.contains("XXXXXXXXX") }) {
                println("$s\n")
                mapIndexed.forEach { println(it) }
                readln()
            }
        }
        return 0
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}