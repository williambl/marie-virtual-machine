import org.slf4j.LoggerFactory

class Register(val name: String, val mask: UShort = UShort.MAX_VALUE, initialValue: UShort = 0u) {
    private var value: UShort = initialValue
    set(value) {
        field = value and mask
    }

    fun set(value: UShort) {
        this.value = value
        //LOGGER.debug("{} <- 0x{}", name, value.toString(16))
    }

    fun get(): UShort = value

    fun set(other: Register) {
        set(other.get())
        //LOGGER.debug("{} <- {}", name, other.name)
    }

    operator fun plus(other: Register): UShort {
        return (get() + other.get()).toUShort()
    }

    operator fun minus(other: Register): UShort {
        return (get() - other.get()).toUShort()
    }

    operator fun plus(value: UShort): UShort {
        return (get() + value).toUShort()
    }

    operator fun minus(value: UShort): UShort {
        return (get() - value).toUShort()
    }

    operator fun plusAssign(other: Register) = set(plus(other))
    operator fun minusAssign(other: Register) = set(minus(other))

    operator fun plusAssign(value: UShort) = set(plus(value))
    operator fun minusAssign(value: UShort) = set(minus(value))

    infix fun from(other: Register) = set(other)
    infix fun from(value: UShort) = set(value)

    companion object {
        val LOGGER = LoggerFactory.getLogger(Register::class.java)
    }
}