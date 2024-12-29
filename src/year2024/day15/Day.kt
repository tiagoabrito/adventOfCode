package year2024.day15

import year2024.solveIt


fun main() {
    val day = "15"

    val expectedTest1 = 10092L
    val expectedTest2 = 9021L

    fun move(line: String): String {
        val currIdx = line.indexOf('@')
        val start: Int = line.lastIndexOf('#', currIdx)
        val end: Int = line.indexOf('#', currIdx + 1)

        val toMove = line.substring(start + 1, end)
        val idx = toMove.indexOf('@')
        val moved = ('.' + toMove.takeLast(toMove.length - idx).replaceFirst(".", "")).takeLast(toMove.length - idx)
        return line.take(currIdx) + moved + line.substring(end)
    }

    fun move(direction: Char, map: List<String>): List<String> {
        val pos = map.mapIndexed { index, s -> index to s.indexOf('@') }.first { it.second > -1 }
        return when (direction) {
            '>' -> map.take(pos.first) + move(map[pos.first]) + map.drop(pos.first + 1)
            '<' -> map.take(pos.first) + move(map[pos.first].reversed()).reversed() + map.drop(pos.first + 1)
            'v' -> move(map.map { it[pos.second] }
                .joinToString("")).mapIndexed { index, c -> map[index].take(pos.second) + c + map[index].drop(pos.second + 1) }

            '^' -> move(map.map { it[pos.second] }.joinToString("").reversed()).reversed()
                .mapIndexed { index, c -> map[index].take(pos.second) + c + map[index].drop(pos.second + 1) }

            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
    }


    fun part1(input: List<String>): Long {
        val map = input.takeWhile { it.isNotEmpty() }

        val instructions = input.dropWhile { it.isNotEmpty() }.drop(1)
        val end = instructions.flatMap { it.asSequence() }.fold(map) { acc, c ->
            move(c, acc)
        }

        return end.flatMapIndexed { y, s -> s.mapIndexedNotNull { x, c -> c.takeIf { it == 'O' }?.let { y * 100 + x } } }.sum().toLong()
    }


    fun toMove(map: List<String>, pos: List<Pair<Int, Int>>): List<Pair<Int, Int>>? {
        if (pos.isEmpty()) {
            return emptyList()
        }
        if (pos.any { map[it.first][it.second] == '#' }) {
            return null
        }
        if (pos.all { map[it.first][it.second] == '.' }) {
            return pos.distinct()
        }

        val n = (pos + pos.mapNotNull {
            when (map[it.first][it.second]) {
                ']' -> it.first to it.second - 1
                '[' -> it.first to it.second + 1
                '@' -> it.first to it.second
                else -> null
            }
        }).distinct()

        val next = toMove(map, n.filter { map[it.first][it.second] == '@' }.map { it.first + 1 to it.second })
        val nextL =
            toMove(map, n.filter { map[it.first][it.second] == ']' }.flatMap { listOf(it.first + 1 to it.second - 1, it.first + 1 to it.second) })
        val nextR =
            toMove(map, n.filter { map[it.first][it.second] == '[' }.flatMap { listOf(it.first + 1 to it.second, it.first + 1 to it.second + 1) })

        if (next == null || nextL == null || nextR == null) {
            return null
        }
        return (nextL + nextR + n + next + pos).distinct()
    }


    fun moveDown(original: List<String>): List<String> {
        val pos = original.mapIndexed { index, s -> index to s.indexOf('@') }.first { it.second > -1 }

        val toMove = toMove(original, listOf(pos.first to pos.second)) ?: emptyList()
        return toMove.fold(original) { map, moving ->
            val toReplace = when (toMove.contains(moving.first - 1 to moving.second)) {
                true -> original[moving.first - 1][moving.second]
                false -> '.'
            }

            val newLine = map[moving.first].substring(0, moving.second) + toReplace + map[moving.first].substring(moving.second + 1)
            map.take(moving.first) + newLine + map.drop(moving.first + 1)
        }
    }


    fun move2(direction: Char, map: List<String>): List<String> {
        val pos = map.mapIndexed { index, s -> index to s.indexOf('@') }.first { it.second > -1 }
        return when (direction) {
            '>' -> map.take(pos.first) + move(map[pos.first]) + map.drop(pos.first + 1)
            '<' -> map.take(pos.first) + move(map[pos.first].reversed()).reversed() + map.drop(pos.first + 1)
            'v' -> moveDown(map)
            '^' -> moveDown(map.reversed()).reversed()
            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
    }

    fun part2(input: List<String>): Long {
        val map = input.takeWhile { it.isNotEmpty() }.map { l ->
            l.map { c ->
                when (c) {
                    '#' -> "##"
                    'O' -> "[]"
                    else -> "$c."
                }
            }.joinToString("")
        }

        val instructions = input.dropWhile { it.isNotEmpty() }.drop(1)

        val end = instructions.flatMap { it.asSequence() }.fold(map) { acc, c ->
            move2(c, acc)
        }

        return end.flatMapIndexed { y, s -> s.mapIndexedNotNull { x, c -> c.takeIf { it == '[' }?.let { y * 100 + x } } }.sum().toLong()
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}