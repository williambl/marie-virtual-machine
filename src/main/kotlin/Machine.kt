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
            LOGGER.debug(memory[i - 1].toUShort().toString(16))
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
        PC += 1
        this.read(currentPc)
        IR from MBR
        val currentIr = IR.get()
        val opcode = currentIr.getTopNibble()
        val operand = currentIr and 0b0000111111111111

        (Instruction.values().find { it.opcode == opcode } ?: halt())
            .run(this, operand)
    }

    private fun Short.getTopNibble(): Byte {
        return (this.toUInt() shr 12).toByte() and 0b00001111
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(Machine::class.java)
    }
}