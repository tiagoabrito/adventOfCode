package year2021.day25

import java.util.stream.IntStream
import readInput

fun main() {


    fun part1(input: List<CharSequence>): Int {
        var move = 0;
        for (l in 0..input.size) {
            var line = input[l]
            var lineResult = line.toMutableList()
            for (c in 0..line.length) {
                //  if(line[c] == '>' && line[((c + 1) % line.length)])
            }
        }
        return -1
    }


    fun part2(input: List<String>): Int {
        return -1
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("year2021/day4/test")
    println(part1(testInput))
    check(part1(testInput) == 58)

    val input = readInput("year2021/day4/input")
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}




