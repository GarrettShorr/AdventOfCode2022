import java.util.LinkedList
import java.util.PriorityQueue

fun main() {
//  class Point(var row: Int, var col: Int, var height: Int, var parent: Point? = null) {
//    fun getPos() : Pair<Int, Int> { return Pair(row, col) }
//    override fun toString(): String {
//      return "Point(row=$row, col=$col, height=$height)"
//    }
//  }
  class Node(
    var row: Int,
    var col: Int,
    var height: Int,
    var distance: Int = Integer.MAX_VALUE,
    var shortestPath: LinkedList<Node> = LinkedList(),
    var adjacentNodes: MutableMap<Node, Int> = mutableMapOf()
  ) : Comparable<Node> {
    fun addAdjacentNode(node: Node, weight: Int) {
      adjacentNodes[node] = weight
    }

    override fun compareTo(other: Node): Int {
      return Integer.compare(distance, other.distance)
    }

    override fun toString(): String {
      return "Node(row=$row, col=$col, height=$height)"
    }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Node

    if (row != other.row) return false
    if (col != other.col) return false

    return true
  }

  override fun hashCode(): Int {
    var result = row
    result = 31 * result + col
    return result
  }

}

  fun evaluateDistanceAndPath(adjacentNode: Node, edgeWeight: Int, sourceNode: Node ) {
    val newDistance = sourceNode.distance + edgeWeight
    if(newDistance < adjacentNode.distance) {
      adjacentNode.distance = newDistance
      val shortestPath = LinkedList<Node>()
      shortestPath.addAll(sourceNode.shortestPath)
      shortestPath.add(sourceNode)
      adjacentNode.shortestPath = shortestPath
    }
  }

  fun calculateShortestPath(source: Node) : Set<Node> {
    source.distance = 0
    var settled = hashSetOf<Node>()
    var unsettled = PriorityQueue<Node>()
    unsettled.add(source)
    while(unsettled.isNotEmpty()) {
      var currentNode = unsettled.poll()
      currentNode.adjacentNodes.entries.filter {
        !settled.contains(it.key) && !unsettled.contains(it.key)
      }.forEach {
        evaluateDistanceAndPath(it.key, it.value, currentNode)
        unsettled.add(it.key)
      }
      settled.add(currentNode)
      println("unsettled: ${unsettled.size} settled ${settled.size}")
    }
    return settled

  }

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
//
//  fun getNeighbors(r: Int, c: Int, maze: List<List<Int>>) : MutableList<Point> {
//    val height = maze.size
//    val width = maze[0].size
//    var neighbors = mutableListOf<Point>()
//
//    if(r + 1 < height) {
//      neighbors.add((Point(r + 1, c, maze[r + 1][c])))
//    }
//    if(r - 1 >= 0) {
//      neighbors.add((Point(r - 1, c, maze[r - 1][c])))
//    }
//    if(c + 1 < width) {
//      neighbors.add((Point(r, c + 1, maze[r][c + 1])))
//    }
//    if(c - 1 >= 0) {
//      neighbors.add(Point(r , c - 1, maze[r][c - 1]))
//    }
//
//    return neighbors
//  }

  fun getNeighbors(r: Int, c: Int, maze: List<List<Int>>, locations: MutableList<Node>) : MutableList<Node> {
    val height = maze.size
    val width = maze[0].size
    var neighbors = mutableListOf<Node>()

    if(r + 1 < height && maze[r][c] >= maze[r+1][c] - 1) {
//      neighbors.add((Node(r + 1, c, maze[r + 1][c])))
      neighbors.add(locations.find { it.row == r + 1 && it.col == c }!!)
    }
    if(r - 1 >= 0 && maze[r][c] >= maze[r-1][c] - 1) {
//      neighbors.add((Node(r - 1, c, maze[r - 1][c])))
      neighbors.add(locations.find { it.row == r - 1 && it.col == c }!!)
    }
    if(c + 1 < width && maze[r][c] >= maze[r][c+1] - 1) {
//      neighbors.add((Node(r, c + 1, maze[r][c + 1])))
      neighbors.add(locations.find { it.row == r && it.col == c + 1 }!!)
    }
    if(c - 1 >= 0 && maze[r][c] >= maze[r][c-1] - 1) {
//      neighbors.add(Node(r , c - 1, maze[r][c - 1]))
      neighbors.add(locations.find { it.row == r && it.col == c - 1 }!!)
    }
    return neighbors
  }

  fun part1(input: List<String>): Int {
    val START = 'S'.code
    val END = 'E'.code
    val maze = input.map {
      it.windowed(1).map { it.first().toChar().code }.toMutableList()
    }.toMutableList()

    val locations = mutableListOf<Node>()
    maze.forEachIndexed { r, nums ->
      nums.forEachIndexed { c, value ->
        locations.add(Node(r, c, maze[r][c]))
      }
    }

    val start = locations.find { it.height == START }!!
    start.height = 'a'.code
    maze[start.row][start.col] = 'a'.code
    val end = locations.find { it.height == END }!!
    end.height = 'z'.code
    maze[end.row][end.col] = 'z'.code
    locations.forEach {
      val neighbors = getNeighbors(it.row, it.col, maze, locations)
//      println(neighbors)
      val neighborMap = neighbors.associateWith { 1 }.toMutableMap()
      it.adjacentNodes = neighborMap
    }

//    println(locations)
    for(location in locations) {
      println("node: ${location.row} ${location.col} ${location.adjacentNodes}")
    }

    val settled = calculateShortestPath(start)
    val endNode = settled.find {it.row == end.row && it.col == end.col}!!
    println("path: ${endNode.shortestPath} distance: ${endNode.distance}")
//    println(settled.find{ it.height == END}?.shortestPath?.size)
//    var count = 'a'
//    for(row in maze.indices) {
//      for(col in maze[0].indices) {
//        if(endNode.shortestPath.find { (it.row == row) && (it.col == col) } != null) {
//          var node = endNode.shortestPath.find { (it.row == row) && (it.col == col) }
//          print(count+endNode.shortestPath.indexOf(node))
//        } else {
//          print(".")
//        }
//      }
//      println()
//    }

    return endNode.distance
  }

