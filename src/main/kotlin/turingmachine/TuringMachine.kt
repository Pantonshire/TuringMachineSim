package turingmachine

class TuringMachine<N>(private val states: HashMap<N, State<N>>, private val initialState: N, tapeStartingPos: Int) {

    val tape = Tape(tapeStartingPos)

    var returnValue: String? = null
        private set

    var lastInstruction: String = ""
        private set

    private var currentState = this.states[this.initialState] ?: throw BadTransitionException("Unknown initial state ${this.initialState}")

    fun reset() {
        this.currentState = this.states[this.initialState] ?: throw BadTransitionException("Unknown initial state ${this.initialState}")
        this.tape.reset()
    }

    fun finish(returnValue: String) {
        this.returnValue = returnValue
    }

    fun isFinished() =
            this.returnValue != null

    fun step() {
        this.lastInstruction = this.currentState.toString()
        val nextState = this.currentState.run(this)
        this.currentState = this.states[nextState]  ?: throw BadTransitionException("Unknown next state $nextState")
    }

}