package year2024.day23

import year2024.solveIt


fun main() {
    val day = "23"
    val expectedTest1 = 7
    val expectedTest2 = "co,de,ka,ta"

    fun getConnections(input: List<String>) =
        input.map { it.split("-") }.map { listOf(it[0] to it[1], it[1] to it[0]) }.flatten().groupBy({ it.first }, { it.second })

    fun part1(input: List<String>): Int {
        val connections: Map<String, List<String>> = getConnections(input)
        val distinct: List<Set<String>> = connections.map { entry ->
            entry.value.map { that ->
                connections[that]!!.filter { other -> entry.value.contains(other) }.map { setOf(entry.key, that, it) }
            }.flatten()
        }.distinct().flatten().distinct()
        return distinct.count { it.any { it.startsWith("t") } }
    }

    fun part2(input: List<String>): String {
        val connections = input.map { it.split("-") }.map { listOf(it[0] to it[1], it[1] to it[0]) }.flatten().toSet()
        val pcs = (connections.map { it.first } + connections.map { it.second }).toSet()

        val networks = pcs.map { mutableSetOf(it) }

        for (n in networks){
            for (c in pcs){
                if(n.all{ it to c in connections}){
                    n.add(c)
                }
            }
        }
        return networks.maxBy { it.size }.sorted().joinToString(",")
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}