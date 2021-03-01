package cinema.command

import java.util.*

abstract class CommandManager {
    protected val scanner = Scanner(System.`in`)

    private fun processCommand(command: Command) {
        val count = command.getArgCount()
        do {
            val args = Array<String>(count) {
                println(command.getInputMessages()[it])
                scanner.nextLine()
            }
            val result = command.runCommand(args)
            println(result.message)
        } while (!result.success)
    }

    fun executeProgram() {
        initializeApplication()
        do {
            println(getMainCommand())
            val command = getCommand(scanner.nextLine()!!)
            processCommand(command)
        } while (!command.isExit())
    }

    abstract fun initializeApplication()
    abstract fun getCommand(nextLine: String): Command
    abstract fun getMainCommand(): String

}


