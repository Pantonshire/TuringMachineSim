package turingmachine

class TMSpecParser {

    companion object {
        const val HEAD_TOKEN = "head"
        const val START_TOKEN = "=>"
        const val ARROW_TOKEN = "->"
        const val READ_TOKEN = "read"
        const val WRITE_TOKEN = "write"
        const val LEFT_TOKEN = "left"
        const val RIGHT_TOKEN = "right"
        const val NO_OP_TOKEN = "noop"
        const val RETURN_TOKEN = "return"
        const val BLANK_TOKEN = "blank"
        const val ELSE_TOKEN = "else"
        const val TRANSITION_SEPARATOR_TOKEN = ","
        const val BLOCK_OPEN_TOKEN = '{'
        const val BLOCK_CLOSE_TOKEN = '}'
        const val COMMENT_TOKEN = "//"
    }

    fun parse(specification: String): TuringMachine<String> {
        val lines = specification.split(Regex("\n+")).map { line -> line.trim() }

        var initialState: String? = null
        var initialHeadPos: Int? = null

        val states = mutableListOf<State<String>>()

        lines.forEachIndexed {
            index, line ->

            if (!line.startsWith(COMMENT_TOKEN) && line.isNotEmpty()) {
                val lineNo = index + 1

                val splitByWhitespace = line.split(Regex("\\s+"))
                val firstWord = splitByWhitespace[0]

                if (firstWord == HEAD_TOKEN) {
                    if (initialHeadPos == null) {
                        initialHeadPos = splitByWhitespace[1].toInt()
                    } else {
                        error("Error at line ${lineNo}: conflicting starting head position")
                    }
                } else {
                    val isInitialState = line.startsWith(START_TOKEN)
                    val withoutInitialState = line.removePrefix(START_TOKEN).trim()

                    val splitByArrow = withoutInitialState.split(ARROW_TOKEN, limit = 2).map { string -> string.trim() }
                    val stateData = splitByArrow[0].split(Regex("\\s+"))
                    val stateIdentifier = stateData[0]
                    val stateType = stateData[1]

                    if (isInitialState) {
                        if (initialState == null) {
                            initialState = stateIdentifier
                        } else {
                            error("Error at line ${lineNo}: conflicting starting state")
                        }
                    }

                    if (stateType == RETURN_TOKEN) {
                        val returnValue = stateData[2]
                        states += ReturnState(stateIdentifier, returnValue)
                    } else {
                        val transitionData = splitByArrow[1]

                        if (stateType == READ_TOKEN) {
                            val readTransitionsData = transitionData.substring(
                                    transitionData.indexOf(BLOCK_OPEN_TOKEN) + 1, transitionData.indexOf(BLOCK_CLOSE_TOKEN)
                            ).split(TRANSITION_SEPARATOR_TOKEN).map { transition -> transition.trim() }

                            val readTransitions = HashMap<Char, String>()

                            readTransitionsData.forEach { readTransition ->
                                val readTransitionParts = readTransition.split(ARROW_TOKEN).map { string -> string.trim() }
                                val transitionOn = readTransitionParts[0]
                                val transitionOnChar = when (transitionOn) {
                                    BLANK_TOKEN -> Tape.BLANK_CHAR
                                    ELSE_TOKEN -> ReadState.WILDCARD
                                    else -> if (transitionOn.length == 1) {
                                        transitionOn[0]
                                    } else {
                                        error("Error at line ${lineNo}: invalid read character \"${transitionOn}\"")
                                    }
                                }

                                val transitionTo = readTransitionParts[1]
                                readTransitions[transitionOnChar] = transitionTo
                            }

                            states += ReadState(stateIdentifier, readTransitions)
                        } else {
                            if (stateType == WRITE_TOKEN) {
                                val writeData = stateData[2]
                                val writeCharacter = when (writeData) {
                                    BLANK_TOKEN -> Tape.BLANK_CHAR
                                    else -> if (writeData.length == 1) {
                                        writeData[0]
                                    } else {
                                        error("Error at line ${lineNo}: invalid write character \"${writeData}\"")
                                    }
                                }

                                states += WriteState(stateIdentifier, transitionData, writeCharacter)
                            } else {
                                states += when (stateType) {
                                    LEFT_TOKEN -> MoveLeftState(stateIdentifier, transitionData)
                                    RIGHT_TOKEN -> MoveRightState(stateIdentifier, transitionData)
                                    NO_OP_TOKEN -> NoOpState(stateIdentifier, transitionData)
                                    else -> error("Error at line ${lineNo}: invalid state type \"${stateType}\"")
                                }
                            }
                        }
                    }
                }
            }
        }

        if (initialState == null) {
            error("Error: no starting state specified")
        }

        if (initialHeadPos == null) {
            error("Error: no starting head position specified")
        }

        return TuringMachine(hashMapOf(*(states.map { state -> makeStatePair(state) }.toTypedArray())), initialState!!, initialHeadPos!!)
    }

    private fun<N> makeStatePair(state: State<N>) =
            Pair(state.identifier, state)

}