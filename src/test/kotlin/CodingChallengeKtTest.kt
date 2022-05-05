import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class CodingChallengeKtTest {

    @Test
    fun `test 1 - given example 1`() {
        val inputValue = "? 1/2 * 3_3/4"
        val expectedOutput = "= 1_7/8"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 2 - given example 2`() {
        val inputValue = "? 2_3/8 + 9/8"
        val expectedOutput = "= 3_1/2"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 3 - add operand variation Test`() {
        val inputValue = "? 1/3 + 2/7"
        val expectedOutput = "= 13/21"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 4 - subtract operand variation Test`() {
        val inputValue = "? 7/5 - 6/3"
        val expectedOutput = "= -3/5"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 5 - multiply operand variation Test`() {
        val inputValue = "? 4/15 * 3/4"
        val expectedOutput = "= 1/5"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 6 - divide operand variation Test`() {
        val inputValue = "? 1/10 / 1/5"
        val expectedOutput = "= 1/2"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 7 - Operands and operators shall be separated by one or more spaces`() {
        val inputValue = "?    2_3/8    +    9/8"
        val expectedOutput = "= 3_1/2"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 8 - whole numbers are allowed as operands`() {
        val inputValue = "? 5 * 6"
        val expectedOutput = "= 30"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 8 - negative number example`() {
        val inputValue = "? -1/4 + 3/4"
        val expectedOutput = "= 1/2"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 9 - special case example 1`() {
        val inputValue = "? -5_1/4 + 3_3/4"
        val expectedOutput = "= -1_1/2"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }

    @Test
    fun `test 10 - special case example 2`() {
        val inputValue = "? -1/4 - 1/4"
        val expectedOutput = "= -1/2"
        assertEquals(evaluateExpression(inputValue), expectedOutput)
    }


}