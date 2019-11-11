package turingmachine

class Tape(private val initialHeadPos: Int) {

    companion object {
        const val BLANK_CHAR = '␣'
        private const val HEAD_DISPLAY_CHAR = '▼'
    }

    private val data = HashMap<Int, Char>()
    private var head = this.initialHeadPos

    fun reset() {
        resetHead()
        resetData()
    }

    fun resetHead() {
        this.head = this.initialHeadPos
    }

    fun resetData() {
        this.data.clear()
    }

    private fun characterAt(position: Int) =
            this.data[position] ?: BLANK_CHAR

    fun inputCharacters(inputAt: Int, inputCharacters: Array<Char>) {
        inputCharacters.forEachIndexed {
            index, character -> this.data[inputAt + index] = character
        }
    }

    fun read(): Char =
            characterAt(this.head)

    fun write(character: Char) {
        this.data[this.head] = character
    }

    fun writeBlank() {
        this.data.remove(this.head)
    }

    fun left() {
        this.head--
    }

    fun right() {
        this.head++
    }

    override fun toString(): String {
        val filledPositions = this.data.keys
        var min = this.head
        var max = this.head

        filledPositions.forEach {
            position -> if (position < min) {
                min = position
            } else if (position > max) {
                max = position
            }
        }

        var tapeString = ""
        var headString = ""

        for (i in (min - 1)..(max + 1)) {
            tapeString += characterAt(i)
            headString += if (i == this.head) {
                HEAD_DISPLAY_CHAR
            } else {
                ' '
            }
        }

        return " $headString \n…${tapeString}…"
    }

}