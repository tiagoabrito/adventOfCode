package year2023.day19

import java.util.LinkedList
import java.util.Queue
import year2023.solveIt


fun main() {
    val day = "19"

    val expectedTest1 = 19114L
    val expectedTest2 = 167409079868000L

    fun toRuleFunction(line: String): Pair<String, List<(Map<Char, Long>) -> String?>> {
        val (name, rules) = line.split("{", "}")
        val lineRules: List<(Map<Char, Long>) -> String?> = rules.split(",").map { rule ->
            if (rule.contains(":")) {
                val (condition, destination) = rule.split(":")
                val (prop, value) = condition.split(">", "<")

                if (condition.contains(">")) {
                    { l -> if (l[prop[0]]!! > value.toLong()) destination else null }
                } else {
                    { l -> if (l[prop[0]]!! < value.toLong()) destination else null }
                }
            } else { _ -> rule }
        }
        return name to lineRules
    }

    fun part1(input: List<String>): Long {
        val (rules, parts) = input.joinToString("\n").split("\n\n").map { it.split("\n") }
        val rulesMap = rules.associate { toRuleFunction(it) }


        val partsMap = parts.map {
            it.removeSurrounding("{", "}").split(",").map { c ->
                val (letter, value) = c.split("=")
                letter[0] to value.toLong()
            }.toMap()
        }



        return partsMap.filter { part ->
                var curr = "in"
                while (curr != "A" && curr != "R") {
                    val firstNotNullOf = rulesMap[curr]!!.firstNotNullOf { it.invoke(part) }
                    curr = firstNotNullOf
                }
                curr == "A"
            }.sumOf { it['x']!! + it['m']!! + it['a']!! + it['s']!! }
    }


    fun getSet(): Set<Int> = (1..4000).iterator().asSequence().toSet()


    fun toRuleFunction2(line: String): Pair<String, List<Pair<Pair<Char, IntRange>, String>>> {
        val (name, rules) = line.split("{", "}")
        val lineRules = rules.split(",").map { rule ->
            if (rule.contains(":")) {
                val (condition, destination) = rule.split(":")
                val (prop, value) = condition.split(">", "<")

                if (condition.contains(">")) {
                    prop[0] to (value.toInt() + 1..4000) to destination
                } else {
                    prop[0] to (1 until value.toInt()) to destination
                }
            } else {
                '1' to (1..4000) to rule
            }
        }
        return name to lineRules
    }

    fun part2(input: List<String>): Long {
        val (rules, _) = input.joinToString("\n").split("\n\n").map { it.split("\n") }
        val rulesMap = rules.associate { toRuleFunction2(it) }


        val queue: Queue<Pair<String, Map<Char, Set<Int>>>> = LinkedList()
        queue.add("in" to mapOf('a' to getSet(), 'x' to getSet(), 'm' to getSet(), 's' to getSet()))

        var valid = 0L
        while (queue.isNotEmpty()) {
            val (s, map) = queue.remove()
            when (s) {
                "A" -> valid += map.values.map { it.size }.fold(1L) { acc, i -> acc * i }
                "S" -> continue
                else -> {
                    var current = map
                    rulesMap[s]?.forEach { rule ->
                        val r = rule.first.first
                        if (r == '1') {
                            queue.add(rule.second to current)
                        } else {
                            val groupBy = current[r]!!.partition { rule.first.second.contains(it) }
                            queue.add(rule.second to current + (r to groupBy.first.toSet()))
                            current = current + (r to groupBy.second.toSet())
                        }
                    }
                }
            }
        }
        return valid
    }

    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")
}




