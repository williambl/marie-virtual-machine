class Register(val name: String, val mask: UShort = UShort.MAX_VALUE, initialValue: UShort = 0u) {
    private var value: UShort = initialValue
    set(value) {
        field = value and mask
    }

    fun set(value: UShort) {
        println("setting $name to ${value.toString(16)} from ${this.value.toString(16)}")
        this.value = value
    }

    fun get(): UShort = value
}