enum class Instruction(val opcode: UByte, val run: Machine.(UShort) -> Unit) {
    Load(0x1u, { operand ->
        readMemory(operand)
        AC.set(MBR.get())
    }),
    Store(0x2u, { operand ->
        MBR.set(AC.get())
        writeMemory(operand)
    }),
    Add(0x3u, { operand ->
        readMemory(operand)
        AC.set((AC.get() + MBR.get()).toUShort())
    }),
    Subt(0x4u, { operand ->
        readMemory(operand)
        AC.set((AC.get() - MBR.get()).toUShort())
    }),
    Input(0x5u, {
        AC.set(IN.get())
    }),
    Output(0x6u, {
        OUT.set(AC.get())
    }),
    Halt(0x7u, {
        halt()
    }),
    SkipCond(0x8u, { operand ->
        if(when {
            operand and 0b0100000000000000u != 0u.toUShort() -> AC.get().toInt() == 0
            operand and 0b1000000000000000u != 0u.toUShort() -> AC.get().toInt() > 0
            else -> AC.get().toInt() < 0
        }) {
            PC.set((PC.get() + 1u).toUShort())
        }
    }),
    Jump(0x9u, { operand ->
        PC.set(operand)
    })
}