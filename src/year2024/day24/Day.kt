package year2024.day24

import year2024.solveIt


data class Oper(val inputs: List<String>, val operation: String, val output: String){

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
    val expectedTest2 = "guess it"


    fun getWires(input: List<String>) = input.takeWhile { it.isNotEmpty() }.map {
        val split = it.split(": ")
        split[0] to split[1].toInt()
    }.toMap().toMutableMap()

    fun getOperations(input: List<String>) = input.dropWhile { it.isNotEmpty() }.drop(1).map {
        val split = it.split(" ")
        Oper(listOf(split[0], split[2]), split[1], split[4])
    }.toMutableList()

    fun getZValues(
        input: List<String>,
        wireValues: MutableMap<String, Int>
    ): List<Map.Entry<String, Int>> {
        val operations = getOperations(input)

        while (operations.isNotEmpty()) {
            val filter = operations.filter { it.inputs.all { wireValues.contains(it) } }
            if (filter.isEmpty()) {
                break
            }
            operations.removeAll(filter)
            filter.forEach { wireValues[it.output] = it.getResult(wireValues) }
        }

        val z = wireValues.asSequence().filter { it.key.startsWith("z") }.asIterable().sortedBy { it.key }.reversed().toList()
        return z
    }

    fun part1(input: List<String>): Long {
        val wireValues = getWires(input)

        val z = getZValues(input, wireValues)
        return z.map { it.value }.joinToString("").toLong(2)
    }


