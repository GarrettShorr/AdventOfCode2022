fun main() {

  data class Point(var x: Int, var y: Int)

  class Shape(var type: Int, val h: Int) {
    lateinit var locs : List<Point>

    init {
      when(type) {
        0 -> {
          locs = mutableListOf(Point(2, h), Point(3, h), Point(4, h), Point(5, h))
        }
        1 -> {
          locs = mutableListOf(Point(3, h+2), Point(2, h+1), Point(3, h+1),
            Point(4, h+1), Point(3, h))
        }
        2 -> {
          locs = mutableListOf(Point(4, h+2), Point(4, h+1), Point(2, h),
            Point(3, h), Point(4, h))
        }
        3 -> {
          locs = mutableListOf(Point(2, h+3), Point(2,h+2), Point(2,h+1), Point(2, h))
        }
        4 -> {
          locs = mutableListOf(Point(2, h+1), Point(3, h+1), Point(2, h), Point(3, h))
        }
        else -> {
          locs = mutableListOf()
          throw Exception("This shape sucks")
        }
      }
    }
    fun canMove(dx: Int, dy: Int, shapeList: List<Shape>, walls: Pair<Int, Int>) : Boolean {
//      println(locs.count { Point(it.x + dx, it.y + dy) in shapeList.flatMap { it.locs } } == 0)
      return locs.none { Point(it.x + dx, it.y + dy) in shapeList.flatMap { it.locs } } &&
          locs.none{ it.x + dx < walls.first}  && locs.none { it.x + dx > walls.second} &&
          locs.none { it.y + dy < 0 }
    }

    override fun toString(): String {
      return "Shape(type=$type, h=$h, locs=${locs.joinToString { "x: ${it.x} y: ${it.y}" }}"
    }


  }


  fun display(visual: MutableList<MutableList<Boolean>>) {
    var startDrawing = false
    for(row in visual) {
      if(!startDrawing && row.all { !it }) {
        continue
      } else {
        startDrawing = true
      }
      print("|")
      for(col in row) {
        print(if(col) "#" else ".")
      }
      print("|")
      println()
    }
    println("+-------+")
    println("$$$$$$$$$")
  }

  fun part1(input: List<String>): Int {
    val WALLS = Pair(0, 6)
    val SHAPE_VARIETIES = 5
    val DRAW_HEIGHT = 1000
    var currType = 0
    var dropHeight = 3
    var windex = 0
    val jets = input[0]
    val settledShapes = mutableListOf<Shape>()

    val visual = mutableListOf<MutableList<Boolean>>()
    for(r in 0..DRAW_HEIGHT) {
      val row = mutableListOf<Boolean>()
      for(c in 0..6) {
        row.add(false)
      }
      visual.add(row)
    }

    repeat(20) {
      val shape = Shape(currType, dropHeight)
      currType++
      currType %= SHAPE_VARIETIES
      var canMove = true
      var justBlew = false
      while(canMove) {
        for(r in 0..DRAW_HEIGHT) {
          for(c in 0..6) {
           visual[r][c] = false
          }
        }
        shape.locs.forEach { visual[DRAW_HEIGHT - it.y][it.x] = true }
        settledShapes.forEach { it.locs.forEach{ visual[DRAW_HEIGHT - it.y][it.x] = true } }
        display(visual)
        if(!justBlew) {
          justBlew = true
          var dx = 1
//          print(jets[windex])
          if(jets[windex] == '<') {
            dx = -1
//            println("blew left")
          }
          if(shape.canMove(dx, 0, settledShapes, WALLS)) {
            shape.locs.forEach { it.x += dx }
          }
          windex++
          windex %= jets.length
        } else {
          justBlew = false
          var dy = -1
          if(shape.canMove(0, dy, settledShapes, WALLS)) {
            shape.locs.forEach { it.y += dy }
          } else {
            canMove = false
          }
        }
//        println("windex: $windex $shape")
      }
      settledShapes.add(shape)
      dropHeight = shape.locs.maxOf { it.y } + 4
//      println("height: $dropHeight")
    }

    println("${settledShapes.size}${settledShapes.takeLast(5)}")
    return settledShapes.maxOf { it.locs.maxOf { it.y } }
  }


  fun part2(input: List<String>): Int {

    return 0
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day17_test")
  println(part1(testInput))
//  println(part2(testInput))

  val input = readInput("Day17")
//  output(part1(input))
//  output(part2(input))
}
