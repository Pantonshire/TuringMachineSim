package turingmachine

abstract class State<N>(val identifier: N) {

    abstract fun run(turingMachine: TuringMachine<N>): N

    abstract fun getInstructionName(): String

    override fun toString(): String =
            "[${identifier}] ${getInstructionName()}"

}