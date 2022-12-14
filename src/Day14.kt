import java.lang.Exception
import java.lang.Integer.max
import java.lang.Integer.min

data class Point(val x: Int, val y: Int)

data class Line(val p1: Point, val p2: Point) {
  fun isHorizontal() : Boolean {
    return p1.x != p2.x && p1.y == p2.y
  }

  fun isVertical() : Boolean {
    return p1.x == p2.x && p1.y != p2.y
  }

  fun intersects(grain : Sand, dx: Int, dy: Int) : Boolean {
    if(isHorizontal()) {
      return grain.y + dy == p1.y && grain.x + dx>= min(p2.x, p1.x) && grain.x + dx <= max(p2.x, p1.x)
    }
    if(isVertical()) {
      return grain.x + dx == p1.x && grain.y + dy >= min(p2.y, p1.y) && grain.y + dy <= max(p2.y, p1.y)
    }
    throw Exception("No diagonal lines yet?")
  }
}

data class Sand(var x: Int, var y: Int, var atRest: Boolean = false)  {
  // center, left, right
  fun calculateClearPaths(sandPile: List<Sand>, structures: List<Line>) : Triple<Boolean, Boolean, Boolean> {
    var (center, left, right) = Triple(true, true, true)
    for(rocks in structures) {
      if(rocks.intersects(this, 0, 1)) {
        center = false
      }
      if(rocks.intersects(this, -1, 1)) {
        left = false
      }
      if(rocks.intersects(this, 1, 1)) {
        right = false
      }
    }
    for(grain in sandPile) {
      if(grain.y == this.y + 1 && grain.x == this.x) {
        center = false
      }
      if(grain.y == this.y + 1 && grain.x == this.x - 1) {
        left = false
      }
      if(grain.y == this.y + 1 && grain.x == this.x + 1) {
        right = false
      }
    }
    return Triple(center, left, right)
  }

  fun isAbyssBound(sandPile: List<Sand>, rockStructures: MutableList<Line>): Boolean {
    return sandPile.all { it.y < this.y } && rockStructures.all { max(it.p1.y, it.p2.y) < this.y }
  }
}
fun main() {


  fun simulateSandFall(grain: Sand, rockStructures: MutableList<Line>, sandPile: List<Sand>): Boolean {
    do {
      var clearPaths = grain.calculateClearPaths(sandPile, rockStructures)
      if(clearPaths.first) {
        grain.y++
      } else if(clearPaths.second) {
        grain.y++
        grain.x--
      } else if(clearPaths.third) {
        grain.y++
        grain.x++
      } else {
        grain.atRest = true
      }
      var abyssBound = grain.isAbyssBound(sandPile, rockStructures)
    } while(clearPaths != Triple(false, false, false) && !abyssBound && !grain.atRest)
    return grain.atRest
  }


  fun part1(input: List<String>): Int {
    var rockStructures = mutableListOf<Line>()
    for(line in input) {
      line.split(" -> ").map {
        Point(it.split(",")[0].toInt(), it.split(",")[1].toInt())
      }.zipWithNext { a, b -> Line(a, b) }.forEach { rockStructures.add(it) }
    }
    var sandAtRest = 0
    var sandPile = mutableListOf<Sand>()
    do {
      val grain = Sand(500, 0)
      var sandFellAndRested = simulateSandFall(grain, rockStructures, sandPile)
      if(sandFellAndRested) {
        sandAtRest++
        sandPile.add(grain)
        println(grain)
      }
    } while(sandFellAndRested)

    return sandAtRest
  }




  fun part2(input: List<String>): Int {
    var rockStructures = mutableListOf<Line>()
    for(line in input) {
      line.split(" -> ").map {
        Point(it.split(",")[0].toInt(), it.split(",")[1].toInt())
      }.zipWithNext { a, b -> Line(a, b) }.forEach { rockStructures.add(it) }
    }
    var lowestRock = rockStructures.maxBy { max(it.p1.y, it.p2.y)  }
    var floorY = max(lowestRock.p1.y, lowestRock.p2.y) + 2
    var floor = Line(Point(-1000000, floorY), Point(1000000, floorY))
    rockStructures.add(floor)
    var sandAtRest = 0
    var sandPile = mutableListOf<Sand>()
    do {
      val grain = Sand(500, 0)
      var sandFellAndRested = simulateSandFall(grain, rockStructures, sandPile)
      if(sandFellAndRested) {
        sandAtRest++
        sandPile.add(grain)
      }
      if(sandAtRest % 1000 == 0) {
        println(grain)
      }
//      println("${grain.x}, ${grain.y}, ${grain.y != 0 && grain.x != 500}")
    } while(grain.y != 0 || grain.x != 500)

    return sandAtRest
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day14_test")
//  println(part1(testInput))
//  println(part2(testInput))

  val input = readInput("Day14")
//  output(part1(input))
  output(part2(input))
}
