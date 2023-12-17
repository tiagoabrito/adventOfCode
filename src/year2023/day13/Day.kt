package year2023.day13

import year2023.solveIt


fun main() {
    val day = "13"

    val expectedTest1 = 709L
    val expectedTest2 = 1400L

    fun getReflections(lines: List<String>): List<Int> {
        val repeated = lines.zipWithNext().mapIndexedNotNull { index, pair -> if (pair.first == pair.second) index else null }
        val map = repeated.map { r -> r to ((0 until r).map { it to r + (r - it) + 1 }.filter { it.second < lines.size }) }
        return map.filter { possible -> possible.second.all { lines[it.first] == lines[it.second] } }.map { it.first }
    }

    fun getColumnReflection(map: List<String>, original: Int = -1): Int? {
        if(map == listOf(
                "#..####.##.##",
        ".##.##.####.#",
        "#######.##.##",
        "####.........",
        "#..##..####..",
        "....##.#..#.#",
        ".##..#.####.#",
        "....#.#....#.",
        ".....##....##",
        ".##.##.#..#.#",
        "#..##..#..#..",
        "####.######.#",
        ".....#.####.#",
        ".##.##......#",
        "#..##........"
        )){
            println()
        }
        val transpose = map[0].indices.map { idx -> map.map { l -> l[idx] }.joinToString("") }
        val filter = getReflections(transpose)
        return filter.map { it.inc() }.firstOrNull { it != original }
    }

    fun getRowReflection(lines: List<String>, original: Int = -1): Int? {
        val filter = getReflections(lines)
        return filter.map { it.inc() }.firstOrNull { it != original }
    }

    fun part1(input: List<String>): Long {
        val items = input.joinToString("\n").split("\n\n").map { it.split("\n") }


        val map = items.map { (getColumnReflection(it) ?: (getRowReflection(it)?.let { r -> r * 100 } ?: 0)).toLong() }
        return map.sum()
    }

    fun possibleSmugs(it: List<String>): Sequence<List<String>> = sequence {
        for (i in it.indices) {
            for (j in it[i].indices) {
                val changedChar = when (it[i][j]) {
                    '#' -> '.'
                    else -> '#'
                }
                val changedLine = it[i].substring(0, j) + changedChar + it[i].substring(j + 1, it[i].length)
                val value = it.subList(0, i) + changedLine + it.subList(i + 1, it.size)
                yield(value)
            }
        }
    }

    fun part2(input: List<String>): Long {
        val items = input.joinToString("\n").split("\n\n").map { it.split("\n") }

        return items.mapNotNull { l ->
            l.forEach { println(it) }
            val original = getColumnReflection(l) ?: getRowReflection(l)?.let { r -> r * 100 } ?: -1
            val firstOrNull = possibleSmugs(l)
                .mapNotNull { s -> getColumnReflection(s, original) ?: getRowReflection(s, original / 100)?.let { r -> r * 100 } }
                .filter { it != original }
                .firstOrNull()
            println(firstOrNull)
            println()
            firstOrNull
        }.sum().toLong()
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
    //>26148
}




