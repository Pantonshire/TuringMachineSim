package turingmachine

class MoveRightState<N>(identifier: N, nextState: N): SimpleTransitionState<N>(identifier, nextState) {

    override fun run(turingMachine: TuringMachine<N>): N {
        turingMachine.tape.right()
        return super.run(turingMachine)
    }

    override fun getInstructionName() =
            "Right"

}