//  fun part1(input: List<String>): Int {
//    val START = 'S'.code
//    val END = 'E'.code
//    val maze = input.map {
//      it.windowed(1).map { it.first().toChar().code }.toMutableList()
//    }.toMutableList()
//
//    val locations = mutableListOf<Point>()
//    maze.forEachIndexed { r, nums ->
//      nums.forEachIndexed { c, value ->
//        locations.add(Point(r, c, value))
//      }
//    }
//    val (startRow, startCol) = locations.filter { it.height == START }[0].getPos()
//    locations.filter { it.height == START }[0].height = 'a'.code
//
//
//    // you start at a
//    maze[startRow][startCol] = 'a'.code
//
//    val distances = mutableMapOf<Point, Int>()
////    val precedingPoints = mutableMapOf<Point, Point?>()
//
//    locations.forEach {
//      distances[it] = Int.MAX_VALUE
////      precedingPoints[it] = null
//    }
//
//    // set up start node
//    distances[locations[0]] = 0
//    val start = locations.find { it.row == startRow && it.col == startCol }!!
//    val dest = locations.find { it.height == END }
//    val locationQueue = mutableListOf<Point>()
//    locationQueue.add(start)
//
//    val visited = mutableListOf<Point>()
//    visited.add(start)
////    val finalPath = mutableListOf<Point>()
//    while(locationQueue.isNotEmpty()) {
//      val loc = locationQueue.removeAt(0)
//      println(loc)
//      for(pos in getNeighbors(loc.row, loc.col, maze).filter { !visited.contains(it) }) {
//        if(pos.parent == null && loc.height >= pos.height - 1 && !visited.contains(pos)) {
//          distances[pos] = distances[loc]!! + 1
//          pos.parent = loc
//          locationQueue.add(pos)
//          visited.add(pos)
//          if(pos.height == END) {
//            break
//          }
//        }
//      }
//    }
//    val path = mutableListOf<Point>()
//    path.add(dest!!)
//    var prev = dest.parent
//    while(prev != null) {
//      path.add(prev)
//      prev = prev.parent
//    }
//    println(path)
////    println(distances[locations.filter { it.height == END }[0]])
//
//
//    return 0
//  }


  fun part2(input: List<String>): Int {
    val START = 'S'.code
    val END = 'E'.code
    val maze = input.map {
      it.windowed(1).map { it.first().toChar().code }.toMutableList()
    }.toMutableList()

    val locations = mutableListOf<Node>()
    maze.forEachIndexed { r, nums ->
      nums.forEachIndexed { c, value ->
        locations.add(Node(r, c, maze[r][c]))
      }
    }

    val start = locations.find { it.height == START }!!
    start.height = 'a'.code
    maze[start.row][start.col] = 'a'.code
    val end = locations.find { it.height == END }!!
    end.height = 'z'.code
    maze[end.row][end.col] = 'z'.code
    locations.forEach {
      val neighbors = getNeighbors(it.row, it.col, maze, locations)
//      println(neighbors)
      val neighborMap = neighbors.associateWith { 1 }.toMutableMap()
      it.adjacentNodes = neighborMap
    }

//    println(locations)
    for(location in locations) {
      println("node: ${location.row} ${location.col} ${location.adjacentNodes}")
    }

    val possibleStarts = locations.filter{ it.height == 'a'.code }
    val distances = mutableListOf<Int>()
    for(possibleStart in possibleStarts) {
      val settled = calculateShortestPath(possibleStart)
      val endNode = settled.find {it.row == end.row && it.col == end.col}
      if(endNode != null) {
        distances.add(endNode.distance)
      }
    }

//    println("path: ${endNode.shortestPath} distance: ${endNode.distance}")
//    println(settled.find{ it.height == END}?.shortestPath?.size)
//    var count = 'a'
//    for(row in maze.indices) {
//      for(col in maze[0].indices) {
//        if(endNode.shortestPath.find { (it.row == row) && (it.col == col) } != null) {
//          var node = endNode.shortestPath.find { (it.row == row) && (it.col == col) }
//          print(count+endNode.shortestPath.indexOf(node))
//        } else {
//          print(".")
//        }
//      }
//      println()
//    }

    return distances.min()
  }



  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day12_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day12")
//  output(part1(input))
  output(part2(input))
}
