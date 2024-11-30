package year2023.day23

import kotlin.math.max
import year2023.solveIt


fun main() {
    val day = "23"

    val expectedTest1 = 94L
    val expectedTest2 = 154L

    data class Movement(val x: Int, val y: Int, val d: Char)
    data class Position(val x: Int, val y: Int)

    data class Path(val movement: Movement, val visited: List<Movement>)

    fun validPaths(input: List<String>, stepUnbounded: Boolean): Path {
        val start = Movement(input[0].indexOf('.'), 0, 'D')
        val end = Movement(input[input.size - 1].indexOf('.'), input.size - 1, 'D')

        var availablePaths: List<Path> = mutableListOf((Path(start, listOf(start))))

        var possiblePaths = availablePaths[0]
        val deadends = mutableListOf<Movement>()

        while (availablePaths.isNotEmpty()) {
            val toList = availablePaths.stream().parallel().map { path ->
                if (path.movement == end) {
                    path to listOf()
                } else {

                    val pos = path.movement
                    val nextPaths = listOf(
                        Movement(pos.x - 1, pos.y, 'L'),
                        Movement(pos.x + 1, pos.y, 'R'),
                        Movement(pos.x, pos.y - 1, 'U'),
                        Movement(pos.x, pos.y + 1, 'D')
                    ).asSequence().filter { it.x in input[0].indices && it.y in input.indices }.filter { input[it.y][it.x] != '#' }.filter {
                        stepUnbounded || when (input[it.y][it.x]) {
                            '<' -> it.d != 'R'
                            '>' -> it.d != 'L'
                            'v' -> it.d != 'U'
                            else -> true
                        }
                    }.filter { path.visited.none { v -> it.x == v.x && it.y == v.y } }.map { Path(it, listOf(it) + path.visited) }.toList()
                    if (nextPaths.isEmpty()) {
                        val deadenedIndex = path.visited.indexOfFirst { v ->
                            listOf(
                                Movement(v.x - 1, v.y, 'L'), Movement(v.x + 1, v.y, 'R'), Movement(v.x, v.y - 1, 'U'), Movement(v.x, v.y + 1, 'D')
                            ).filter { it.x in input[0].indices && it.y in input.indices }.count { input[it.y][it.x] != '#' } >= 3
                        } - 1
                        val position = path.visited[deadenedIndex]
                        deadends.add(position)
                    }

                    null to nextPaths
                }
            }.toList()
            availablePaths = toList.flatMap { it.second }
            possiblePaths = (listOf(possiblePaths) + toList.mapNotNull { it.first }).maxBy { it.visited.size }
        }

        return possiblePaths
    }

    fun part1(input: List<String>): Long {
        val possiblePaths = validPaths(input, false)

        return possiblePaths.visited.size - 1L
    }


    fun getConnectionPoints(input: List<String>, start: Position, end: Position) =
        (input.flatMapIndexed { y: Int, l: String -> l.mapIndexed { x, c -> Position(x, y) to c } }
            .filter { it.second != '#' }
            .map { it.first }
            .filter { v ->
                listOf(
                    Position(v.x - 1, v.y),
                    Position(v.x + 1, v.y),
                    Position(v.x, v.y - 1),
                    Position(v.x, v.y + 1)
                )
                    .filter { it.x in input[0].indices && it.y in input.indices }
                    .count { input[it.y][it.x] != '#' } > 2
            } +
                start +
                end
                ).toSet()


    fun getConnections(p: Position, connectionPoints: Set<Position>, input: List<String>): Map<Position, Int> {

        val queue = ArrayDeque<Pair<Position, Int>>()
        queue.add(p to 0)
        val visited = mutableSetOf(p)
        val connections = mutableMapOf<Position, Int>()

        while (queue.isNotEmpty()) {
            val (current, distance) = queue.removeFirst()
            if (current != p && current in connectionPoints) {
                connections[current] = distance
            } else {
                listOf(
                    Position(current.x - 1, current.y),
                    Position(current.x + 1, current.y),
                    Position(current.x, current.y - 1),
                    Position(current.x, current.y + 1)
                ).filter { it.x in input[0].indices && it.y in input.indices }
                    .filter { input[it.y][it.x] != '#' }
                    .filter { visited.add(it) }
                    .forEach { queue.add(it to distance + 1) }
            }
        }
        return connections
    }

    fun part2(input: List<String>): Long {
        val start = Position(input[0].indexOf('.'), 0)
        val end = Position(input[input.size - 1].indexOf('.'), input.size - 1)
        val connectionPoints = getConnectionPoints(
            input,
            start,
            end
        )
        val nextToIt = connectionPoints.associateWith { p -> getConnections(p, connectionPoints, input) }


        var best = 0
        val visited = mutableSetOf<Position>()
        fun navigate(position: Position, steps: Int): Int {
            if (position == end) {
                best = max(steps, best)
                return best
            }
            visited += position
            nextToIt[position]
                ?.filter { (p, _) -> p !in visited }
                ?.forEach { (place, distance) -> navigate(place, distance + steps) }
            visited -= position
            return best
        }
        return navigate(start, 0).toLong()
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




