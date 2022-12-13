fun main() {
  data class Point(var row: Int, var col: Int, var height: Int)

//  fun getNeighbors(r: Int, c: Int, maze: List<List<Int>>) : MutableList<Point> {
//    val height = maze.size
//    val width = maze[0].size
//    var neighbors = mutableListOf<Point>()
//    for(row in -1..1) {
//      for(col in -1..1) {
//        if(r + row < height && r + row >= 0 && c + col < width && c + col >= 0 && !(r==0 && c==0)) {
//          neighbors.add(Point(r+row, c+col, maze[r+row][c+col]))
//        }
//      }
//    }
//    return neighbors
//  }

  fun getNeighbors(r: Int, c: Int, maze: List<List<Int>>) : MutableList<Point> {
    val height = maze.size
    val width = maze[0].size
    var neighbors = mutableListOf<Point>()

    if(r + 1 < height) {
      neighbors.add((Point(r + 1, c, maze[r + 1][c])))
    }
    if(r - 1 >= 0) {
      neighbors.add((Point(r - 1, c, maze[r - 1][c])))
    }
    if(c + 1 < width) {
      neighbors.add((Point(r, c + 1, maze[r][c + 1])))
    }
    if(c - 1 >= 0) {
      neighbors.add(Point(r , c - 1, maze[r][c - 1]))
    }

    return neighbors
  }

  fun part1(input: List<String>): Int {
    val START = 'S'.code
    val END = 'E'.code
    val maze = input.map {
      it.windowed(1).map { it.first().toChar().code }.toMutableList()
    }.toMutableList()
    // you start at a


    val locations = mutableListOf<Point>()
    maze.forEachIndexed { r, nums ->
      nums.forEachIndexed { c, value ->
        locations.add(Point(r, c, value))
      }
    }

    val startLoc = locations.find { it.height == START }!!
    startLoc.height = 'a'.code
    maze[startLoc.row][startLoc.col] = 'a'.code
    val end = locations.find { it.height == END }!!
    end.height = 'z'.code
    maze[end.row][end.col] = 'z'.code
    val distances = mutableMapOf<Point, Int>()
    val precedingPoints = mutableMapOf<Point, Point?>()

    locations.forEach {
      distances[it] = Int.MAX_VALUE
      precedingPoints[it] = null
    }

    // set up start node
    distances[locations[0]] = 0
    val start = locations[0]
    val locationQueue = mutableListOf<Point>()
    locationQueue.add(start)
    val visited = mutableListOf<Point>()
    while(locationQueue.isNotEmpty()) {
      val loc = locationQueue.removeAt(0)
      for(pos in getNeighbors(loc.row, loc.col, maze)) {
        if(!visited.contains(pos) && loc.height >= pos.height - 1) {
          distances[pos] = distances[loc]!! + 1
          locationQueue.add(pos)
          visited.add(pos)

          if(pos.height == END) {
            break
          }
        }
      }
    }
    println(distances[locations.filter { it.row == end.row && it.col == end.col }[0]])


    return 0
  }


  fun part2(input: List<String>): Int {

    return 0
  }



  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day12_test")
  println(part1(testInput))
//  println(part2(testInput))

  val input = readInput("Day12")
  output(part1(input))
//  output(part2(input))
}
