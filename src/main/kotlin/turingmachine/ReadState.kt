package turingmachine

class ReadState<N>(identifier: N, private val transitions: HashMap<Char, N>): State<N>(identifier) {

    companion object {
        const val WILDCARD = '%'
    }

    override fun run(turingMachine: TuringMachine<N>): N {
        val readCharacter = turingMachine.tape.read()
        return this.transitions[readCharacter] ?: this.transitions[WILDCARD] ?: throw BadTransitionException("No transition defined for read character $readCharacter")
    }

    override fun getInstructionName(): String =
            "Read"

}
