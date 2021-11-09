import org.slf4j.LoggerFactory
import java.nio.ShortBuffer
import kotlin.experimental.and
import kotlin.system.exitProcess

@Suppress("PropertyName")
class Machine(buffer: ShortBuffer) {
    val PC = Register("PC", 0b0000111111111111, 1)
    val IR = Register("IR")
    val OUT = Register("OUT", 0b0000000011111111)
    val IN = Register("IN", 0b0000000011111111)
    val AC = Register("AC")
    val MBR = Register("MBR")
    val MAR = Register("MAR", 0b0000111111111111)

    private val memory = ShortArray(4096) { 0 }

    init {
        var i = 0
        while (buffer.hasRemaining()) {
            memory[i++] = buffer.get()
            println(memory[i - 1].toString(2))
        }
    }

    fun read(address: Short) {
        MAR from address
        read()
    }

    fun read() = MBR from (memory[MAR.get().toInt()])

    fun write(address: Short) {
        MAR from (address)
        write()
    }

    fun write() {
        memory[MAR.get().toInt()] = MBR.get()
    }

    fun halt(): Nothing {
        exitProcess(0)
    }

    fun input(): Short {
        LOGGER.info("Input a value:")
        IN from ((readLine() ?: "0").toInt(16).toShort())
        return IN.get()
    }

    fun output(value: Short) {
        OUT from value
        LOGGER.info(value.toString(16))
    }

    fun step() {
        val currentPc = PC.get()
        LOGGER.debug("0x{}", currentPc)
        this.read(currentPc)
        IR from MBR
        val opcode = IR.get().getTopNibble()
        val operand = IR.get() and 0b0000111111111111

        (Instruction.values().find { it.opcode == opcode } ?: halt())
            .run(this, operand)

        if (PC.get() == currentPc) {
            PC += 1
        }
    }

    private fun Short.getTopNibble(): Byte {
        return (this.toInt() shr 12).toByte()
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Machine::class.java)
    }
}