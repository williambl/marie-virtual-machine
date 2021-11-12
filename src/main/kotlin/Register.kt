import org.slf4j.LoggerFactory
import kotlin.experimental.and

class Register(val name: String, val mask: Short = 0b1111111111111111u.toShort(), initialValue: Short = 0) {
    private var value: Short = initialValue
    set(value) {
        field = value and mask
    }

    fun set(value: Short) {
        this.value = value
        //LOGGER.debug("{} <- 0x{}", name, value.toString(16))
    }

    fun get(): Short = value

    fun set(other: Register) {
        set(other.get())
        //LOGGER.debug("{} <- {}", name, other.name)
    }

    operator fun plus(other: Register): Short {
        return (get() + other.get()).toShort()
    }

    operator fun minus(other: Register): Short {
        return (get() - other.get()).toShort()
    }

    operator fun plus(value: Short): Short {
        return (get() + value).toShort()
    }

    operator fun minus(value: Short): Short {
        return (get() - value).toShort()
    }

    operator fun plusAssign(other: Register) = set(plus(other))
    operator fun minusAssign(other: Register) = set(minus(other))

    operator fun plusAssign(value: Short) = set(plus(value))
    operator fun minusAssign(value: Short) = set(minus(value))

    infix fun from(other: Register) = set(other)
    infix fun from(value: Short) = set(value)

    companion object {
        val LOGGER = LoggerFactory.getLogger(Register::class.java)
    }
}