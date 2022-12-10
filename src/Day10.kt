fun main() {


  fun part1(input: List<String>): Int {
    var x = 1
    var cycle = 1
    var addValue = 0
    var adding = false

    val cycleValues = mutableListOf<Int>()
    var i = 0
    while (i < input.size) {
      if (adding) {
        x += addValue
        adding = false
        i++
      } else if (input[i] == "noop") {
        // do nothing
        i++
      } else {
        val (command, value) = input[i].split(" ")
        if (command == "addx") {
          adding = true
          addValue = value.toInt()
          println("x: $x cycle: $cycle addvalue: $addValue adding $adding i $i")
        }
      }

      cycle++

      if ((cycle + 20) % 40 == 0) {
        cycleValues.add(x * cycle)
      }

    }
    println(cycleValues)
    return cycleValues.sum()
  }


  fun part2(input: List<String>): Int {
    var x = 1
    var cycle = 0
    var addValue = 0
    var adding = false
    var screen = mutableListOf<String>()
    var line = ""

    var i = 0
    while (i < input.size) {
      cycle++
      if(cycle - 1 in x-1..x+1) {
//      if(x >= cycle - 1 && x <= cycle + 1) {
        line += "█"
      } else {
        line += " "
      }

      if(cycle >= 40) {
        screen.add(line)
        line = ""
        cycle = 0
      }

      if (adding) {
        x += addValue
        adding = false
        i++
      } else if (input[i] == "noop") {
        // do nothing
        i++
      } else {
        val (command, value) = input[i].split(" ")
        if (command == "addx") {
          adding = true
          addValue = value.toInt()
        }
      }



    }
    for(row in screen) {
      println(row)
    }
    return 0
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day10_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day10")
//  output(part1(input))
  output(part2(input))
}
