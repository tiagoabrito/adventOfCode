package year2022.day13

import readInput

sealed class Data {
    data class Integer(val value: Int) : Data()

    data class ListData(val data: List<Data> = listOf()) : Data()
}

fun String.findClosingBracketFromPos(openBracketPos: Int): Int {
    var closedBracketPos = openBracketPos
    var counter = 1
    while (counter > 0) {
        when (this[++closedBracketPos]) {
            ']' -> counter--
            '[' -> counter++
        }
    }
    return closedBracketPos
}
fun String.parseLine(): Data {
    if (isEmpty()) return Data.ListData(listOf())

    val list = mutableListOf<Data>()

    var index = 0

    while (index < count()) {
        when (val char = this[index]) {
            '[' -> {
                val closedPos = this.findClosingBracketFromPos(index)
                val sub = substring(startIndex = index + 1, endIndex = closedPos)
                list.add(sub.parseLine())
                index = closedPos
            }
            ',' -> {}
            else -> {
                var digitToParse = ""
                var curr = char
                while (curr.isDigit()) {
                    digitToParse += curr
                    index++
                    if (index == count()) {
                        break
                    }
                    curr = this[index]
                    continue
                }
                list.add(Data.Integer(digitToParse.toInt()))
            }
        }
        index++
    }

    return Data.ListData(list)
}
fun main() {
    val day = "13"
    val expectedTest1 = 13
    val expectedTest2 = 1


    fun checkIt(left: String, right: String): Boolean {
        //return left.parseLine() == ""
        return false
    }

    fun part1(input: List<String>): Int {
        val sum = input.chunked(3)
            .mapIndexed { index, strings ->
                when (checkIt(strings[0], strings[1])) {
                    true -> index + 1
                    false -> 0
                }
            }
            .sum()
        return sum
    }


    fun part2(input: List<String>): Int {
        return input.size
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




