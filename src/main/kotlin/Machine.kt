import java.nio.ByteBuffer
import java.nio.ShortBuffer
import kotlin.system.exitProcess

@Suppress("PropertyName")
@OptIn(ExperimentalUnsignedTypes::class)
class Machine(buffer: ShortBuffer) {
    val PC = Register("PC", 0b0000111111111111u, 1u)
    val IR = Register("IR")
    val OUT = Register("OUT", 0b0000000011111111u)
    val IN = Register("IN", 0b0000000011111111u)
    val AC = Register("AC")
    val MBR = Register("MBR")
    val MAR = Register("MAR", 0b0000111111111111u)

    private val memory = UShortArray(4096) { 0u }

    init {
        var i = 0
        while (buffer.hasRemaining()) {
            memory[i++] = buffer.get().toUShort()
            println(memory[i - 1].toString(2))
        }
    }

    fun readMemory(address: UShort) {
        MAR.set(address)
        readMemory()
    }

    fun readMemory() {
        MBR.set(memory[MAR.get().toInt()])
    }

    fun writeMemory(address: UShort) {
        MAR.set(address)
        writeMemory()
    }

    fun writeMemory() {
        memory[MAR.get().toInt()] = MBR.get()
    }

    fun halt(): Nothing {
        exitProcess(0)
    }

    fun input(): UShort {
        println("Input a value:")
        IN.set((readLine() ?: "0").toInt(16).toUShort())
        return IN.get()
    }

    fun output(value: UShort) {
        OUT.set(value)
        println(value.toString(16))
    }

    fun step() {
        val currentPc = PC.get()
        readMemory(currentPc)
        IR.set(MBR.get())
        val opcode = IR.get().getTopNibble()
        val operand = IR.get() and 0b0000111111111111u

        (Instruction.values().find { it.opcode == opcode } ?: halt())
            .run(this, operand)

        if (PC.get() == currentPc) {
            PC.set((PC.get() + 1u).toUShort())
        }
    }

    private fun UShort.getTopNibble(): UByte {
        return (this.toUInt() shr 12).toUByte()
    }
}