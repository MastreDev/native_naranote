package kr.mastre.naranote

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class CalculatePaymentUseCaseTest : StringSpec({

    lateinit var useCase: CalculatePaymentUseCase

    beforeTest {
        useCase = CalculatePaymentUseCase()
    }

    "전자어음 정보와 이율을 기반으로 지급액을 계산해야 한다" {
        // given
        val noteAmount = 100_000 // 전자어음의 금액
        val interestRate = 5.0 // 이율 (%)
        val durationInDays = 30 // 일 수
        val charge = 500 // 수수료

        // when
        val paymentAmount = useCase.execute(noteAmount, interestRate, durationInDays, charge)

        // then
        val expectedAmount = 94_500 // 계산식에 따른 예시 지급액
        paymentAmount shouldBe expectedAmount
    }

    "이율이 0%일 때 원금에서 수수료만 차감된 값을 계산해야 한다" {
        // given
        val noteAmount = 100_000 // 전자어음의 금액
        val interestRate = 0.0 // 이율 (%)
        val durationInDays = 30 // 일 수
        val charge = 500 // 수수료

        // when
        val paymentAmount = useCase.execute(noteAmount, interestRate, durationInDays, charge)

        // then
        val expectedAmount = 99_500 // 이율이 0%일 때 지급액은 원금에서 수수료를 뺀 값
        paymentAmount shouldBe expectedAmount
    }

    "기간이 93일이고 원금이 39,600,000인 경우 지급액을 계산해야 한다" {
        // given
        val noteAmount = 39_600_000 // 전자어음의 금액
        val interestRate = 1.7 // 이율 (%)
        val durationInDays = 93 // 일 수
        val charge = 4_000 // 수수료

        // when
        val paymentAmount = useCase.execute(noteAmount, interestRate, durationInDays, charge)

        // then
        val expectedAmount = 37_509_080 // 계산식에 따른 예시 지급액
        paymentAmount shouldBe expectedAmount
    }

    "기간이 74일이고 원금이 4,270,710인 경우 지급액을 계산해야 한다" {
        // given
        val noteAmount = 4_270_710 // 전자어음의 금액
        val interestRate = 2.0 // 이율 (%)
        val durationInDays = 74 // 일 수
        val charge = 4_000 // 수수료

        // when
        val paymentAmount = useCase.execute(noteAmount, interestRate, durationInDays, charge)

        // then
        val expectedAmount = 4_056_022 // 계산식에 따른 예시 지급액
        paymentAmount shouldBe expectedAmount
    }

    "기간이 43일이고 원금이 5,000,000인 경우 지급액을 계산해야 한다" {
        // given
        val noteAmount = 5_000_000 // 전자어음의 금액
        val interestRate = 1.5 // 이율 (%)
        val durationInDays = 43 // 일 수
        val charge = 4_000 // 수수료

        // when
        val paymentAmount = useCase.execute(noteAmount, interestRate, durationInDays, charge)

        // then
        val expectedAmount = 4_888_500 // 계산식에 따른 예시 지급액
        paymentAmount shouldBe expectedAmount
    }

    "원금이 0인 경우 지급액은 0이어야 한다" {
        // given
        val noteAmount = 0 // 전자어음의 금액
        val interestRate = 5.0 // 이율 (%)
        val durationInDays = 30 // 일 수
        val charge = 500 // 수수료

        // when
        val paymentAmount = useCase.execute(noteAmount, interestRate, durationInDays, charge)

        // then
        val expectedAmount = -500 // 원금이 0일 때 수수료만 차감된 값
        paymentAmount shouldBe expectedAmount
    }

    "이율이 음수인 경우 예외를 발생시켜야 한다" {
        // given
        val noteAmount = 1_000_000 // 전자어음의 금액
        val interestRate = -5.0 // 이율 (%)
        val durationInDays = 30 // 일 수
        val charge = 500 // 수수료

        // when / then
        shouldThrow<IllegalArgumentException> {
            useCase.execute(noteAmount, interestRate, durationInDays, charge)
        }
    }

    "이율이 50% 이상인 경우, 예외를 발생시켜야 한다." {
        // given
        val noteAmount = 1_000_000 // 전자어음의 금액
        val interestRate = 100.0 // 이율 (%)
        val durationInDays = 30 // 일 수
        val charge = 500 // 수수료

        // when / then
        shouldThrow<IllegalArgumentException> {
            useCase.execute(noteAmount, interestRate, durationInDays, charge)
        }
    }

    "수수료가 음수인경우, 예외를 발생시켜야 한다." {
        // given
        val noteAmount = 5_000_000 // 전자어음의 금액
        val interestRate = 0.01 // 이율 (%)
        val durationInDays = 43 // 일 수
        val charge = -4_000 // 수수료

        // when / then
        shouldThrow<IllegalArgumentException> {
            useCase.execute(noteAmount, interestRate, durationInDays, charge)
        }
    }

    "기간이 음수인경우, 예외를 발생시켜야 한다." {
        // given
        val noteAmount = 5_000_000 // 전자어음의 금액
        val interestRate = 0.01 // 이율 (%)
        val durationInDays = -43 // 일 수
        val charge = 4_000 // 수수료

        // when / then
        shouldThrow<IllegalArgumentException> {
            useCase.execute(noteAmount, interestRate, durationInDays, charge)
        }
    }

    "이율이 0.01%인 경우, 지급액을 계산해야 한다." {
        // given
        val noteAmount = 5_000_000 // 전자어음의 금액
        val interestRate = 0.01 // 이율 (%)
        val durationInDays = 43 // 일 수
        val charge = 4_000 // 수수료

        // when
        val paymentAmount = useCase.execute(noteAmount, interestRate, durationInDays, charge)

        // then
        val expectedAmount = 4_995_283 // 계산식에 따른 예시 지급액
        paymentAmount shouldBe expectedAmount
    }

})