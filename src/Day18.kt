import kotlin.math.abs

fun main() {

  data class Cube(val x: Int, val y: Int, val z: Int) {
    fun isAdjacent(other: Cube): Boolean {
      if (x == other.x && y == other.y) {
        return abs(z - other.z) == 1
      } else if (y == other.y && z == other.z) {
        return abs(x - other.x) == 1
      } else if (z == other.z && x == other.x) {
        return abs(y - other.y) == 1
      } else {
        return false
      }
    }

    fun countExposedSides(others: List<Cube>): Int {
      var count = 6
      val cubes = others.filterNot { it.x == x && it.y == y && it.z == z }
      if (cubes.any { (it.x == (x - 1)) && (it.y == y) && (it.z == z) }) {
        count--
      }
      if (cubes.any { it.x == x + 1 && it.y == y && it.z == z }) {
        count--
      }
      if (cubes.any { it.y == y - 1 && it.x == x && it.z == z }) {
        count--
      }
      if (cubes.any { it.y == y + 1 && it.x == x && it.z == z }) {
        count--
      }
      if (cubes.any { it.z == z - 1 && it.x == x && it.y == y }) {
        count--
      }
      if (cubes.any { it.z == z + 1 && it.x == x && it.y == y }) {
        count--
      }
      return count
    }

    fun getNeighbors(): MutableList<Cube> {
      val neighbors = mutableListOf<Cube>()
      neighbors.add(Cube(x - 1, y, z))
      neighbors.add(Cube(x + 1, y, z))
      neighbors.add(Cube(x, y - 1, z))
      neighbors.add(Cube(x, y + 1, z))
      neighbors.add(Cube(x, y, z - 1))
      neighbors.add(Cube(x, y, z + 1))
//      for (dx in -1..1) {
//        for (dy in -1..1) {
//          for (dz in -1..1) {
//            if (dx != 0 && dy != 0 && dz != 0) {
//              neighbors.add(Cube(x + dx, y + dy, z + dz))
//            }
//          }
//        }
//      }
      return neighbors
    }
  }

  fun part1(input: List<String>): Int {
    var cubes = input.map {
      var (x, y, z) = it.split(",").map { it.toInt() }
      Cube(x, y, z)
    }
    var count = 0
    cubes.forEach { count += it.countExposedSides(cubes) }
    return count
  }

  fun hasNoEscape(cube: Cube, lavaDrops: Set<Cube>, dest: Cube,
                  maxX: Int, maxY: Int, maxZ: Int, minX: Int, minY : Int, minZ: Int) : Boolean {
    val visited = mutableSetOf<Cube>()
    val visitedMap = mutableMapOf<Cube, Int>()
    visited.add(cube)
    val queue = mutableSetOf<Cube>()
    queue.addAll(cube.getNeighbors().filterNot { it in lavaDrops })
    while(queue.isNotEmpty()) {
      val nextMove = queue.first()
      queue.remove(nextMove)
      if(nextMove == dest) {
//        println("Outside")
        return false
      }
      val neighbors = nextMove.getNeighbors().filterNot { it in lavaDrops }
        .filterNot { it in visited }
        .filterNot { it.x > maxX + 2 || it.y > maxY + 2 || it.z > maxZ + 2
            || it.x < minX - 2 || it.y < minY - 2 || it.z < minZ - 2 }.shuffled()
//      if(neighbors.isEmpty()) {
////        println("empty neighbors")
//        return true
//      }
//      if(visitedMap.getOrDefault(nextMove, 0) > 100) {
////        println("going in circles")
//        return true
//      }
      visited.add(nextMove)
      visitedMap[nextMove] = visitedMap.getOrDefault(nextMove, 0) + 1
      queue.addAll(neighbors)
    }
    return true
  }


  fun part2(input: List<String>): Int {
    val cubes = input.map {
      val (x, y, z) = it.split(",").map { it.toInt() }
      Cube(x, y, z)
    }

    // find all holes
    val minX = cubes.minOf { it.x }
    val maxX = cubes.maxOf { it.x }
    val minY = cubes.minOf { it.y }
    val maxY = cubes.maxOf { it.y }
    val minZ = cubes.minOf { it.z }
    val maxZ = cubes.maxOf { it.z }

    val empties = mutableListOf<Cube>()
    for (x in minX + 1 until maxX) {
      for (y in minY + 1 until maxY) {
        for (z in minZ + 1 until maxZ) {
          val cube = Cube(x, y, z)
          if (cube !in cubes) {
            empties.add(cube)
          }
        }
      }
    }

    val airPockets = mutableSetOf<Cube>()
    var outside = 0
    for(i in empties.indices) {
      if(hasNoEscape(empties[i], cubes.toSet(), Cube(minX , minY, minZ+1),
          maxX, maxY, maxZ, minX, minY, minZ)) {
        airPockets.add(empties[i])
      } else {
        outside++
      }
    }
    println("Airpockets: ${airPockets.size}  Outside: $outside")

    var surfaceArea = 0
    var adjacentPockets = 0
    cubes.forEach { surfaceArea += it.countExposedSides(cubes) }
    println(surfaceArea)
    for(airPocket in airPockets) {
      cubes.forEach {
        if(it.isAdjacent(airPocket)) {
          surfaceArea--
          adjacentPockets++
        }
      }
    }
    println(adjacentPockets)
    return surfaceArea
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day18_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day18")
//  output(part1(input))
  output(part2(input))
}

