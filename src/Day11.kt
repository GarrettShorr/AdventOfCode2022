fun main() {
  fun factorize(number: Int): MutableMap<Int, Int> {
    var num = number
    var factor = 2
    var map = mutableMapOf<Int, Int>()
    while (num > 1) {
      if (num % factor == 0) {
        map[factor] = map.getOrDefault(factor, 0) + 1
        num /= factor
      } else {
        factor++
      }
    }
    return map
  }


  // returns value and to which monkey
  fun processItem(item: Int, operation: String, test: String, ifTrue: Int, ifFalse: Int): Pair<Int, Int> {
    val (first, op, second) = operation.substring(operation.indexOf("= ") + 2).split(" ")
    var num1 = 0
    var num2 = 0
    num1 = if (first.trim() == "old") {
      item
    } else {
      first.toInt()
    }
    num2 = if (second.trim() == "old") {
      item
    } else {
      second.trim().toInt()
    }
    var total = when (op) {
      "+" -> num1 + num2
      "*" -> num1 * num2

      else -> -1
    }


    var numToTest = test.substring(test.indexOf("by ") + 3).toInt()
    if (total % numToTest == 0) {
      return Pair(total, ifTrue)
    } else {
      return Pair(total, ifFalse)
    }
  }

  fun processItemRemainders(item: List<Int>, monkey: Int, operation: String, tests: MutableList<Int>, ifTrue: Int, ifFalse: Int): Pair<List<Int>, Int> {
    val (first, op, second) = operation.substring(operation.indexOf("= ") + 2).split(" ")
    var num1 = mutableListOf<Int>()
    var num2 = mutableListOf<Int>()
    if (first.trim() == "old") {
      num1 = item as MutableList<Int>
    } else {
      for(remainder in item) {
        num2.add(first.toInt())
      }

    }
    if (second.trim() == "old") {
      num2 = item as MutableList<Int>
    } else {
      for(remainder in item) {
        num2.add(second.trim().toInt())
      }
    }

    val total = when (op) {
      "+" -> {
        num1.zip(num2).map { it.first + it.second }
      }
      "*" -> {
        num1.zip(num2).map { it.first * it.second }
      }
      else -> mutableListOf()
    }.mapIndexed { index, num -> num % tests[index] }


////    if(total/3 == 694) {
//      println("$item $operation $test $num1 $op $num2")
//    }

    if (total[monkey] == 0) {
      return Pair(total, ifTrue)
    } else {
      return Pair(total, ifFalse)
    }
  }

//  fun processItemMap(item: MutableMap<Int, Int>, operation: String, test: String, ifTrue: Int, ifFalse: Int): Pair<MutableMap<Int, Int>, Int> {
//    val (first, op, second) = operation.substring(operation.indexOf("= ") + 2).split(" ")
//    var num1 = mutableMapOf<Int, Int>()
//    var num2 = mutableMapOf<Int, Int>()
//    if (first.trim() == "old") {
//      num1 = item
//    } else {
//      num1 = factorize(first.trim().toInt())
//    }
//    if (second.trim() == "old") {
//      num2 = item
//    } else {
//      num2 = factorize(second.trim().toInt())
//    }
//    var total = when (op) {
//      "+" -> {
//        for(item in num2.keys) {
//          num1[item] = num1.getOrDefault(item, 0) + num2[item]!!
//        }
//      }
//      "*" -> {
//        num1 * num2
//      }
//      else -> -1
//    }
//////    if(total/3 == 694) {
////      println("$item $operation $test $num1 $op $num2")
////    }
//
//    var numToTest = test.substring(test.indexOf("by ") + 3).toInt()
//    if (total % numToTest == 0) {
//      return Pair(total, ifTrue)
//    } else {
//      return Pair(total, ifFalse)
//    }
//  }

  fun processTrueFalse(lines: Pair<String, String>): Pair<Int, Int> {
    var ifTrue = lines.first.substring(lines.first.indexOf("monkey ") + "monkey ".length).toInt()
    var ifFalse = lines.second.substring(lines.second.indexOf("monkey ") + "monkey ".length).toInt()
    return Pair(ifTrue, ifFalse)
  }

  fun parseMonkeys(input: List<String>): MutableList<MutableList<Int>> {

    var lastMonkey = 0
    val monkeys = mutableListOf<MutableList<Int>>()
    for (line in input) {
      if (line.startsWith("Monkey")) {
        lastMonkey = line.split(" ")[1].substring(0, line.split(" ")[1].lastIndex).toInt()
      } else if (line.trim().startsWith("Starting")) {
        var items = line.split(":")[1].trim().split(", ").map { it.toInt() }.toMutableList()
        monkeys.add(items)
      } else {
        continue
      }
    }
    return monkeys
  }

  fun parseMonkeysToRemainders(input: List<String>, tests: List<Int>): MutableList<MutableList<MutableList<Int>>> {

    val monkeys = mutableListOf<MutableList<MutableList<Int>>>()
    for (line in input) {
      if (line.trim().startsWith("Starting")) {
        var items = line.split(":")[1].trim().split(", ").map { it.toInt() }.toMutableList()
        var remainders = mutableListOf<MutableList<Int>>()
        for(item in items) {
          remainders.add(mutableListOf())
          for(test in tests) {
            remainders.last().add(item % test)
          }
        }
        monkeys.add(remainders)
      } else {
        continue
      }
    }
    return monkeys
  }

  fun parseTests(input: List<String>): MutableList<Int> {
    var tests = mutableListOf<Int>()

    for (line in input) {
      if (line.trim().startsWith("Test")) {
        var test = line.split(": ")[1]
        tests.add(test.substring(test.indexOf("by ") + 3).toInt())
      }
    }
    return tests

  }



  fun fetchFactorMap(items: MutableList<Int>): MutableList<MutableMap<Int, Int>> {
    val maps = mutableListOf<MutableMap<Int, Int>>()
    for (item in items) {
      maps.add(factorize(item))
    }
    return maps
  }

  fun parseMonkeysMap(input: List<String>): MutableList<MutableList<MutableMap<Int, Int>>> {
    val monkeys = mutableListOf<MutableList<MutableMap<Int, Int>>>()
    for (line in input) {
      if (line.trim().startsWith("Starting")) {
        var items = line.split(":")[1].trim().split(", ").map { it.toInt() }.toMutableList()
        monkeys.add(fetchFactorMap(items))
      } else {
        continue
      }
    }
    return monkeys
  }


  fun parseOpsAndTests(input: List<String>): Triple<MutableList<String>, MutableList<String>, MutableList<Pair<Int, Int>>> {
    var ops = mutableListOf<String>()
    var tests = mutableListOf<String>()
    var trueFalses = mutableListOf<Pair<Int, Int>>()
    for (line in input) {
      if (line.trim().startsWith("Operation")) {
        ops.add(line.split(": ")[1])
      }
      if (line.trim().startsWith("Test")) {
        tests.add(line.split(": ")[1])
      }
    }
    for (i in input.indices) {
      if (input[i].trim().startsWith("If true")) {
        trueFalses.add(processTrueFalse(Pair(input[i], input[i + 1])))
      }
    }
    return Triple(ops, tests, trueFalses)
  }

  fun part1(input: List<String>): Int {
    val monkeys = parseMonkeys(input)
    println(monkeys)
    val inspections = mutableListOf<Int>()
    for (i in monkeys.indices) {
      inspections.add(0)
    }
    val otherStuff = parseOpsAndTests(input)
    for (round in 1..20) {
      for (i in monkeys.indices) {
        var j = 0
        while (monkeys[i].size > 0) {
          var op = otherStuff.first[i]
          var test = otherStuff.second[i]
          var results = otherStuff.third[i]
          var total = processItem(monkeys[i][j], op, test, results.first, results.second)
          monkeys[i].removeAt(j)
          if (total.first == 694) {
//          println("here: ${monkeys[i][j]} total: $results")
          }
          monkeys[total.second].add(total.first)
          inspections[i]++
        }
//      println(monkeys)
      }
    }
//    println(inspections)
    return inspections.sortedDescending().take(2).reduce { a, b -> a * b }
  }


  fun part2(input: List<String>): Long {
    val tests = parseTests(input)
    val monkeys = parseMonkeysToRemainders(input, tests)
    println(monkeys)
    val inspections = mutableListOf<Long>()
    for(i in monkeys.indices) {
      inspections.add(0)
    }
    val otherStuff = parseOpsAndTests(input)
    for(round in 1..10000) {
      for(i in monkeys.indices) {
        var j = 0
        while(monkeys[i].size > 0) {
          var op = otherStuff.first[i]
          var test = otherStuff.second[i]
          var results = otherStuff.third[i]
          var total = processItemRemainders(monkeys[i][j], i, op, tests, results.first, results.second)
          monkeys[i].removeAt(j)

          monkeys[total.second].add(total.first as MutableList<Int>)
          inspections[i]++
        }
//        println(monkeys)
      }}
    println(inspections)
    return inspections.sortedDescending().take(2).reduce { a, b -> a * b }
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day11_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day11")
//  output(part1(input))
  output(part2(input))
}
