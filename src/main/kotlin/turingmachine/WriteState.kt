package turingmachine

class WriteState<N>(identifier: N, nextState: N, val writeChar: Char): SimpleTransitionState<N>(identifier, nextState) {

    override fun run(turingMachine: TuringMachine<N>): N {
        turingMachine.tape.write(this.writeChar)
        return super.run(turingMachine)
    }

    override fun getInstructionName() =
            "Write ${this.writeChar}"
    
}
