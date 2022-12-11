package year2022.day07

import readInput


fun main() {


    fun getDirectorySize(input: List<String>): List<Pair<String, Int>> {
        val v =
            (input + listOf("$ cd \\")).asSequence().mapIndexedNotNull { index, s ->
                when {
                    s.startsWith("$ cd") -> index to s.substring(5)
                    else -> null
                }
            }
                .zipWithNext()
                .map {
                    (it.first.second to it.second.second) to
                            input.subList(it.first.first, it.second.first)
                                .mapNotNull { it.split(" ").first().toIntOrNull() }.sum()
                }
                .fold(mutableListOf<Pair<String, Int>>()) { acc, pair ->
                    acc.also {
                        val previous = acc.lastOrNull()?.first ?: ""
                        val actualFolder = when (pair.first.first) {
                            ".." -> previous.substring(0, previous.lastIndexOf("/"))
                            else -> "$previous/${pair.first.first}"
                        }

                        acc.add(actualFolder to pair.second)
                    }
                }.toList().distinctBy { it.first }
        return v.map { item -> item.first to v.filter { it.first.startsWith(item.first) }.sumOf { it.second } }
    }

    fun part1(input: List<String>): Int {
        val map = getDirectorySize(input)

        return map.filter { it.second <= 100000 }.sumOf { it.second }
    }


    fun part2(input: List<String>): Int {
        val sz = getDirectorySize(input)
        val directorySize = sz.sortedByDescending { it.second }

        val needed = 30000000 - (70000000 - directorySize[0].second)
        return directorySize.last { it.second > needed }.second
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day07/test")
    val expectedTest1 = 95437
    val part1Test = part1(testInput)
    check(part1Test == expectedTest1) { "expected $expectedTest1 but was $part1Test" }


    val input = readInput("year2022/day07/input")
    println(part1(input))

    val expectedTest2 = 24933642
    val part2Test = part2(testInput)
    check(part2Test == expectedTest2) { "expected $expectedTest2 but was $part2Test" }
    println(part2(input))
}




