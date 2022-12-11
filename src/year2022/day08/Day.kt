package year2022.day08

import readInput


fun main() {
    val day = "08"
    val expectedTest1 = 21
    val expectedTest2 = 8


    fun checkIt(trees: CharSequence, index: Int, sz: Char) = trees.subSequence(0, index).any { t -> t >= sz } &&
                                                trees.subSequence(index+1, trees.length).any { t -> t >= sz }

    fun indexVisibility(trees: CharSequence, index: Int, sz: Char) =
        (trees.subSequence(1, index).takeLastWhile { t -> t < sz }.length + 1) *
        (trees.subSequence(index + 1, trees.length - 1).takeWhile { t -> t < sz }.length + 1)

    fun part1(input: List<String>): Int {
        val map = input.mapIndexed{ rowNumber, row ->
            row.mapIndexed { columnNumber, c ->
                rowNumber != 0 && rowNumber < input.size - 1 &&
                columnNumber != 0 && columnNumber < row.length-1
               && checkIt(row, columnNumber, c)
               && checkIt(input.map { it[columnNumber] }.joinToString(""), rowNumber , c)
            }
        }
        return map.flatten().count { !it }
    }

    fun part2(input: List<String>): Int {
        val map = input.mapIndexed { rowNumber, row ->
            row.mapIndexed { columnNumber, c ->
                when {
                    rowNumber == 0 -> 0
                    rowNumber == input.size-1 -> 0
                    columnNumber == 0 -> 0
                    columnNumber == row.length-1 -> 0
                    else -> indexVisibility(row, columnNumber, c) *
                            indexVisibility(input.map { it[columnNumber] }.joinToString(""), rowNumber, c)
                }
            }
        }
        return map.flatten().max()
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




