class Date(val year: Int, val month: Int = 1, val day: Int = 1) {

    init {
        require(year in 1500..2200)
        require(month in 1..12)
        require(day in 1..lastDayOfMonth)
    }
//    constructor(year :Int, month :Int) :this(year, month, 1)
//    constructor(year :Int) :this(year, 1, 1)

//    val leapYear get() = year%4 == 0 && year%100 != 0 || year%400 == 0

    fun leapYear2() = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
}

fun Date.leapYear3() = year % 4 == 0 && year % 100 != 0 || year % 400 == 0

val Date.leapYear get() = year.isLeapYear
val Int.isLeapYear get() = this % 4 == 0 && this % 100 != 0 || this % 400 == 0

private val daysOfMonth = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

val Date.lastDayOfMonth: Int
    get() = if (month == 2 && leapYear) 29 else daysOfMonth[month - 1]


tailrec fun Date.addDays(days: Int): Date = when {
    day + days <= lastDayOfMonth ->    // Date in same month
        Date(year, month, day + days)

    month < 12 ->                      // Date in next month
        Date(year, month + 1, 1).addDays(days - (lastDayOfMonth - day + 1))

    else ->                            // Date in next year
        Date(year + 1, 1, 1).addDays(days - (lastDayOfMonth - day + 1))
}

operator fun Date.plus(days: Int) = this.addDays(days)

operator fun Int.plus(date: Date) = date.addDays(this)
