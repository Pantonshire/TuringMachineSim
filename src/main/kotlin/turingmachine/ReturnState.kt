package turingmachine

class ReturnState<N>(identifier: N, private val returnValue: String): State<N>(identifier) {

    override fun run(turingMachine: TuringMachine<N>): N {
        turingMachine.finish(this.returnValue)
        return this.identifier
    }

    override fun getInstructionName(): String =
            "Return ${this.returnValue}"

}
