package year2024.day21

import year2024.solveIt


fun main() {
    val day = "21"
    val expectedTest1 = 126384L
    val expectedTest2 = 154115708116294L


    val keyPad = listOf(
        "789",
        "456",
        "123",
        " 0A"
    )

    val robotPads = listOf(
        " ^A",
        "<v>"
    )

    fun getKeypadPosition(pad: List<String>, c: Char) = pad.mapIndexed { l, s -> l to s.indexOf(c) }.first { it.second > -1 }

    fun getPadOperations(pad: List<String>, current: Char, next: Char): String {
        val (currentY, currentX) = getKeypadPosition(pad, current)
        val (nextY, nextX) = getKeypadPosition(pad, next)

        fun move(x: Int, y: Int, s: String): Sequence<String> = sequence {
            if (x == nextX && y == nextY) yield(s + 'A')
            if (nextX < x && pad[y][x - 1] != ' ') yieldAll(move(x - 1, y, "$s<"))
            if (nextY < y && pad[y - 1][x] != ' ') yieldAll(move(x, y - 1, "$s^"))
            if (nextY > y && pad[y + 1][x] != ' ') yieldAll(move(x, y + 1, s + 'v'))
            if (nextX > x && pad[y][x + 1] != ' ') yieldAll(move(x + 1, y, "$s>"))
        }

        return move(currentX, currentY, "").minByOrNull { p -> p.zipWithNext().count { it.first != it.second } }!!
    }



    fun getOperations(code: String, indirectionLevel: Int, numberOfIndirection: Int, memo: MutableMap<Pair<String, Int>, Long>): Long {
        if (indirectionLevel > numberOfIndirection) return code.length.toLong()
        memo[code to indirectionLevel]?.let { return it }
        val currentPad = if (indirectionLevel > 0) robotPads else keyPad
        val result = "A$code".zip(code).sumOf { (current, next) -> getOperations(getPadOperations(currentPad, current, next), indirectionLevel + 1, numberOfIndirection, memo) }
        memo[code to indirectionLevel] = result
        return result
    }

    fun part1(input: List<String>): Long {
        val memo = mutableMapOf<Pair<String, Int>, Long>()
        return input.sumOf { getOperations(it, 0, 2, memo) * it.dropLast(1).toInt() }
    }

    fun part2(input: List<String>): Long {
        val memo = mutableMapOf<Pair<String, Int>, Long>()
        return input.sumOf { getOperations(it, 0, 25, memo) * it.dropLast(1).toInt() }
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}

