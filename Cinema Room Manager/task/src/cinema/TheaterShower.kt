package cinema

import cinema.model.TheaterReservation

object TheaterShower {
    fun printSeats(theater: TheaterReservation): String {
        val col = theater.col
        val row = theater.row
        val reserved = theater.reserved
        val header = (1..col).map { it }.joinToString(separator = " ", prefix = "Cinema:\n  ", postfix = "\n")

        return (1..row).joinToString("\n", prefix = header, postfix = "\n")
        { r ->
            (1..col)
                .map {
                    if (reserved.any { seat -> seat.row == r && seat.col == it }) 'B'
                    else 'S'
                }
                .joinToString(separator = " ", prefix = "$r ")
        }
    }
}