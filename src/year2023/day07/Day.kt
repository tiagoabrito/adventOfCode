package year2023.day07

import year2023.solveIt


fun main() {
    val day = "07"
    val expectedTest1 = 6440L
    val expectedTest2 = 5905L

    val sortingOrder = listOf('A','K','Q','J','T','9','8','7','6','5','4','3','2')

    fun getHandValue(eachCount: Map<Char, Int>): Int {
        return when(eachCount.size){
            1 -> 7
            2 -> when (eachCount.any { it.value == 4 }) {
                true -> 6
                false -> 5
            }
            3 -> when (eachCount.any { it.value == 3 }) {
                    true -> 4
                    false -> 3
                }
            4 -> 2
            5 -> 1
            else -> 0
        }
    }
    fun getHandValueJ(eachCount: Map<Char, Int>): Int {
        val jokers = eachCount.getOrDefault('J', 0)
        val filter = eachCount.filter { it.key != 'J' }.entries.sortedWith(compareBy({it.value * -1}, {sortingOrder.indexOf(it.key)}))
        val optimal = when (filter.isEmpty()){
            true -> eachCount
            false -> mapOf(filter[0].key to filter[0].value + jokers) + filter.drop(1).map { it.toPair() }
        }
        return getHandValue(optimal)
    }


    fun getIt(input: List<String>, kFunction1: (Map<Char, Int>) -> Int, sortingList: List<Char>): Long {
        val map = input.map { line ->
            val (cards, bet) = line.split(" ")
            val handValue = kFunction1(cards.groupingBy { it }.eachCount())
            cards to (bet to handValue)
        }
        val sorted = map.sortedWith(
            compareBy(
                { it.second.second * -1 },
                { sortingList.indexOf(it.first[0]) },
                { sortingList.indexOf(it.first[1]) },
                { sortingList.indexOf(it.first[2]) },
                { sortingList.indexOf(it.first[3]) },
                { sortingList.indexOf(it.first[4]) }
            )).reversed()
        sorted.map { it.first }.forEach { println(it) }
        val b = sorted.mapIndexed { k, v -> v.second.first.toLong() * (k + 1) }
        return b.sum()
    }

    fun part1(input: List<String>): Long {
        return getIt(input, ::getHandValue, sortingOrder)
    }

    fun part2(input: List<String>): Long {
        return getIt(input, ::getHandValueJ, sortingOrder.filter { l -> l != 'J' } + 'J')
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2)
}




