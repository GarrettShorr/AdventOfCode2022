fun main() {
  fun findNumCrates(input: List<String>): Int {
    for(line in input) {
      if(line.startsWith(" 1")) {
        val nums = line.split(" ")
        return nums.last().toInt()
      }
    }
    return 0
  }

  fun fillCrates(crates: MutableList<MutableList<String>>, input: List<String>) {
      for(line in input) {
        if(line.startsWith(" 1")) { return }
        // 3 digits then a space
        for(i in line.indices step 4) {
          val crate = line.substring(i+1, i+2)
          if(!crate.startsWith(" ")) {
            crates[i/4].add(crate)
          }
        }
      }
  }

  fun parseMoves(input: List<String>): MutableList<MutableList<Int>> {
    val moves = mutableListOf<MutableList<Int>>()
    for(line in input) {
      if(!line.startsWith("move")) {
        continue
      }
      val instructions = line.split(" ") as MutableList
      instructions.remove("move")
      instructions.remove("from")
      instructions.remove("to")
      val move = instructions.map { it.toInt() } as MutableList
      move[1]--
      move[2]--
      moves.add(move)
    }
    return moves
  }

  fun part1(input: List<String>): String {
    // find the numbers first to find out how many crates
    val numCrates = findNumCrates(input)
    val crates = mutableListOf<MutableList<String>>()
    for(i in 0 until numCrates) {
      crates.add(mutableListOf())
    }
    // read the crates. 3 digits then space until the last 3 digits
    fillCrates(crates, input)
    println(numCrates)
    println(crates)

    // read and parse the moves
    val moves = parseMoves(input)
    println(moves)

    // execute instructions
    for(move in moves) {
      for(i in 1..move[0]) {
        crates[move[2]].add(0, crates[move[1]].removeAt(0))
      }
    }

    var top = ""
    for(crate in crates) {
      top += crate[0]
    }
    return top
  }


  fun part2(input: List<String>): String {
    // find the numbers first to find out how many crates
    val numCrates = findNumCrates(input)
    val crates = mutableListOf<MutableList<String>>()
    for(i in 0 until numCrates) {
      crates.add(mutableListOf())
    }
    // read the crates. 3 digits then space until the last 3 digits
    fillCrates(crates, input)
    println(numCrates)
    println(crates)

    // read and parse the moves
    val moves = parseMoves(input)
    println(moves)

    // execute instructions
    for(move in moves) {
      val cratesStack = mutableListOf<String>()
      for(i in 1..move[0]) {
        cratesStack.add(crates[move[1]].removeAt(0))
      }
      for(i in cratesStack.indices) {
        crates[move[2]].add(i, cratesStack[i])
      }
    }


    var top = ""
    for(crate in crates) {
      top += crate[0]
    }
    return top
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day05")
//  output(part1(input))
  output(part2(input))
}
