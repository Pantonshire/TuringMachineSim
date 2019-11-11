package turingmachine

abstract class SimpleTransitionState<N>(identifier: N, private val nextState: N): State<N>(identifier) {

    override fun run(turingMachine: TuringMachine<N>): N =
            this.nextState

}