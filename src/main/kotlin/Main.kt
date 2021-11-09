import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.path
import kotlin.io.path.readBytes

class MarieMachine: CliktCommand() {
    private val input by argument(help="Assembled MARIE code").path(canBeDir = false, mustBeReadable = true)

    override fun run() {
        Machine(input.readBytes())
    }
}

fun main(args: Array<String>) = MarieMachine().main(args)