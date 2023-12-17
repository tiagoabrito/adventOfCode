package year2023.day14

import year2023.solveIt


fun main() {
    val day = "14"

    val expectedTest1 = 136L
    val expectedTest2 = 64L


    fun getColumn(chars: List<Char>): Long {
        var total = 0L
        var lastSupport = 0

        for (index in chars.indices) {
            if (chars[index] == 'O') {
                total += chars.size - lastSupport
                lastSupport++
            }
            if (chars[index] == '#') {
                lastSupport = index + 1
            }
        }
        return total
    }

    fun rotate(input: List<String>): List<String> {
        val temp = input.map { it.reversed() }
        return input[0].indices.map { i -> temp.map { it[i] }.joinToString("") }
    }


    fun sort(v: List<Char>): String {
        val map =
            (listOf(0) + v.mapIndexedNotNull { index, c -> if (c == '#') index else null } + v.size).zipWithNext { a, b -> v.subList(a, b) }.map {
                it.filter { c -> c == '#' } + it.filter { c -> c != '#' }.sortedDescending()
            }
        val joinToString = map.flatten().joinToString("")
        return joinToString
    }

    fun part1(input: List<String>): Long {
        val rotate = rotate(input)
        val columns = rotate.map { sort(it.toList()) }
        val map = columns.map { getColumn(it.toList()) }

        return map.sum()
    }


    fun printIt(current: List<String>) {
        current.forEach { println(it) }
        println()
        println()
    }


    fun getIt(chars: List<Char>): Long {

        return chars.mapIndexedNotNull{index, c -> if(c=='O'){chars.size-index } else null}.sum().toLong()
    }

    fun part2(input: List<String>): Long {
        val cycleCache = mutableMapOf<List<String>, List<String>>()
        val cache = mutableMapOf<String, String>()
        var current = input;

        repeat(1000000000) {
            current = cycleCache.getOrPut(current) {
                var v = current;
                v = rotate(v).map { sort(it.toList()) }
                v = rotate(rotate(rotate(v))).map { sort(it.toList()) }
                v = rotate(rotate(rotate(v))).map { sort(it.toList()) }
                v = rotate(rotate(rotate(v))).map { sort(it.toList()) }
                rotate(rotate(v))
            }
           // println(rotate(current).sumOf { getIt(it.toList()) })
            //printIt(current)
        }





        return rotate(current).sumOf { getIt(it.toList()) }
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




