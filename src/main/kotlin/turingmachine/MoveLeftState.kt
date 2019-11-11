package turingmachine

class MoveLeftState<N>(identifier: N, nextState: N): SimpleTransitionState<N>(identifier, nextState) {

    override fun run(turingMachine: TuringMachine<N>): N {
        turingMachine.tape.left()
        return super.run(turingMachine)
    }

    override fun getInstructionName() =
            "Left"

}
