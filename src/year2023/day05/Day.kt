package year2023.day05

import java.util.stream.LongStream
import year2023.solveIt


fun main() {
    val day = "05"

    val expectedTest1 = 35L
    val expectedTest2 = 46L




    fun part1(input: List<String>): Long {
        val map = input.joinToString("\n").split("\n\n").map { it.split("\n") }
        val seeds = Regex("\\d+").findAll(map[0][0]).map { it.value.toLong() }
        val map1 = map.stream().skip(1).map {
            it.stream().skip(1).map { line ->
                val (dest, src, sz) = line.split(" ").map { it.toLong() }
                LongRange(src,src + sz-1) to dest-src
            }.toList()
        }.toList()

        val map2 = seeds.map { seed -> map1.fold(seed) { curr,
                                                         m -> curr + (m.firstOrNull { it.first.contains(curr) }?.second?:0)
        }
        }.toList()
        return map2.min()
    }


    fun part2(input: List<String>): Long {
        val items = input.joinToString("\n").split("\n\n").map { it.split("\n") }
        val seeds = Regex("\\d+").findAll(items[0][0]).map { it.value.toLong() }.chunked(2).sortedBy { it[0] }.map { LongStream.range(it[0], it[0] + it[1] - 1) }
        val ranges = items.drop(1).map {
            it.drop(1).map { line ->
                val (dest, src, sz) = line.split(" ").map { it.toLong() }
                LongRange(src, src + sz - 1) to dest - src
            }.sortedBy { it.first.first }.toList()
        }.toList()

        val map2 = Regex("\\d+").findAll(items[0][0]).map { it.value.toLong() }.chunked(2).sortedBy { it[0] }
            .mapNotNull { LongStream.range(it[0], it[0] + it[1] - 1).parallel().map { seed -> ranges.fold(seed) { curr, range -> curr + (range.firstOrNull { it.first.contains(curr) }?.second ?: 0) } }.min() }.map{it.asLong }.min()
        return map2
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




