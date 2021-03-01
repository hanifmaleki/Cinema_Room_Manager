package cinema.model


data class Statistic(
    val purchasedTicketsCount: Int,
    val purchasedTicketPercentage: Double,
    val currentIncome: Int,
    val totalPossibleIncome: Int
)