fun main() {
  fun part1(input: List<String>): Int {
    val forest = input.map { it.chunked(1).map { it.toInt() } }
//    val forest2 = input.map { it.map { it.digitToInt() } }
    println(forest)
    val visible = mutableSetOf<Pair<Int, Int>>()
    val width = forest[0].size
    val height = forest.size


    //outside edges
    for(i in forest[0].indices) {
      visible.add(Pair(0, i))
      visible.add(Pair(height-1, i))
    }
    for(i in forest.indices) {
      visible.add(Pair(i, 0))
      visible.add(Pair(i, width - 1))
    }

    // looking left, right, top, bottom
    for(row in 1 until forest.size) {
      var tallest = forest[row][0]
      for(col in 1 until forest[0].size) {
        if(forest[row][col] > tallest) {
          visible.add(Pair(row, col))
          tallest = forest[row][col]
        }
      }
    }

    for(row in 1 until forest.size) {
      var tallest = forest[row][width-1]
      for(col in forest[0].size-1 downTo 1) {
        if(forest[row][col] > tallest) {
          visible.add(Pair(row, col))
          tallest = forest[row][col]
        }
      }
    }

    for(col in 1 until forest[0].size) {
      var tallest = forest[0][col]
      for(row in 1 until forest.size) {
        if(forest[row][col] > tallest) {
          visible.add(Pair(row, col))
          tallest = forest[row][col]
        }
      }
    }

    for(col in 1 until forest[0].size) {
      var tallest = forest[height-1][col]
      for(row in forest.size-1 downTo  1) {
        if(forest[row][col] > tallest) {
          visible.add(Pair(row, col))
          tallest = forest[row][col]
        }
      }
    }

    return visible.size
  }


  fun part2(input: List<String>): Int {
    val forest = input.map { it.chunked(1).map { it.toInt() } }

    val width = forest[0].size
    val height = forest.size


    var maxScore = 0
    for(row in forest.indices) {
      for(col in forest.indices) {
        val treeHeight = forest[row][col]
        var count = 0
        var score = 1
        // look up
        for(r in row-1 downTo 0) {
          count++
          if(forest[r][col] >= treeHeight) {
            break
          }
        }
        score *= count


        count = 0
        // look down
        for(r in row+1 until height) {
          count++
          if(forest[r][col] >= treeHeight) {
            break
          }
        }
        score *= count
        println("row $row col $col tree: ${forest[row][col]} score: $score count: $count")


        count = 0
        // look left
        for(c in col-1 downTo 0) {
          count++
          if(forest[row][c] >= treeHeight) {
            break
          }
        }
        score *= count

        count = 0
        // look right
        for(c in col+1 until width) {
          count++
          if(forest[row][c] >= treeHeight) {
            break
          }
        }
        score *= count

        if(score > maxScore) {
          maxScore = score
        }
      }
    }




    return maxScore
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day08_test")
  println(part1(testInput))
//  println(part2(testInput))

  val input = readInput("Day08")
//  output(part1(input))
//  output(part2(input))
}
