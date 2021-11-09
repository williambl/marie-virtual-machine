@Suppress("PropertyName")
@OptIn(ExperimentalUnsignedTypes::class)
class Machine(byteArray: ByteArray) {
    val PC = Register(0b0000111111111111u)
    val IR = Register()
    val OUT = Register(0b0000000011111111u)
    val IN = Register(0b0000000011111111u)
    val AC = Register()
    val MBR = Register()
    val MAR = Register(0b0000111111111111u)

    private val memory = UShortArray(4096) { i -> (byteArray[2*i].toUInt() shl 8 or byteArray[i].toUInt()).toUShort() }

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

    fun halt() {

    }
}