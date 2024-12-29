package year2024.day24

import year2024.solveIt


data class Oper(val inputs: List<String>, val operation: String, val ouput: String){

    fun getResult(wireValues: Map<String, Int>) =
        when (operation) {
            "AND" -> wireValues[inputs[0]]!! and wireValues[inputs[1]]!! and 1
            "OR" -> (wireValues[inputs[0]]!! or wireValues[inputs[1]]!!) and 1
            "XOR" -> (wireValues[inputs[0]]!! xor wireValues[inputs[1]]!!) and 1
            else -> throw IllegalArgumentException("Invalid operation")
        }
}

fun main() {
    val day = "24"
    val expectedTest1 = 2024L
    val expectedTest2 = 0L


    fun part1(input: List<String>): Long {
        val wireValues = input.takeWhile { it.isNotEmpty() }.map { val split = it.split(": ")
            split[0] to split[1].toInt()
        }.toMap().toMutableMap()

        val operations = input.dropWhile { it.isNotEmpty() }.drop(1).map {
            val split = it.split(" ")
            Oper(listOf(split[0], split[2]), split[1], split[4])
        }.toMutableList()

        while (operations.isNotEmpty()){
            val filter = operations.filter { it.inputs.all { wireValues.contains(it) } }
            if (filter.isEmpty()){
                break
            }
            operations.removeAll(filter)
            filter.forEach { wireValues[it.ouput] = it.getResult(wireValues) }
        }

        val zzz = wireValues.asSequence().filter { it.key.startsWith("z") }
        val joinToString = zzz.asIterable().sortedBy { it.key }.reversed().map { it.value }.joinToString("").toLong(2)
        return joinToString
    }


    fun part2(input: List<String>): Long {
        return 0


    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}