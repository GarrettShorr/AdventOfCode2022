fun main() {


  fun part1(input: List<String>): Int {
    println(input)
    val potentialMarkers = input[0].windowed(4).map { it.windowed(1).sorted() }
    println(potentialMarkers)
    for(i in potentialMarkers.indices) {
      if(potentialMarkers[i][0] != potentialMarkers[i][1] &&
        potentialMarkers[i][1] != potentialMarkers[i][2] &&
        potentialMarkers[i][2] != potentialMarkers[i][3]) {
        return i + 4
      }
    }
    return 0
  }


  fun part2(input: List<String>): Int {
    println(input)
    val potentialMarkers = input[0].windowed(14).map { it.windowed(1)  }
    println(potentialMarkers)

    for(i in potentialMarkers.indices) {
      val markerSet = mutableSetOf<String>()
      markerSet.addAll(potentialMarkers[i])
      if(markerSet.size == 14) { return i + 14 }
    }
    return 0
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day06")
//  output(part1(input))
  output(part2(input))
}
