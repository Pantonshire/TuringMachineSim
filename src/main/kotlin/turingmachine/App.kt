package turingmachine

import java.io.File

fun main(args: Array<String>) {
    val inputStream = File("BlockFinder.tm").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }

    val tmParser = TMSpecParser()
    val blockFinder = tmParser.parse(inputString)

    blockFinder.tape.inputCharacters(-9, "abbabb".toCharArray().toTypedArray())

    while (!blockFinder.isFinished()) {
        println("Instruction: ${blockFinder.lastInstruction}")
        println(blockFinder.tape)
        blockFinder.step()
        println()
    }

    println(blockFinder.returnValue)
}
