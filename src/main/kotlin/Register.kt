class Register(val mask: UShort = UShort.MAX_VALUE) {
    private var value: UShort = 0u
    set(value) {
        field = value and mask
    }

    fun set(value: UShort) {
        this.value = value
    }

    fun get(): UShort = value
}