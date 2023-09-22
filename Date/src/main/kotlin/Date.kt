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

private const val DAY_BITS = 5   // 0..31
private const val MONTH_BITS = 4 // 0..15
private const val YEAR_BITS = 12 // 0..4095

class DateOptimized(year: Int, month: Int = 1, day: Int = 1) {
    private val bits: Int =
        (year shl (DAY_BITS + MONTH_BITS)) or (month shl DAY_BITS) or day


    val year: Int get() = bits shr (DAY_BITS + MONTH_BITS)
    val month: Int get() = (bits shr DAY_BITS) and ((1 shl MONTH_BITS) - 1)
    val day: Int get() = bits and ((1 shl DAY_BITS) - 1)

    override fun equals(other: Any?) = other is DateOptimized && bits == other.bits
    override fun hashCode() = bits
    operator fun compareTo(other: DateOptimized) = bits - other.bits

}

@JvmInline
value class DateOptimizedValue private constructor(private val bits: Int) {
    val year: Int get() = bits shr (DAY_BITS + MONTH_BITS)
    val month: Int get() = (bits shr DAY_BITS) and ((1 shl MONTH_BITS) - 1)
    val day: Int get() = bits and ((1 shl DAY_BITS) - 1)


    constructor(y: Int, m: Int = 1, d: Int = 1) : this(
        (y shl (DAY_BITS + MONTH_BITS)) or (m shl DAY_BITS) or d
    ) {
        require(y in 0..4000) { "Invalid year=$year" }
        require(m in 1..12) { "Invalid month=$month" }
//        require(d in 1..lastDayOfMonth) { "Invalid day=$day" }
    }

    override fun toString() = "$year-" + "%02d-%02d".format(month, day)
    operator fun compareTo(other: DateOptimizedValue) = bits - other.bits
}