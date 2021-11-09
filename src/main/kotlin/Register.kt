class Register(val name: String, val mask: UShort = UShort.MAX_VALUE, initialValue: UShort = 0u) {
    private var value: UShort = initialValue
    set(value) {
        field = value and mask
    }

    fun set(value: UShort) {
        this.value = value
    }

    fun get(): UShort = value

    fun set(other: Register) {
        set(other.get())
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

    infix fun from(other: Register) = set(other.get())
    infix fun from(value: UShort) = set(value)
}