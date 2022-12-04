fun main() {
  fun part1(input: List<String>): Int {
    var count = 0
    for(jobs in input) {
      val jobPair = jobs.split(",")
      var range1 = extractRange(jobPair[0])
      var range2 = extractRange(jobPair[1])
      if(isRangeContained(range1, range2)) {
        count++
      }
    }

    return count
  }


  fun part2(input: List<String>): Int {
    var count = 0
    for(jobs in input) {
      val jobPair = jobs.split(",")
      var range1 = extractRange(jobPair[0])
      var range2 = extractRange(jobPair[1])
      if(isOverlapped(range1, range2)) {
        count++
      }
    }

    return count
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_test")
  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day04")
  output(part1(input))
  output(part2(input))
}

fun extractRange(str: String) : Pair<Int, Int> {
  var nums = str.split("-").map { it.toInt() }
  return Pair(nums[0], nums[1])
}

fun isRangeContained(range1: Pair<Int, Int>, range2: Pair<Int, Int>) : Boolean {
  return range1.first <= range2.first && range1.second >= range2.second ||
      range2.first<= range1.first && range2.second >= range1.second
}

fun isOverlapped(range1: Pair<Int, Int>, range2: Pair<Int, Int>) : Boolean {
  return !(range1.second < range2.first || range1.first > range2.second)
}