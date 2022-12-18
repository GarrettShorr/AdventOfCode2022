import java.lang.Math.*
import java.util.*

fun main() {
  class Valve(
    var name: String,
    var flowRate: Int,
    var isOpen: Boolean = false,
    var isOpening: Boolean = false,
    var totalPressureReleased: Int = Integer.MAX_VALUE,
    var mostFlowRate: LinkedList<Valve> = LinkedList(),
    var adjacentNodes: MutableMap<Valve, Int> = mutableMapOf(),
    var openedValves: MutableList<Valve> = mutableListOf()
  ) : Comparable<Valve> {
    fun addAdjacentNode(valve: Valve, weight: Int) {
      adjacentNodes[valve] = weight
    }

    override fun compareTo(other: Valve): Int {
      if(!other.isOpen) {
        return Integer.compare(totalPressureReleased, other.totalPressureReleased)
      } else {
        return (random()*2 - 3).toInt()
      }
    }

    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Valve

      if (name != other.name) return false
      if (flowRate != other.flowRate) return false
      if (isOpen != other.isOpen) return false
      if (totalPressureReleased != other.totalPressureReleased) return false

      return true
    }

    override fun hashCode(): Int {
      var result = name.hashCode()
      result = 31 * result + flowRate
      result = 31 * result + isOpen.hashCode()
      result = 31 * result + totalPressureReleased
      return result
    }

    fun printAdjacentNodeNames() {
       println("${this.name}: ${(adjacentNodes.keys).joinToString { it.name }}")
    }

    override fun toString(): String {
      return "Valve(name='$name', flowRate=$flowRate, isOpen=$isOpen, isOpening=$isOpening)"
    }


  }
  fun evaluateFlowRateAndPath(adjacentNode: Valve, edgeWeight: Int, sourceNode: Valve ) {
    val newDistance = sourceNode.totalPressureReleased + edgeWeight
    if(newDistance > adjacentNode.totalPressureReleased) {
      adjacentNode.totalPressureReleased = newDistance
      val mostFlowRatePath = LinkedList<Valve>()
      mostFlowRatePath.addAll(sourceNode.mostFlowRate)
      mostFlowRatePath.add(sourceNode)
      adjacentNode.mostFlowRate = mostFlowRatePath
    }
  }

  fun calculateLargestFlowrate(source: Valve) : Set<Valve> {
    source.totalPressureReleased = 0
    var settled = hashSetOf<Valve>()
    var unsettled = PriorityQueue<Valve>()
    unsettled.add(source)
    while(unsettled.isNotEmpty()) {
      var currentNode = unsettled.poll()
      currentNode.adjacentNodes.entries.forEach {
        evaluateFlowRateAndPath(it.key, it.value, currentNode)
        unsettled.add(it.key)
      }
      settled.add(currentNode)
      println("unsettled: ${unsettled.size } settled ${settled.joinToString { it.name }}")
    }
    return settled

  }


  fun part1(input: List<String>): Int {
    var valves = input
      .map { it.replace("Valve ", "") }
      .map { it.replace("has flow rate=", "") }
      .map { it.replace(";", "") }
      .map { it.replace("tunnel", "tunnels") }
      .map { it.replace("leads", "lead") }
      .map { it.replace("tunnels lead to valves ", "") }
      .map {
        val split = it.split(" ")
        Valve(split[0],split[1].toInt())
      }
    valves.forEachIndexed { i, valve ->
      val connectedValves = input.map { it.replace("valves", "valve") }
        .map { it.substring(it.indexOf("valve ") + "valve ".length) }[i]
        .split(", ")
      connectedValves.forEach { valve.addAdjacentNode(valves.find { v -> v.name == it }!!, 1) }
    }
//    println(valves)
    var bestLog = mutableListOf<String>()
    var logList = mutableListOf<String>()
//    val releasedPressures = mutableSetOf<Int>().toSortedSet(Collections.reverseOrder())
//    releasedPressures.add(0)
    var maxPressure = 0

    repeat(1_000_000_000) {
      var minute = 30
      var location = valves[0]
      var oldLocation = location
      var movingToAnotherRoom = true
      var releasedPressure = 0
      while(minute > 0) {
        if(movingToAnotherRoom) {
          oldLocation = location
          location = location.adjacentNodes.keys.random()
//          println("from: ${oldLocation.name} to possibly ${oldLocation.adjacentNodes.keys.joinToString{it.name}} but went to ${location.name}")
//          bestLog += "from: ${oldLocation.name} to possibly ${oldLocation.adjacentNodes.keys.joinToString{it.name}} but went to ${location.name}"
          if(!location.isOpen && location.flowRate>0 && random() < 0.5) {
            movingToAnotherRoom = false
          }
        } else {
          location.isOpen = true
          movingToAnotherRoom = true
          releasedPressure += location.flowRate * (minute - 1)
        }
        minute--
//        releasedPressure += valves.filter { it.isOpen }.sumOf { it.flowRate }
        logList.add("\ntime: $minute pressure: $releasedPressure location: ${location.name} openValves: " +
            "${valves.filter { it.isOpen }.joinToString{ it.name }} " +
//            "opening: ${valves.filter { it.isOpening }.joinToString { it.name }} total " +
            "flow: ${valves.filter { it.isOpen }.sumOf { it.flowRate }}")

//        valves.filter { it.isOpening }.forEach { it.isOpening = false; it.isOpen = true }

      }
      valves.forEach {
        it.isOpening = false
        it.isOpen = false
      }
      if(releasedPressure > maxPressure) {
        bestLog.clear()
        bestLog.addAll(logList)
        maxPressure = releasedPressure
      }
//      releasedPressures.add(releasedPressure)
//      if(it % 100000 == 0) {
//        for(i in releasedPressures.size-1 downTo 5) {
//          releasedPressures.remove(releasedPressures.last())
//        }
////        println(releasedPressures)
//      }

      logList.clear()
    }
    valves.forEach { println("${it.name}: ${it.adjacentNodes.keys.joinToString { it.name }} flowRate: ${it.flowRate}") }
    println(bestLog)
//    val start = valves[0]
//    val settled = calculateLargestFlowrate(start)
//    println(settled)
//
//    return 0
    return maxPressure
  }


  fun part2(input: List<String>): Int {

    return 0
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day16_test")
//  println(part1(testInput))
//  println(part2(testInput))

  val input = readInput("Day16")
  output(part1(input))
//  output(part2(input))
}
