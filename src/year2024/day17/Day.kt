package year2024.day17

import year2024.solveIt

data class Registries(val a: Long, val b: Long, val c: Long)

fun main() {
    val day = "17"
    val expectedTest1 = "6,5,7,4,5,7,3,1,0"
    val expectedTest2 = 105875099912602


    fun getValue(s: String) = s.split(" ")[2].toLong()

    fun getCombo(it: Long, a: Long, b: Long, c: Long): Long =
        when (it) {
            4L -> a
            5L -> b
            6L -> c
            else -> it
        }

    fun getOutput(program: List<Int>, originalA: Long): String {
        var a: Long = originalA
        var b: Long = 0
        var c: Long = 0
        var pc = 0
        val output = mutableListOf<Int>()
        while (pc < program.size - 1) {
            val instruction = program[pc]
            val literal = program[pc + 1].toLong()
            val combo = { getCombo(literal, a, b, c) }
            when (instruction) {
                0 -> a /= (1 shl combo().toInt())
                1 -> b = b.xor(literal)
                2 -> b = combo() % 8
                3 -> {
                    if (a != 0L) {
                        pc = literal.toInt()
                        continue
                    }
                }

                4 -> b = b.xor(c)
                5 -> output.add(combo().toInt() and 7)
                6 -> b = a / (1L shl combo().toInt())
                7 -> c = a / (1L shl combo().toInt())
                else -> error("Invalid input")
            }
            pc += 2
        }
        return output.joinToString(",")
    }

    fun part1(input: List<String>): String {
        val registries = Registries(getValue(input[0]), getValue(input[1]), getValue(input[2]))
        val program = input[4].split(" ")[1].split(",").map { it.toInt() }
        return getOutput(program, registries.a)
    }


    fun part2(input: List<String>): Long {
        val program = input[4].split(" ")[1].split(",").map { it.toInt() }

       return program
            .reversed()
            .map { it.toLong() }
            .fold(listOf(0L)) { possibleSolutions, current ->
                possibleSolutions.flatMap { candidate ->
                    val shifted = candidate shl 3
                    (shifted until shifted + 8).mapNotNull { attempt ->
                                attempt.takeIf { getOutput(program, attempt).split(",").first().toLong() == current }
                    }
                }
            }.first()
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}
