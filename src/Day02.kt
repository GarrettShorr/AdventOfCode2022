fun main() {
    fun part1(input: List<String>): Int {
        val THEIR_ROCK = "A"
        val THEIR_PAPER = "B"
        val THEIR_SCISSORS = "C"
        val MY_ROCK = "X"
        val MY_PAPER = "Y"
        val MY_SCISSORS = "Z"


        val scores = mutableListOf<Int>()
        val rounds = input.map { it.split(" ") }
        val symbolScores = mapOf(
            MY_ROCK to 1,
            MY_PAPER to 2,
            MY_SCISSORS to 3
        )
        val roundScores = mapOf(
            THEIR_ROCK + MY_ROCK to 3,
            THEIR_PAPER + MY_PAPER to 3,
            THEIR_SCISSORS + MY_SCISSORS to 3,
            THEIR_ROCK + MY_PAPER to 6,
            THEIR_ROCK + MY_SCISSORS to 0,
            THEIR_PAPER + MY_ROCK to 0,
            THEIR_PAPER + MY_SCISSORS to 6,
            THEIR_SCISSORS + MY_ROCK to 6,
            THEIR_SCISSORS + MY_PAPER to 0
        )

        for(round in rounds) {
            val theirs = round[0]
            val yours = round[1]
            var score = symbolScores[yours]
            println("theirs: $theirs yours: $yours score: $score round result: ${roundScores[theirs+yours]!!}")
            score = score?.plus(roundScores[theirs+yours]!!) ?: 0
            println("after the round: $score")
            scores.add(score)
        }
        return scores.sum()
    }
    // rock = A = 1   lose X: scissors 3, tie Y: rock 1, win Z: paper : 2
    // paper B = 2  lose X: rock 1, tie Y: paper: 2, win Z: scissors: 3
    // scissors = C = 3  lose X: paper 2, tie Y: scissors: 3, win Z: rock: 1


    fun part2(input: List<String>): Int {
        val scores = mutableListOf<Int>()
        val roundScores = mapOf(
            "A X" to 3 + 0,
            "A Y" to 1 + 3,
            "A Z" to 2 + 6,
            "B X" to 1 + 0,
            "B Y" to 2 + 3,
            "B Z" to 3 + 6,
            "C X" to 2 + 0,
            "C Y" to 3 + 3,
            "C Z" to 1 + 6
        )

        for(round in input) {
            println("round: $round score: ${roundScores[round]!!}")
            scores.add(roundScores[round]!!)
        }
        return scores.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
//    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day02")
//    output(part1(input))
    output(part2(input))
}
