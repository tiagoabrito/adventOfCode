package year2024.day25

import year2024.solveIt


fun main() {
    val day = "25"
    val expectedTest1 = 3
    val expectedTest2 = 0L


    fun countChar(key: List<String>, toCount: Char): List<Int> {
        val map = key[0].indices.map { idx -> key.count { it[idx] == toCount } }
        return map
    }

    fun getSchematics(input: List<String>) = input.chunked(8).map { it.filter { it.isNotEmpty() } }

    fun getKeys(input: List<String>) = getSchematics(input).mapNotNull {
        if (it[0][0] == '.') {
            countChar(it, '.').map { d -> it.size - d - 1 }
        } else null
    }

    fun getLocks(input: List<String>) = getSchematics(input).mapNotNull {
        if (it[0][0] == '#') {
            countChar(it, '#').map { d -> d - 1 }
        } else null
    }

    fun part1(input: List<String>): Int {
        val keys = getKeys(input)
        val locks = getLocks(input)


        val flatMap = locks.flatMap { l -> keys.map { l to it } }
        return flatMap.count { it.first.indices.all { idx -> (6 - it.first[idx]) > it.second[idx] } }
    }


    fun part2(input: List<String>): Long {
        return 0


    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}