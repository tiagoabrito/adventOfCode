package year2024.day09

import year2024.solveIt


private const val FREE = Int.MIN_VALUE

fun main() {
    val day = "09"

    val expectedTest1 = 1928L
    val expectedTest2 = 2858L


    fun zipValues(values: List<Int>): List<Int> {
        val result = values.toMutableList()
        var endPointer = values.size - 1

        for (index in result.indices) {
            if (index > endPointer) {
                break
            }
            if (result[index] == FREE) {
                while (result[endPointer] == FREE && endPointer > index) {
                    endPointer--
                }
                if (index < endPointer) {
                    result[index] = result[endPointer]
                    result[endPointer] = FREE
                    endPointer--
                }
            }
        }
        return result
    }

    fun part1(input: List<String>): Long {
        val indexedValues: List<Int> = input.joinToString("").mapIndexed { index, char ->
            val num = Character.getNumericValue(char)
            when {
                index % 2 == 0 -> List(num) { index / 2 }
                else -> List(num) { FREE }
            }
        }.flatten()

        val compacted = zipValues(indexedValues)

        return compacted.mapIndexed { index, value ->
            if (value == FREE) 0L else value * index.toLong()
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val mapIndexed = input.joinToString("")
            .mapIndexed { index, c ->
                Character.getNumericValue(c) to if (index % 2 == 0) index / 2 else FREE
            }.toMutableList()

        val result = mutableListOf<Pair<Int, Int>>()

        for (index in mapIndexed.indices) {
            val (size, id) = mapIndexed[index]

            if (id != FREE) {
                result.add(size to id)
            } else {
                var freeSpace = size
                for (i in mapIndexed.indices.reversed()) {
                    if (freeSpace == 0) break
                    if (i == index) {
                        result.add(freeSpace to FREE)
                        break
                    }
                    val (toMoveSize, toMoveId) = mapIndexed[i]
                    if (toMoveId != FREE && freeSpace >= toMoveSize) {
                        result.add(toMoveSize to toMoveId)
                        mapIndexed[i] = toMoveSize to FREE
                        freeSpace -= toMoveSize
                    }
                }
            }
        }

        return result
            .flatMap { (size, id) -> List(size) { id } }
            .mapIndexed { idx, id -> idx.toLong() * (if (id == FREE) 0 else id) }
            .sum()
    }
    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}
