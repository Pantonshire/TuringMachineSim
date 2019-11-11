package turingmachine

class NoOpState<N>(identifier: N, nextState: N): SimpleTransitionState<N>(identifier, nextState) {

    override fun getInstructionName() =
            "No-op"

}
