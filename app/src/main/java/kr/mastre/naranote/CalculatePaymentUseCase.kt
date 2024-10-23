package kr.mastre.naranote

import kotlin.math.roundToInt

class CalculatePaymentUseCase {
    fun execute(noteAmount: Int, interestRate: Double, durationInDays: Int, charge: Int): Int {
        require(interestRate >= 0) { "이율은 0 이상이어야 합니다." }
        require(interestRate < 50) { "이율은 50% 미만이어야 합니다." }
        require(charge >= 0) {"수수료는 0 이상이어야 합니다."}
        require(durationInDays >= 1) {"기간은 1 이상이어야 합니다."}

        val interest = (noteAmount / 30.0 * durationInDays * interestRate / 100).roundToInt()
        return noteAmount - interest - charge
    }
}