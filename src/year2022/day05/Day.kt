package year2022.day05

import java.util.function.Function
import readInput

fun main() {

    fun part1(input: List<String>, myFunc: Function<List<Char>, List<Char>> = Function { t: List<Char> -> t.reversed() }): String {
        val origin: MutableMap<Int, MutableList<Char>> = input.subList(0, input.indexOf("")).map { it.chunked(4).map { it[1] } }
            .reversed()
            .drop(1)
            .fold<List<Char>, MutableMap<Int, MutableList<Char>>>(mutableMapOf()) { acc, elem ->
                acc.also {
                    elem.forEachIndexed { index, item -> acc.computeIfAbsent(index + 1) { mutableListOf() }.add(item) }
                }
            }.onEach { (_, l) -> l.removeIf { !it.isLetter() } }

        val moves =
            input.subList(input.indexOf("") + 1, input.size)
                .map {
                    Regex("\\d+").findAll(it)
                        .map { it.value.toInt() }.toList()
                }
                .fold(origin) { acc, move ->
                    acc[move[2]]?.addAll(myFunc.apply(acc[move[1]]?.takeLast(move[0]) ?: emptyList()))
                    acc[move[1]] = acc[move[1]]?.dropLast(move[0])?.toMutableList() ?: mutableListOf()
                    acc
                }.entries.sortedBy { it.key }.map { it.value.last() }.joinToString("")
        return moves
    }


    fun part2(input: List<String>): String {
        return part1(input, Function.identity())
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2022/day05/test")
    println(part1(testInput))
    check(part1(testInput) == "CMZ") { "expected \"CMZ\" but was ${part1(testInput)}" }

    val input = readInput("year2022/day05/input")
    println(part1(input))

    check(part2(testInput) == "MCD")
    println(part2(input))
}




