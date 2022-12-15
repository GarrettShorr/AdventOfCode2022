import com.beust.klaxon.Klaxon
import kotlin.reflect.typeOf

fun main() {


    fun isCorrect(leftList: MutableList<Any>?, rightList: MutableList<Any>?): Boolean? {
        println("$leftList  vs.  $rightList")
        if(leftList == null && rightList == null) {
            return null
        }
        if(leftList?.isEmpty() == true && !rightList?.isEmpty()!!) {
            return true
        }
        if(rightList?.isEmpty() == true && !leftList?.isEmpty()!!) {
            return false
        }

        var left = leftList?.get(0)
        var right = rightList?.get(0)

        //both list
        if(left is List<*> && right is List<*>) {
            if(left.isEmpty() && !right.isEmpty()) {
                return true
            } else if(!left.isEmpty() && right.isEmpty()) {
                return false
            } else {
                return isCorrect(left as MutableList<Any>?, right as MutableList<Any>?)
            }
        }

        // both string
        if(left is Int && right is Int)  {
            if(left == right) {
                leftList?.removeAt(0)
                rightList?.removeAt(0)
                return isCorrect(leftList, rightList)
            } else {
                return left < right
            }
        }

        // 1 of each
        if(left is String && right is List<*>) {
            left = listOf(left)
            leftList?.set(0, left)
            return isCorrect(leftList, rightList)
        }

        // 1 empty
        return false
    }

    fun part1(input: List<String>): Int {
        val parser = Klaxon()
        var instructions = mutableListOf<List<Any>>()
        for(line in input) {
            if(line != "") {
                instructions.add(parser.parseArray<List<Any>>(line)!!)
            }
        }
        instructions = instructions.windowed(2).toMutableList()
//        println(instructions.joinToString("\n"))

        var count = 0
        var indexTotal = 0
        for(i in instructions.indices step 2) {
            println("original pair: ${instructions[i]} ")
            if(isCorrect(instructions[i][0] as MutableList<Any>, instructions[i][1] as MutableList<Any>) == true) {
                count++
                indexTotal += i
                println("pair: ${instructions[i]}  is correct.  count: $count i: $i")

            }

        }
        return indexTotal
    }


    fun part2(input: List<String>): Int {

        return 0
    }




    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
  println(part1(testInput))
//    println(part2(testInput))

    val input = readInput("Day13")
//  output(part1(input))
//    output(part2(input))
}
