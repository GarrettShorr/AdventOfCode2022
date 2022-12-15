import kotlin.math.abs
import kotlin.properties.Delegates

fun main() {
  data class Point(val x: Int, val y: Int)  {
    fun manhattanDistanceTo(other: Point) : Int {
      return abs(x - other.x) + abs(y - other.y)
    }
  }

  data class Sensor(val location: Point, val nearestBeacon: Point) {
    var range by Delegates.notNull<Int>()
    init {
      range = location.manhattanDistanceTo(nearestBeacon)
    }

    override fun toString(): String {
      return "Sensor(location=$location, nearestBeacon=$nearestBeacon, range=$range)"
    }

  }


  fun part1(input: List<String>): Int {
    val Y = 2000000

    val sensorsText =  input.map { it.split(": closest beacon is at ") }.map {
      it.map { it.split(", ") }
    }
    val sensors = mutableListOf<Sensor>()
    sensorsText.forEach {
      sensors.add(Sensor(Point(it[0][0].split("=")[1].toInt(), it[0][1].split("=")[1].toInt()),
        Point(it[1][0].split("=")[1].toInt(), it[1][1].split("=")[1].toInt())))
    }

    var cantBeThere = 0
    for(x in -10_000_000..10_000_000) {
//    for(x in -50..50) {
//      println(sensors.count { Point(x, Y).manhattanDistanceTo(it.location) <= it.range })
      if(sensors.count { Point(x, Y).manhattanDistanceTo(it.location) <= it.range } >= 1) {
        cantBeThere++
      }
    }

    return cantBeThere - 1
  }


  fun part2(input: List<String>): Long {val Y = 2000000

    val sensorsText =  input.map { it.split(": closest beacon is at ") }.map {
      it.map { it.split(", ") }
    }
    val sensors = mutableListOf<Sensor>()
    sensorsText.forEach {
      sensors.add(Sensor(Point(it[0][0].split("=")[1].toInt(), it[0][1].split("=")[1].toInt()),
        Point(it[1][0].split("=")[1].toInt(), it[1][1].split("=")[1].toInt())))
    }

    sensors.sortBy { it.location.x }
    println(sensors)

//    var count = 0
//    var xx = 596122
//    var yy = 1712290
//    for(sensor in sensors) {
//      println("${Point(xx, yy).manhattanDistanceTo(sensor.location)} ${sensor.range}")
//    }
//    return 0
    var (realX, realY) = listOf(0, 0)
//    for(x in 0..20) {
//      for (y in 0..20) {
    var x = 0
    var y = 0

    sensors.sortBy { it.location.x }

    loop@ while (true) {
//      sensors.first { it.location.manhattanDistanceTo(Point(x, y)) <= it.range && it.location.x >= x }
      for(sensor in sensors) {
        var d = sensor.location.manhattanDistanceTo(Point(x, y))
        if (d <= sensor.range) {
          var dx = sensor.range - d
//          x = abs(x - dx - sensor.location.x) + sensor.location.x + 1
          x = sensor.range - abs(sensor.location.y - y) + sensor.location.x

          if (x >= 4000000) {
            x = 0
            y++
            continue@loop
          }
          println("x: $x y: $y ${sensors.count { it.location.manhattanDistanceTo(Point(x, y)) >= it.range }} " +
              "${x.toLong() * 4000000L + y.toLong()} sensors size: ${sensors.size}")

          if (sensors.count { it.location.manhattanDistanceTo(Point(x, y)) > it.range } == sensors.size) {
            return x.toLong() * 4000000L + y.toLong()
          }
        } else {
          x++

          println(
            "x: $x y: $y ${
              sensors.count {
                it.location.manhattanDistanceTo(
                  Point(
                    x,
                    y
                  )
                ) >= it.range
              }
            } ${x.toLong() * 4000000L + y.toLong()}")

          if (sensors.count { it.location.manhattanDistanceTo(Point(x, y)) > it.range } == sensors.size) {
            return x.toLong() * 4000000L + y.toLong()
          }
//          x++
//          if (x >= 4000000) {
//            x = 0
//            y++
//          }
//          continue@loop
        }
      }



    }



////      println(sensors.count { Point(x, Y).manhattanDistanceTo(it.location) <= it.range })
//        if (sensors.count { Point(x, y).manhattanDistanceTo(it.location) > it.range } >= sensors.size) {
//          realX = x
//          realY = y
//          break
//        } else {
//          continue
//        }
//      }
//    }
    println("x: $realX y: $realY")

    return x.toLong() * 4000000L + y.toLong()
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day15_test")
  println(part1(testInput))
//  println(part2(testInput))

  val input = readInput("Day15")
//  output(part1(input))
//  output(part2(input))
}

