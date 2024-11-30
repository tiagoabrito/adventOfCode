package year2023.day12

import year2023.solveIt


fun main() {
    val day = "12"

    val expectedTest1 = 21L
    val expectedTest2 = 525152L
    /*

    fun getInnerPermutations(list: List<String>, pattern: String): List<List<String>> {
        if (list.isEmpty()) {
            return listOf(emptyList())

        }

        val result: MutableList<List<String>> = mutableListOf()
        for (i in list.indices) {
            val v = list[i]
            val subPattern = pattern.substring(0, v.length)
            if (v.matches(Regex(subPattern))) {
                getInnerPermutations(list.subList(0, i) + list.subList(i + 1, list.size), pattern.substring(list[i].length))
                    .filter { list[i][0]!= '#' || it.isEmpty() || it[0][0] != '#' }
                    .mapTo(result) { listOf(list[i]) + it }
            }
        }
        return result
    }

    fun allPermutations(list: List<String>, pattern: String): List<List<String>> {
        val innerPermutations = getInnerPermutations(list, pattern)
        return innerPermutations
    }

    fun getPermutations(line: String): Long {
        val (layout, groups) = line.split(" ")
        val pattern = layout.replace(".", "D").replace("?", ".")
        val regex = Regex(pattern)
        val brokenSizes = groups.split(",").map { it.toInt() }
        val cardinals = brokenSizes.map { "#".repeat(it) }

        val dotGroups = layout.split("#", "?").filter { it.isNotEmpty() }.map { it.map { "D" }.joinToString("") }

        val additionalDots = List(layout.length - brokenSizes.sum() - dotGroups.sumOf { it.length }) { "D" }
        val stringGroups = cardinals + dotGroups + additionalDots

        val permutations = allPermutations(stringGroups, pattern)
            .asSequence()
            .filter { perm -> perm.filter { it.startsWith("#") } == cardinals }
            .map { it.joinToString("") }
            .filter { regex.matches(it) }
            .distinct()

        return permutations.count().toLong()
    }
        return input.sumOf { getPermutations(it) }    }

     */

    fun arrangements(s: String, ls: List<Int>): Long {
        val memo = IntArray(s.length) { i -> s.drop(i).takeWhile { c -> c != '.' }.length }

        val dp = mutableMapOf<Pair<Int, Int>, Long>()

        fun canTake(i: Int, l: Int) = memo[i] >= l && (i + l == s.length || s[i + l] != '#')
        fun helper(si: Int, lsi: Int): Long =
            if (lsi == ls.size) {
                if (s.drop(si).none { c -> c == '#' }) 1L else 0
            }
            else if (si >= s.length) 0L
            else {
                if (dp[si to lsi] == null) {
                    val take = if (canTake(si, ls[lsi])) helper(si + ls[lsi] + 1, lsi + 1) else 0L
                    val dontTake = if (s[si] != '#') helper(si + 1, lsi) else 0L
                    dp[si to lsi] = take + dontTake
                }
                dp[si to lsi] ?: 0
            }
        return helper(0, 0)
    }

    fun part1(input: List<String>): Long {

        val map = input.map { line -> line.split(" ").let { (l, r) -> l to r } }

        return map.sumOf { (s, counts) ->
            arrangements(s, counts.split(",").map(String::toInt))
        }
    }



    fun part2(input: List<String>): Long {
        val map = input.map { line -> line.split(" ").let { (l, r) -> l to r } }
        return map.sumOf { (s, counts) ->
            arrangements(List(5){s}.joinToString ( "?"), List(5){counts.split(",").map { it.toInt() }}.flatten())
        }
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




