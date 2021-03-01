package cinema.model

import cinema.Seat
import cinema.exception.TicketAlreadyPurchasedException
import cinema.exception.WrongInputException
import java.lang.RuntimeException

const val THRESHOLD = 60
const val FRONT_TICKET_COST = 10
const val BACK_TICKET_COST = 8

class TheaterReservation(val row: Int, val col: Int) {

    val reserved = HashSet<Seat>()

    fun getWholeTicketPrice(): Int {
        return col * if (row * col > THRESHOLD) {
            val frontsSeats = row / 2
            frontsSeats * FRONT_TICKET_COST + (row - frontsSeats) * BACK_TICKET_COST
        } else
            row * FRONT_TICKET_COST
    }

    fun reserveSeat(seat: Seat): Int {
        if (seat.row > row || seat.row < 0 || seat.col > col || seat.col < 0) {
            throw WrongInputException("Wrong Input!")
        }

        if (reserved.contains(seat)) {
            throw TicketAlreadyPurchasedException("That ticket has already been purchased!")
        }

        reserved.add(seat)
        return getTicketPrice(seat)
    }

    fun getStatistics(): Statistic {
        val numberOfReserved = reserved.size
        val percentOfReserved = numberOfReserved.toDouble() / (row * col).toDouble()
        val currentIncome = reserved.map { getTicketPrice(it) }.sum()
        val totalIncome = getWholeTicketPrice()
        return Statistic(numberOfReserved, percentOfReserved, currentIncome, totalIncome)
    }

    private fun getTicketPrice(seat: Seat) =
        if (row * col < THRESHOLD || seat.row <= row / 2)
            FRONT_TICKET_COST
        else
            BACK_TICKET_COST
}

