import java.lang.Exception

fun main() {
  fun part1(input: List<String>): Int {
    val elves = mutableListOf<Int>()
    var totalCalories = 0
    for(food in input) {
      if (food != "") {
        totalCalories += food.toInt()
      } else {
        elves.add(totalCalories)
        totalCalories = 0
      }
    }
    elves.add(totalCalories)
    return elves.max()
  }

  fun part2(input: List<String>): Int {
    val elves = mutableListOf<Int>()
    var totalCalories = 0
    for(food in input) {
      if (food != "") {
        totalCalories += food.toInt()
      } else {
        elves.add(totalCalories)
        totalCalories = 0
      }
    }
    elves.add(totalCalories)
    println(elves)
//    for(i in 1..3) {
//      totalCalories += elves.max()
//      elves.remove(elves.max())
//    }

    return elves.sorted().takeLast(3).sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day01")
  output(part1(input))
  output(part2(input))
}
