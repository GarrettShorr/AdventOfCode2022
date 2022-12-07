fun main() {


  fun part1(input: List<String>): Int {
    val dirs = mutableListOf<Directory>()
    val topLevelDir = Directory("/")
    dirs.add(topLevelDir)
    var currentDir = topLevelDir
    var currlevel = 0
    for(line in input) {
      if(line == "$ cd /") {
        currentDir = topLevelDir
        currlevel++
      }
      // just move on to reading the files
      else if (line.startsWith("$ ls")) {
        continue
      }
      else if(line.startsWith("dir")) {
        var newDir = Directory(line.split(" ")[1], parent = currentDir, level = currlevel)
        currentDir.subDirs.add(newDir)
        dirs.add(newDir)
      }
      // change the current dir to something inside of the current dir
      else if(line.startsWith("$ cd")) {
        if(line.endsWith("..")) {
          currlevel--
            currentDir = currentDir.parent!!
        } else {
          currlevel++
          val dir = line.split(" ")[2]
          currentDir = currentDir.subDirs.first { it.name == dir }
        }
      }
      // otherwise it's a file, add it
      else {
        val newFile = line.split(" ")
        currentDir.files.add(File(newFile[1], newFile[0].toInt()))
      }
    }
    println(topLevelDir)
    var total = 0
    for(dir in dirs) {
      val current = dir.calculateDirSize()
      if(current <= 100000) {
        total += current
      }
    }



    return total
  }


  fun part2(input: List<String>): Int {
    val dirs = mutableListOf<Directory>()
    val topLevelDir = Directory("/")
    dirs.add(topLevelDir)
    var currentDir = topLevelDir
    var currlevel = 0
    for(line in input) {
      if(line == "$ cd /") {
        currentDir = topLevelDir
        currlevel++
      }
      // just move on to reading the files
      else if (line.startsWith("$ ls")) {
        continue
      }
      else if(line.startsWith("dir")) {
        val newDir = Directory(line.split(" ")[1], parent = currentDir, level = currlevel)
        currentDir.subDirs.add(newDir)
        dirs.add(newDir)
      }
      // change the current dir to something inside of the current dir
      else if(line.startsWith("$ cd")) {
        if(line.endsWith("..")) {
          currlevel--
          currentDir = currentDir.parent!!
        } else {
          currlevel++
          val dir = line.split(" ")[2]
          println("line $line before $dir")
          currentDir = currentDir.subDirs.first { it.name == dir }
          println("after")
        }
      }
      // otherwise it's a file, add it
      else {
        val newFile = line.split(" ")
        currentDir.files.add(File(newFile[1], newFile[0].toInt()))
      }
    }
    println(topLevelDir)

    val total = topLevelDir.calculateDirSize()
    val needed = 30000000
    val free = 70000000 - total
    val target = needed - free
    println("total: $total free $free target: $target")

    var smallest = 70000000
    for(dir in dirs) {
      var dirSize = dir.calculateDirSize()
      if(dirSize < target) {
        continue
      } else {
        if(dirSize < smallest) {
          smallest = dirSize
        }
      }
    }

    return smallest
  }


  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day07_test")
//  println(part1(testInput))
  println(part2(testInput))

  val input = readInput("Day07")
//  output(part1(input))
  output(part2(input))
}

data class Directory(
  var name: String,
  var subDirs: MutableSet<Directory> = mutableSetOf(),
  var files : MutableSet<File> = mutableSetOf(),
  var parent : Directory? = null,
  var level: Int = 0
) {
  fun calculateFilesSize() : Int {
    return files.sumOf { it.size }
  }

  fun calculateDirSize() : Int {
    val dirsToCount = subDirs.toMutableList()
    var total = calculateFilesSize()
    while(dirsToCount.size > 0) {
      val currentDir = dirsToCount.removeAt(0)
      dirsToCount.addAll(currentDir.subDirs)
      total += currentDir.calculateFilesSize()
    }
    return total
  }

  override fun toString(): String {
    return "Directory(name='$name', parent='$parent', level=$level, subdirs=${subDirs.joinToString { it.name }})"
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + subDirs.hashCode()
    result = 31 * result + files.hashCode()
    result = 31 * result + level
    return result
  }

}

class File(var name: String, var size: Int)

