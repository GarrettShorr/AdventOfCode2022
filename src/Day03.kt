fun main() {
  fun part1(input: List<String>): Int {
    val LOWER_OFFSET = 97-1
    val UPPER_OFFSET = 65-27
    var total = 0
    for(line in input) {
      val firstHalf = line.substring(0, line.length/2)
      val secondHalf = line.substring(line.length/2)
      val shared = findSharedItem(firstHalf, secondHalf)
      var char = 0
      if(shared.length > 0) {
        char = shared.toCharArray()[0].code
      }
      println("shared: $shared char: $char")
      total += if(char > 90) {
        char - LOWER_OFFSET
      } else {
        char - UPPER_OFFSET
      }
    }

    return total
  }

  fun part2(input: List<String>): Int {
    val LOWER_OFFSET = 'a'.code -1
    val UPPER_OFFSET = 'A'.code -27
    var total = 0
    for(i in 0 .. input.size - 3 step 3) {
      val (elf1, elf2, elf3) = input.subList(i, i + 3)
      val shared = findSharedItem(elf1, elf2, elf3)
      var char = 0
      if(shared.length > 0) {
        char = shared.toCharArray()[0].code
      }
      println("shared: $shared char: $char")
      if(char > 90) {
        total += char - LOWER_OFFSET
      } else {
        total += char - UPPER_OFFSET
      }
    }
    return total
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day03")
//  output(part1(input))
  output(part2(input))
}

fun findSharedItem(str1: String, str2: String) : String{
  var same = ""
  for(i in str1.indices) {
    for(j in str2.indices) {
      if(str1.substring(i, i+1) == str2.substring(j, j+1)) {
        return str1.substring(i, i + 1)
      }
    }
  }
  return ""
}

fun findSharedItem(str1: String, str2: String, str3: String) : String{
  for(i in str1.indices) {
    for (j in str2.indices) {
      for (k in str3.indices) {
        if (str1.substring(i, i + 1) == str2.substring(j, j + 1) && str1.substring(i, i + 1) == str3.substring(k, k + 1)) {
          return str1.substring(i, i + 1)
        }
      }
    }
  }
  return ""
}