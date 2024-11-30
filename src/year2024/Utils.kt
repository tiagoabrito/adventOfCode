package year2024

fun getLCM(a: Long, b: Long): Long {

    val biggerNum = if (a > b) a else b
    var lcm = biggerNum
    while (true) {
        if (((lcm % a) == 0L) && ((lcm % b) == 0L)) {
            break
        }
        lcm += biggerNum
    }
    return lcm
}