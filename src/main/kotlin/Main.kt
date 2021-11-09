import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import java.nio.ByteBuffer
import kotlin.io.path.readBytes

class MarieMachine: CliktCommand() {
    private val input by argument(help="Assembled MARIE code").path(canBeDir = false, mustBeReadable = true)

    override fun run() {
        val inputMemory = ByteBuffer.wrap(input.readBytes())
        val machine = Machine(inputMemory.asShortBuffer())
        while (true) {
            machine.step()
        }
    }
}

fun main(args: Array<String>) = MarieMachine().main(args)