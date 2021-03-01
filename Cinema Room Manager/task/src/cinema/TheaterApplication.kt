package cinema

import cinema.command.Command
import cinema.command.CommandManager
import cinema.command.CommandResult
import cinema.exception.TicketAlreadyPurchasedException
import cinema.exception.WrongInputException
import cinema.model.TheaterReservation
import java.lang.RuntimeException


const val MAIN_COMMAND = """1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit"""


object TheaterApplication : CommandManager() {
    val theaterReservation: TheaterReservation = initializeTheater()

    private fun initializeTheater(): TheaterReservation {
        print("Enter the number of rows:")
        val row = scanner.nextLine().toInt()
        print("Enter the number of seats in each row:")
        val col = scanner.nextLine().toInt()

        return TheaterReservation(row, col)
    }

    override fun initializeApplication() {}

    override fun getCommand(commandString: String) = CommandType.fromCommand(commandString)

    override fun getMainCommand() = MAIN_COMMAND
}


enum class CommandType(val command: String) : Command {
    SHOW_THE_SEATS("1") {
        override fun runCommand(args: Array<String>) =
            CommandResult(TheaterShower.printSeats(TheaterApplication.theaterReservation))

    },
    BUY_A_TICKET("2") {
        override fun getArgCount() = 2
        override fun runCommand(args: Array<String>): CommandResult {
            val seat = Seat(args[0].toInt(), args[1].toInt())
            try {
                val ticketPrice = TheaterApplication.theaterReservation.reserveSeat(seat)
                return CommandResult("Ticket price: \$$ticketPrice\n")
            } catch (exception: TicketAlreadyPurchasedException) {
                return CommandResult("That ticket has already been purchased!\n", false)
            } catch (exception: WrongInputException) {
                return CommandResult("Wrong Input!\n", false)
            }
        }

        override fun getInputMessages() = arrayOf("Enter a row number:", "Enter a seat number in that row:")
    },
    STATISTICS("3") {
        override fun runCommand(args: Array<String>): CommandResult {
            val statistics = TheaterApplication.theaterReservation.getStatistics()
            val purchased = "Number of purchased tickets: ${statistics.purchasedTicketsCount}"
            val purchasedPercentage =
                String.format("Percentage: %.2f", statistics.purchasedTicketPercentage * 100.0) + "%"
            val currentIncome = "Current income: \$${statistics.currentIncome}"
            val totalIncome = "Total income: \$${statistics.totalPossibleIncome}"
            return CommandResult("$purchased\n$purchasedPercentage\n$currentIncome\n$totalIncome\n")
        }
    },
    QUIT("0") {
        override fun isExit() = true
    };

    companion object {
        fun fromCommand(command: String): CommandType =
            values()
                .first { value -> value.command == command }

    }
}