    fun part2(input: List<String>): String {
        val wireValues = input.takeWhile { it.isNotEmpty() }.associate {
            val split = it.split(": ")
            split[0] to when (split[0].startsWith("x")) {
                true -> 1
                false -> 0
            }
        }.toMutableMap()

        val z = getZValues(input, wireValues)

        /**
         * use z outputs and the graph to detect wrong values
         * https://dreampuf.github.io/GraphvizOnline/?engine=dot&compressed=CYSw5gTghgDgFgUgEwAYDiyUIOwCEEoCCmJqJRBxFqAzgK4BGksielpWZ1nPHAdgHtgAUxIBWXDQAuATwA2ogMwARAGYg5C4MgDCAYwFyBEBCojDtY5afzcOHA0ZMqGcuktvEy33qgBeKABsNuw%2B9qhwAO4A1iF2YT5%2BigCMcV6%2BGXAwemnhGUnYuQkZepBFGRwAjgC22oqeeRxSfFLlONbxFbSMzPAkbJ2NxQAeKDwAtKYAopijqaiTijOoo0iYi8soo4rr07MoACy7S-tix5ujwQt7KyiF1ye3ABzn%2BwCcryvJEzdbyfMoDazZJrB4XZI7MHAo5Qr5nWF-K6A37DZL3ZGPP4vBGoj44nxAlZIAGErZIUEYi5ISGU2ZIGG0onwxlkpGk4ZIdHspDYlkcvF8xQ-THDFKfLaKCnsxQ06UM6XM6VslGKLkq3nSgXsg7Ci4HEkog5Sw2yw1HeqhLpWzABXU2lAGzEBY1OlCm13y34BRVeoLigJq10a31ar3ff3-CMuzZ%2BCERz1O5I%2BxPKxOBmPJYOJ0NOgle4n%2B8mF90x%2BmF5Ol1Ol9M2nmFnMxoX%2BsUIpLRm0y5sJxsVjtVjs1-yKLONhs2nX%2B-WT9v%2BA4l8fd8dnC2DMLYDpULwAMQAVJa8vQmNA%2BgNCDI-a2-SvCJd-VfPDRIgArcVRWLXiBPgBur5iaWqT4mK2YrXnAYCRM2qTXl%2B0SIAiWQ5NewBSJUr7ZGkYCVDkwGFNBlQ-jhGFYeKpTOJ4X74SRZTXjISC9qgNR1J4HL0SgjFpLRrHNK014seK3G5KuvDrvuxSiRUQnkOkeR7kgW7iRwh69KwDRiWpvBEOe-YRH%2BvEXiyb5pI%2BL6XsEH7fneZmeABQEGehoHgWhiHkbBkFpMhqHAVBniYdhLKkWkFEEf51E%2BcRhF4cFpIFBxdH8S0aR8QiAk0XFCLsbxaUshlqnWiJklcNJYkFVgRDAKoqjipENCodeLQMFVNWxSOmA0KoTGUByLW0O1aRSFIYDigwkQQdeDAQLECLDaN1lgHw4rfjQgVgNoCKLWkEAwK0wFYB%2B1QNTtaRwF%2B20srGy6ePhB1nUmaSVE%2BcEsptPGeMAwCTU9W0YZ%2B4oQJUZGUE%2BlTzQif0A0QXXinwTDNVDMOpYutAwDNnVlgiNDIxxbritUeiVXp86oLj%2BOeDAT4gyyT4MB1RA0ABC3U1jg4oG9KMQ3c4qs0Z0QfdFSAfNelSqLZfMC54MjDuKqgY4lksItLMAcVOyWqM5nXKyyUiqxxqril%2BDC1cxusIvrhuULRzObUtmWWzA1ueFIEAi78X4rWk0urSyrs04QNBzXeOygZtAcbShnNh9e0R6BTpIeYlyTaSgqiVPb5sJ1LKeJSgXHR1jOfzdeGPVJO5qXXwEGtgcpeUM%2BS3wQw74PsAzuYnADeJQW8H67FjqbMdDXXsTJEWWNMHDz%2B14wKoiuttSRkQKdfOB69ZOFi8dUw7P68O7n6PPmkT40J7pLGRt-uVxdlDVNPk6X0QkSUQiwCr4P0RRb8z8vjRiisR7su-0fPqcATI3W3pQYG11oqZnctUQaCJIgwEGkhfe8DEFZ0RigSAxcaKHHFFgnWM42IQDZjeSU4p-okIQs2NYgtiHUPdjAGe-lGFpEiMAd%2BmI9AsOvFIL8HCYxziOu3Suy9OoOk5mAL%2B4txFP0kWkGAX4K43WrkQMAMtWzJBUTeW66MpAkJkDolk0gSEILgWdMsg84At1LFo4ANBrGYDANUShgCEROJIfYvy0VOyC2qI9bxoiiB6GAP434ehojYM8JEaWJEIlHUiF4sJISNoTRIskyeWs9aqCkZQYxWScm0wgIkzEqhqgF0un4qWZSda90wDBRAvEWxe1coXCAMcwkQBJpQL82SSKdKMmAUJToJzXkiPtScu1PB8DgLzMJ5MMLOOHuUyg08mFQMCYQNh-CbRxkaWOVAwA9Cp00oofZLMjkd0IU%2BWBsUrk3NSmct6jdUaPPeu7OmJEpDPKIAvMxpI9BfMShrUk1RogD3FsC34oLwWdWSGc6Z3yzxwqhjMjCYBZmYgGjCoglQH78TANis8RoqqHyBYQ2uHFwxPwqvHO0ByaU4NYjQZlWcmUsrGk7X8asiB8BpfBBJaRogwEgV6H%2Bgq9HNjvjeCc8svzHKJXSpOcr3JhwRDZQKrk1WAT6trdGYBFYfjQXqg15FekaKsrktpEYLUQ0MaSY6L006sQdWkPgfBimbE8RtaG4ovVjX6uQ7u14gYgNJFdI6z9OaRqQhcp%2B0bxZIETvtBpzFE04wYCmtOtTUDjXBto7NKBc2sPdb9Aa7tqmgzLTRTR4oFEkNRBgut7sJXwNUISqmx9fjRMJRbHGb8O7M1BRPciX5Q2-D9ss1RT4MWev9jgs54FJ03hQAuyIS7VAtvMd5QGjNZ7bs0vpUkn5h1iMTsenWrFogSsaZe691kM3imiJyyOwDH3PqNonaI9idafu-XpbqKAvz3OkQBoDSCokxI0TQzwwShkZmg5QASJssiuoYHXL2KGkK4unAMtZXpiWF2w-LPg8q0MeswKoEj-5Kh-K7eMpCkb4H0YfCgoxL9PDnvRuxyg40Z07Nwg7MtGiBOUHKpVDRkzKD1QjJJzSxKn7Yd4vJlkwBFPSMIZIxFqw8HTtdTAYuCJeVdPvk1QzDLPBwEY2dU57lY3WbFmnQmLM%2BAFO0U54ALms4AZqvolA3m1OUC-Q41AvkD7KrccRJTTmvxwDNnJ6LsXKXkpibxEEVUUs%2BUWRohzPLUXZcFa%2B2eNrCDJsLMV1Eid7FLoMZVq1149AWXlgSu61UpbNdGck%2BC8bKDhJFa3brB74WYz0kNkh0LfpoNGaxo9k3xZoj6dy7RzM9BFMpU51Z8d1vcJg4ChEQrCX6yUaSfbWdFV8C-D7c8Z2LtYwwbi%2BVow7utaUxg6%2BJrzZVxxjfOqN3Lz7sIMAORf3bm1r0PWosCJsgkOAAwAz5iRM-LtoWBHhAWi0cxELPNbVO0Y%2BFu5QHVV97XlUOFlkI1XMrbh9FbORkWWXilTIGVRjBlAsVX7TNRAr1Hd%2BI7PNJ3krvp4zQcj-g-NGSI2dMXoyjUsi2nmvxwWUBy6zgWsA6Ksaq-V-VlhbiZZIU87r97RB9Z9c2MDEhMXPLZXLrFRVYLImozt7D3Dvrm7uQlyfN3jTFUQGY%2BbJsoM-dEFge0p0ihive2bMVyIqSn7O8jpyuPDvbUAbsXmgxqe-0PgXpzYDlAp54cxMAPPiP0Okgfiesqzv4GUUFjb0Gc6Hz6d%2Bo3qTmN0Z43ck%2BdHnrO%2BFxZ3G8DNdxmD8pWcvRRvtHj4QahnvmA0fuVXoZqtnh7eFlk4QWLfH-BkAyYr6G8qMbc8xAf2WzMgo63P7Xy63dIeZNAidWt9-mLQOSq%2B6tAGpDv%2BYoI5KfClZOa8KV6%2Byd7L6EqLpQxSA9qdwsgbrg4FpwGxTuZE6prIGuaihOY1Ac5nidjpSVIfhCbWZaLSxU6ipaIrYi5sQ%2BpjQjTkLUGeBAyh4xgczXiQBjqugo7DCfZcbJ5EoYJFxd5z6i45ao7t6S4iFgxDTC747H6bBkasJtp6zRJHS35ezKGgSVDb4oB0yuZr7oz0zE7qJk7pIWbV7GHYEciJwx6Iq0RWGpJByF5yFjz95MGYAMDOEOz-4mwMCEptxaEm4bTDR3gIZEBYrBGyxOZrqLYSyREloIw4xWIdyvaJHQQ3JPzd7uxUbpFD4-Ij4shRxLrPSPq5ypROb2KOoQxzzow56GGOEdhgJEBtykFh4NGo58o3T-ZFoaL-bTqUEF4HxpGy7fYwa%2B4LSAKFx06UzjEv4FpOw2GRigz2HixkKGZxFGyEJuqLbsR7bf7dIoY7HoFUosh8DN7VpnanGeCqDNYIiU7fTNGbC3HQTKGzwhGEBQ6FivGn6zxSpIbmJSqjDMyMFMwLTAzyJg4LRRyBTPGUyQmNIYIkE6zwkfKTyKIgmuZqJ1GoAhrcxaH3TYGY7kIPTuRwDibGHAHhL3GYBbI1KviOSNIFpgT1o1pTQBrVoYIMCsnLE%2B4RxGzclxYA5A7ZR4o-Y45m7CmXGZzJQr49b9JSk5HLqKq2hYyKljC261rzKZSKpkxLr8i%2Bo8yxRnI0D6kfhBEmyjp9TXFezmk8KFYGTTHgI%2BrwT2lBLzLwSaF9Qz5uk2EoCEIQBBF6S%2Bn%2BnLEAa9aywhlgpJacwknxyEIhLGZLZDRLFpzMy8aIl6xBmdSKAYKHaBQGyvjx4wZlD1y8EUK-T0GUBUKgzlknJnLVA0ayy1n1n1aNaS5aL3Z3haISxJqt4Qzh44w9nLpOZWKT7nhDnBxTbsECL-bjaVz-YYyTmOI-SgR-huJLkNDrhAA
         */
        return "guess it"
    }


    solveIt(day, ::part1, expectedTest1, ::part2, expectedTest2, "test")

}