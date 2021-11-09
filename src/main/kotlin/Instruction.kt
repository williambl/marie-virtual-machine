enum class Instruction(val opcode: UByte, val run: Machine.(UShort) -> Unit) {
    Load(0x1u, { operand ->
        read(operand)
        AC from MBR
    }),
    Store(0x2u, { operand ->
        MBR from AC
        write(operand)
    }),
    Add(0x3u, { operand ->
        read(operand)
        AC += MBR
    }),
    Subt(0x4u, { operand ->
        read(operand)
        AC -= MBR
    }),
    Input(0x5u, {
        AC from input()
    }),
    Output(0x6u, {
        output(AC.get())
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
            PC += 1u
        }
    }),
    Jump(0x9u, { operand ->
        PC from operand
    }),
    Clear(0xau, { operand ->
        AC from 0u
    }),
    AddI(0xbu, { operand ->
        read(operand)
        read(MBR.get())
        AC += MBR
    }),
    JumpI(0xcu, { operand ->
        read(operand)
        PC from MBR
    }),
    LoadI(0xdu, { operand ->
        read(operand)
        read(MBR.get())
        AC from MBR
    }),
    StoreI(0xeu, { operand ->
        read(operand)
        MAR from MBR
        MBR from AC
        write()
    }),
    JnS(0x0u, { operand ->
        MBR from PC
        write(operand)
        MBR from operand
        AC from 1u
        AC += MBR
        PC from AC
    })
}