package year2024.day12

import kotlin.math.abs
import year2024.solveIt


fun main() {
    val day = "12"

    val expectedTest1 = 1930L
    val expectedTest2 = 1206L


    fun isConnected(p: Pair<Int, Int>, v: Pair<Int, Int>) = abs(p.first - v.first) + abs(p.second - v.second) == 1

    fun mergeConnected(points: List<Pair<Int, Int>>): List<List<Pair<Int, Int>>> {
        return points.fold(emptyList()) { acc, v ->
            val (connected, notConnected) = acc.partition { group -> group.any { isConnected(it, v) } }
            val newGroup = connected.flatten() + v
            notConnected + listOf(newGroup)
        }
    }

    fun getCost(input: List<String>, costFunction: (List<Pair<Int, Int>>) -> Int): Long {
        val lettersMap: Map<Char, List<Pair<Int, Int>>> =
            input.indices.flatMap { y -> input[y].indices.map { x -> input[y][x] to Pair(y, x) } }.groupBy({ it.first }, { it.second })

        val finalGroups = lettersMap.values.flatMap { mergeConnected(it) }
        val flatMap = finalGroups.map { it.size to  costFunction(it) }
        return flatMap.sumOf { it.first * it.second }.toLong()
    }

    fun getCost1(group: List<Pair<Int, Int>>) = group.sumOf { p -> 4 - group.count { isConnected(it, p) } }

    fun part1(input: List<String>): Long {
        return getCost(input) { group -> getCost1(group) }
    }

    fun getCostLine(groupLines: Map<Int, List<Pair<Int, Int>>>, delta: Int): List<Int> {
        return groupLines.map { (lineNumber, lineItems) ->
            val adjacentColumnIndexes = groupLines[lineNumber + delta]?.map { it.second }?.toSet().orEmpty()
            val itemsNotConnectedOtherLineItems = lineItems.map { it.second }.filterNot { it in adjacentColumnIndexes }.sorted()

            if (itemsNotConnectedOtherLineItems.isEmpty()) 0
            else itemsNotConnectedOtherLineItems.zipWithNext().count { (a, b) -> b - a > 1 } + 1
        }
    }

    fun getCostColumn(groupColumns: Map<Int, List<Pair<Int, Int>>>, delta: Int) = groupColumns.map { (columnNumber, columnItems) ->
        val adjacentItemsLineNumbers = groupColumns[columnNumber + delta]?.map { it.first }?.toSet().orEmpty()
        val sorted = columnItems.map { it.first }.filterNot { v -> v in adjacentItemsLineNumbers }.sorted().toList()

        if (sorted.isEmpty()) 0
        else sorted.zipWithNext().count { (a, b) -> (b - a) > 1 } + 1
    }

    fun getHorizontalFencesCost(group: List<Pair<Int, Int>>): Int {
        val groupLines: Map<Int, List<Pair<Int, Int>>> = group.groupBy { it.first }
        val upFences = getCostLine(groupLines, -1)
        val downFences = getCostLine(groupLines, 1)
        return (upFences + downFences).sum()
    }

    fun getVerticalFencesCost(group: List<Pair<Int, Int>>): Int {
        val groupColumns: Map<Int, List<Pair<Int, Int>>> = group.groupBy { it.second }
        val leftFences = getCostColumn(groupColumns, -1)
        val rightFences = getCostColumn(groupColumns, 1)
        return (leftFences + rightFences).sum()
    }

    fun getCost2(group: List<Pair<Int, Int>>): Int {
        val horizontalCosts = getHorizontalFencesCost(group)
        val verticalCosts = getVerticalFencesCost(group)
        return horizontalCosts +verticalCosts
    }

    fun part2(input: List<String>): Long {
        return getCost(input) { group -> getCost2(group) }
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}