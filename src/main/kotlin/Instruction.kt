import kotlin.experimental.and

enum class Instruction(val opcode: Byte, val run: Machine.(Short) -> Unit) {
    Load(0x1, { operand ->
        read(operand)
        AC from MBR
    }),
    Store(0x2, { operand ->
        MBR from AC
        write(operand)
    }),
    Add(0x3, { operand ->
        read(operand)
        AC += MBR
    }),
    Subt(0x4, { operand ->
        read(operand)
        AC -= MBR
    }),
    Input(0x5, {
        AC from input()
    }),
    Output(0x6, {
        output(AC.get())
    }),
    Halt(0x7, {
        halt()
    }),
    SkipCond(0x8, { operand ->
        if(when {
            operand and 0b0100000000000000 != 0.toShort() -> AC.get().toInt() == 0
            operand and 0b1000000000000000.toShort() != 0.toShort() -> AC.get().toInt() > 0
            else -> AC.get().toInt() < 0
        }) {
            PC += 1
        }
    }),
    Jump(0x9, { operand ->
        PC from operand
    }),
    Clear(0xa, { operand ->
        AC from 0
    }),
    AddI(0xb, { operand ->
        read(operand)
        read(MBR.get())
        AC += MBR
    }),
    JumpI(0xc, { operand ->
        read(operand)
        PC from MBR
    }),
    LoadI(0xd, { operand ->
        read(operand)
        read(MBR.get())
        AC from MBR
    }),
    StoreI(0xe, { operand ->
        read(operand)
        MAR from MBR
        MBR from AC
        write()
    }),
    JnS(0x0, { operand ->
        MBR from PC
        write(operand)
        MBR from operand
        AC from 1
        AC += MBR
        PC from AC
    })
}