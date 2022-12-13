fun main() {

  fun parseFirstNum(num: String): Int {
    var str = num
    var i = 0
    while(str[0] == '[' || str[0] == ',' || str[0] == ']') {
      str = str.substring(1)
    }
    if(str.count { it.isDigit() } == str.length) {
      return str.toInt()
    }
    while(str[i].isDigit()) {
      i++
    }
//    println("num $num str $str")
    return str.substring(0, i).toInt()
  }

  fun compare(leftSide: String, rightSide: String) : Boolean {
    println("leftside: $leftSide rightside: $rightSide")
    var left = leftSide
    var right = rightSide
    if(left.startsWith(",")) {
      left = left.substring(1)
    }
    if(right.startsWith(",")) {
      right = right.substring(1)
    }

    // both lists
    if(left.isEmpty() && right.isNotEmpty()) {
      return true
    }
    if(right.isEmpty() && left.isNotEmpty()) {
      return false
    }
    // don't think this should happen
    if(left.isEmpty() && right.isEmpty()) {
      throw(Exception("Both shouldn't be empty"))
    }



    if(left.startsWith("[]") && !right.startsWith("[]")) {
      return true
    }

    if(right.startsWith("[]") && !left.startsWith("[]")) {
      return false
    }

//    if(left.startsWith("[") && right.startsWith("[")) {
//      return compare(left.substring(1, left.length-1), right.substring(1, right.length-1))
//    }


//    if(left.startsWith("[[")) {
//      return compare(left.substring(1, left.indexOf("]")+ 1), right)
//    }
//
//    if(right.startsWith("[[")) {
//      return compare(left, right.substring(1, right.indexOf("]")+ 1))
//    }

    // one int, convert to list
    if(left.startsWith("[") && !right.startsWith("[")) {
      var nextCommma = right.indexOf(",")
      if(nextCommma > 0) {
        right = "[" + right.substring(0, nextCommma) + "]" + right.substring(nextCommma)
      } else {
        right = "[" + right + "]"
      }
      return compare(left, right)
    } else if(!left.startsWith("[") && right.startsWith("[")){
      var nextCommma = left.indexOf(",")
      if(nextCommma > 0) {
        left = "[" + left.substring(0, nextCommma) + "]" + left.substring(nextCommma)
      } else {
        left = "[" + left + "]"
      }
      return compare(left, right)
    }


//    if(left.startsWith("[") && right.startsWith("[") && left.endsWith("]") && right.endsWith("]")) {
    if(left.startsWith("[") && right.startsWith("[") && left.contains("]") && right.contains("]")) {
      var leftCount = 1
      var i = 1
      while(leftCount > 0) {
        if(left[i] == '[') {
          leftCount++
        } else if(left[i] == ']') {
          leftCount--
        }
        i++
      }
      var newLeft = if(i < left.length-1) {
        left.substring(1, i-1) + left.substring(i)
      } else {
        left.substring(1, i-1)
      }

      var rightCount = 1
      i = 1
      while(rightCount > 0) {
        if(right[i] == '[') {
          rightCount++
        } else if(right[i] == ']') {
          rightCount--
        }
        i++
      }
//      println("right $right i $i ${right[i]}")
      var newRight = if(i < right.length-1) {
        right.substring(1, i-1) + right.substring(i)
      } else {
        right.substring(1, i-1)
      }

      return compare(newLeft, newRight)
    }

    // both ints and only thing left
    if(left.count { it.isDigit() } == left.length && right.count { it.isDigit() } == right.length) {
      return left.toInt() < right.toInt()
    }

    if(left.startsWith("[[]") && !right.startsWith("[[]")) {
      return compare(left.substring(1), right)
    }

    if(!left.startsWith("[[]") && right.startsWith("[[]")) {
      return compare(left, right.substring(1))
    }

    // both ints and there's more left
    var leftNum = parseFirstNum(left)
    var rightNum = parseFirstNum(right)
    if(leftNum < rightNum) {
      return true
    }
    if(leftNum == rightNum) {
      if(left.length == leftNum.toString().length && right.length > rightNum.toString().length) {
        return true
      }
      if(left.length > leftNum.toString().length && right.length == rightNum.toString().length) {
        return false
      }
      var nextLeft = left.substring(leftNum.toString().length + 1)
      while (!nextLeft.startsWith("[") && !nextLeft.first().isDigit()) {
//        println("left $left nextleft $nextLeft")
        nextLeft = nextLeft.substring(1)
      }

      var nextRight = right.substring(rightNum.toString().length + 1)
      while (!nextRight.startsWith("[") && !nextRight.first().isDigit()) {
//        println("right $right nextright $nextRight")
        nextRight = nextRight.substring(1)
      }

      return compare(nextLeft, nextRight)
    }
    return false

  }

//  fun getNextItemAndRemaining(input: String) : Pair<String, String> {
//    // base case
//    if(input.length == 0 || (input.length == 1 && input == "]")) {
//      return Pair("", "")
//    }
//
//    if(input.first() == '[' || input.first() == ',') {
//      if(!input[1].isDigit()) {
//        return getNextItemAndRemaining(input.substring(1))
//      } else {
//        val nextOpenBracket = input.indexOf("[", 1)
//        val nextCloseBracket = input.indexOf("]", 1)
//        val nextComma = input.indexOf(",", 1)
//        val nextEndIndex = mutableListOf(nextOpenBracket, nextCloseBracket, nextComma).sorted().filter { it > 0 }.first()
//        return Pair(input.substring(1, nextEndIndex), input.substring(nextEndIndex))
//      }
//    }
//
//   if(input.first().isDigit()) {
//     val nextOpenBracket = input.indexOf("[", 1)
//     val nextCloseBracket = input.indexOf("]", 1)
//     val nextComma = input.indexOf(",", 1)
//     val nextEndIndex = mutableListOf(nextOpenBracket, nextCloseBracket, nextComma).sorted().filter { it > 0 }.first()
//     return Pair(input.substring(1, nextEndIndex), input.substring(nextEndIndex))
//   }
//    return Pair("", "")
//  }

//  fun rightOrder(group1: String, group2: String): Boolean {
//    var a = group1
//    var b = group2
//    var nextA = ""
//    var nextB = ""
//    while(a.isNotEmpty() && b.isNotEmpty()) {
//      var next1 = getNextItemAndRemaining(a)
//      var next2 = getNextItemAndRemaining(b)
//      a = next1.second
//      nextA = next1.first
//      b = next2.second
//      nextB = next2.first
//      println("nextA: $nextA nextB: $nextB a: $a b: $b")
//
////      if(nextA[0].isDigit() && nextB[0].isDigit())
//    }
//    return true
//  }

  fun part1(input: List<String>): Int {
    var rightOrder = 0
    var pair = 1
    var indices = mutableListOf<Int>()
    for(i in input.indices step 3) {
      val group1 = input[i]
      val group2 = input[i+1]
      if(compare(group1, group2)) {
        rightOrder++
        indices.add(pair)
      }
      println("correct so far: $rightOrder")
      pair++
    }
    return indices.sum()
  }


  fun part2(input: List<String>): Int {

    return 0
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day13_test")
  println(part1(testInput))
//  println(part2(testInput))

  val input = readInput("Day13")
  output(part1(input))
//  output(part2(input))
}

