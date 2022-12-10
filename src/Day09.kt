import kotlin.math.abs

fun main() {


  fun moveTail(frontX: Int, frontY: Int, tail: Pair<Int, Int>) : Pair<Int, Int> {
    var (tailX, tailY) = tail
    // diagonal move
    if(frontX != tailX && frontY != tailY) {
      if(abs(frontX - tailX) == 2 || abs(frontY - tailY) == 2) {
        if(frontX < tailX) {
          tailX--
        } else {
          tailX++
        }
        if(frontY > tailY) {
          tailY++
        } else {
          tailY--
        }
      }
    } else if(frontX - tailX == 2) {
      tailX++
    } else if(tailX - frontX == 2) {
      tailX--
    } else if(frontY - tailY == 2) {
      tailY++
    } else if(tailY - frontY == 2) {
      tailY--
    }
    return Pair(tailX, tailY)
  }


  fun part1(input: List<String>): Int {
    var head = Pair(0, 0)
    var tail = Pair(0, 0)
    var tailVisited = mutableSetOf<Pair<Int, Int>>()
    tailVisited.add(tail)

    for(line in input) {
      val (dir, distance) = line.split(" ")
      var (headX, headY) = head
      when(dir) {
        "U" -> {
          headY += distance.toInt()
          for(i in head.second + 1 .. headY) {
            tail = moveTail(headX, i, tail)
            tailVisited.add(tail)
          }
        }
        "D" -> {
          headY -= distance.toInt()
          for(i in head.second - 1 downTo  headY) {
            tail = moveTail(headX, i, tail)
            tailVisited.add(tail)
          }
        }
        "L" -> {
          headX -= distance.toInt()
          for(i in head.first - 1 downTo  headX) {
            tail = moveTail(i, headY, tail)
            tailVisited.add(tail)
          }
        }
        "R" -> {
          headX += distance.toInt()
          for(i in head.first + 1 .. headX) {
            tail = moveTail(i, headY, tail)
            tailVisited.add(tail)
          }
        }
      }
      head = Pair(headX, headY)
      println("head: $head tail $tail")



      // check the tail
    }
    return tailVisited.size
  }


  fun part2(input: List<String>): Int {
    var head = Pair(0, 0)
    var tails = mutableListOf<Pair<Int, Int>>()
    for(i in 1..9) {
      tails.add(Pair(0, 0))
    }
    var tailVisited = mutableSetOf<Pair<Int, Int>>()
    tailVisited.add(tails.last())

    for(line in input) {
      val (dir, distance) = line.split(" ")
      var (headX, headY) = head
      when(dir) {
        "U" -> {
          headY += distance.toInt()
          for(i in head.second + 1 .. headY) {
            tails[0] = moveTail(headX, i, tails[0])
            for(j in 1 until tails.size) {
              tails[j] = moveTail(tails[j-1].first,tails[j-1].second, tails[j])
            }
            tailVisited.add(tails.last())
          }
        }
        "D" -> {
          headY -= distance.toInt()
          for(i in head.second - 1 downTo  headY) {
            tails[0] = moveTail(headX, i, tails[0])
            for(j in 1 until tails.size) {
              tails[j] = moveTail(tails[j-1].first,tails[j-1].second, tails[j])
            }
            tailVisited.add(tails.last())
          }
        }
        "L" -> {
          headX -= distance.toInt()
          for(i in head.first - 1 downTo  headX) {
            tails[0] = moveTail(i, headY, tails[0])
            for(j in 1 until tails.size) {
              tails[j] = moveTail(tails[j-1].first, tails[j-1].second, tails[j])
            }
            tailVisited.add(tails.last())
          }
        }
        "R" -> {
          headX += distance.toInt()
          for(i in head.first + 1 .. headX) {
            tails[0] = moveTail(i, headY, tails[0])
            for(j in 1 until tails.size) {
              tails[j] = moveTail(tails[j-1].first, tails[j-1].second, tails[j])
            }
            tailVisited.add(tails.last())
          }
        }
      }
      head = Pair(headX, headY)
//      println("head: $head tails: $tails")



      // check the tail
    }
    return tailVisited.size
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day09_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day09")
//  output(part1(input))
  output(part2(input))
}