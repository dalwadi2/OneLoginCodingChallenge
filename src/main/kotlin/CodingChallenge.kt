/**
 * Created by Harsh Dalwadi on May 04 2022.
 */
fun main() {
    val inputExpressionAndAnswer = mutableMapOf<String, String>()
    inputExpressionAndAnswer["? 1/2 * 3_3/4"] = "= 1_7/8"
    inputExpressionAndAnswer["? 2_3/8 + 9/8"] = "= 3_1/2"

    for (singleEntry in inputExpressionAndAnswer) {
        val calculatedAnswer = evaluateExpression(singleEntry.key)

        if (calculatedAnswer == singleEntry.value) {
            println("${singleEntry.key} ${singleEntry.value} Test Passed")
        } else {
            println("${singleEntry.key} ${singleEntry.value} Test Failed")
            println("Correct answer will be $calculatedAnswer")
        }
    }
}

fun evaluateExpression(inputExpression: String): String {
    // splitting the whole string from n amount of whitespaces
    val components = inputExpression.split("\\s+".toRegex())

    // Building 1st term as Fraction component
    val firstTerm = Fraction.buildTermFromInput(components[1])
    val operator = components[2]
    // Building 2nd term as Fraction component
    val secondTerm = Fraction.buildTermFromInput(components[3])

    // initializing result with default values
    var result = Fraction()

    when (operator) {
        "+" -> {
            result = Fraction.add(firstTerm, secondTerm)
        }
        "-" -> {
            result = Fraction.subtract(firstTerm, secondTerm)
        }
        "*" -> {
            result = Fraction.multiply(firstTerm, secondTerm)
        }
        "/" -> {
            result = Fraction.divide(firstTerm, secondTerm)
        }
        else -> {
            return "Invalid Operator"
        }
    }
    result.convertToLowestTerm()
    return "= " + result.convertToReadableString()
}

class Fraction(var numerator: Int = 1, var denominator: Int = 1) {

    fun convertToLowestTerm() {
        val divisor = gcd(Math.abs(numerator), denominator)

        // If there is a common factor between the numerator and the denominator
        // reduce them to make the value lowest terms
        if (divisor > 1) {
            numerator /= divisor
            denominator /= divisor
        }
    }

    fun convertToReadableString(): String {
        val readableStringBuilder = StringBuilder()
        var localNumerator: Int = numerator

        // If the numerator is more than the denominator then there will be a whole part
        if (Math.abs(localNumerator) >= denominator) {
            val wholePart: Int = localNumerator / denominator
            localNumerator -= wholePart * denominator
            readableStringBuilder.append(wholePart)
            if (localNumerator != 0) {
                readableStringBuilder.append('_')
                localNumerator = Math.abs(localNumerator)
            }
        }

        // If there is anything left in the numerator, append the fractional part
        if (localNumerator != 0) {
            readableStringBuilder.append(localNumerator)
                .append('/')
                .append(denominator)
        }

        return readableStringBuilder.toString()
    }

    companion object {
        fun buildTermFromInput(input: String): Fraction {
            var wholePart = 0
            var numerator = 0

            val denominator: Int
            val underscoreIndex: Int = input.indexOf('_')
            val fractionIndex: Int = input.indexOf('/')

            // If there is an underscore, it is a whole number component before the fraction
            if (underscoreIndex >= 0) {
                // Prune off whole number
                wholePart = input.substring(0, underscoreIndex).toInt()
            }

            // If there is no fraction sign, this is a whole number
            if (fractionIndex < 0) {
                wholePart = input.toInt()
                denominator = 1
            } else {
                // The numerator and denominator surround the "/" in the expression
                numerator = if ((underscoreIndex + 1) > fractionIndex - (underscoreIndex + 1)) {
                    // we are getting start index greater then end so we are reversing the order
                    if (wholePart < 0) {
                        // if the whole part is negative value then we need to omit that from substring
                        input.substring(fractionIndex - (underscoreIndex) + 1, (underscoreIndex + 2)).toInt()
                    } else {
                        input.substring(fractionIndex - (underscoreIndex), (underscoreIndex + 2)).toInt()
                    }
                } else {
                    input.substring(underscoreIndex + 1, fractionIndex - (underscoreIndex + 1)).toInt()
                }
                denominator = input.substring(fractionIndex + 1).toInt()
            }

            // If wholePart is negative, make the numerator negative
            if (wholePart < 0) {
                numerator = -numerator
            }

            // Make an improper fraction and include the whole number part
            numerator += (wholePart * denominator)

            return Fraction(numerator, denominator)
        }

        fun add(value1: Fraction, value2: Fraction): Fraction {
            if (value1.denominator != value2.denominator) {
                val newDenominator = lcm(value1.denominator, value2.denominator)
                val aMultiplier = newDenominator / value1.denominator
                val bMultiplier = newDenominator / value2.denominator

                val updatedValue1 = Fraction(value1.numerator * aMultiplier, newDenominator)
                val updatedValue2 = Fraction(value2.numerator * bMultiplier, newDenominator)

                return Fraction(updatedValue1.numerator + updatedValue2.numerator, updatedValue1.denominator)
            }
            return Fraction(value1.numerator + value2.numerator, value1.denominator)
        }

        fun subtract(value1: Fraction, value2: Fraction): Fraction {
            return add(value1, Fraction(-value2.numerator, value2.denominator))
        }

        fun multiply(value1: Fraction, value2: Fraction): Fraction {
            return Fraction(value1.numerator * value2.numerator, value1.denominator * value2.denominator)
        }

        fun divide(value1: Fraction, value2: Fraction): Fraction {
            return Fraction(value1.numerator * value2.denominator, value1.denominator * value2.numerator)
        }

        private fun gcd(a: Int, b: Int): Int {
            return if (b == 0) a else gcd(b, a % b)
        }

        private fun lcm(a: Int, b: Int): Int {
            return a / gcd(a, b) * b
        }
    }

}