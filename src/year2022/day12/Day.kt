package year2022.day12

import readInput


fun main() {
    val day = "12"
    val expectedTest1 = 31
    val expectedTest2 = 29


    fun getPosition(input: List<CharSequence>, positionToFind: Char) =
        input.mapIndexed { index, charSequence -> charSequence.indexOf(positionToFind) to index }.first { it.first > -1 }

    fun getPositions(input: List<CharSequence>, positionToFind: Char) =
        input.mapIndexed { index, charSequence -> charSequence.indexOf(positionToFind) to index }.filter { it.first > -1 }.toSet()

    fun availableMoves(position: Pair<Int, Int>, map: List<List<Int>>, start: Pair<Int, Int>, end: Pair<Int, Int>): Sequence<Pair<Int, Int>> {
        val sequenceOf = sequenceOf(
            position.first + 1 to position.second,
            position.first - 1 to position.second,
            position.first to position.second + 1,
            position.first to position.second - 1,
        )
        return sequenceOf.filter { it.first >= 0 && it.second >= 0 && it.first < map[0].size && it.second < map.size }
            .filter { ((map[position.second][position.first] - map[it.second][it.first]) <= 1) }
    }

    fun getMap(input: List<CharSequence>) = input.map { line ->
        line.map {
            when (it) {
                'S' -> 0
                'E' -> 'z' - 'a'
                else -> it - 'a'
            }
        }
    }

    fun searchIt(input: List<CharSequence>, startPositions: Set<Pair<Int, Int>>): Int {
        val endPosition = getPosition(input, 'E')
        val map = getMap(input)
        val visited = mutableSetOf<Pair<Int, Int>>()
        val deque = ArrayDeque(listOf(endPosition to 0))
        while (deque.isNotEmpty()) {
            val current = deque.removeFirst()
            if (startPositions.contains(current.first)) {
                return current.second
            }

            availableMoves(current.first, map, endPosition, endPosition)
                .filterNot { visited.contains(it) }
                .forEach {
                    deque.addLast(it to current.second + 1)
                    visited.add(it)
                }
        }
        return -1
    }

    fun part1(input: List<CharSequence>): Int {
        return searchIt(input, setOf(getPosition(input, 'S')))
    }


    fun part2(input: List<String>): Int {
        return searchIt(input, getPositions(input, 'a') + getPosition(input, 'S'))
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




