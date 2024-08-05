package com.example.paymentservice.payment.adapter.`in`.web.api

import com.example.paymentservice.common.WebAdapter
import com.example.paymentservice.payment.adapter.`in`.web.request.TossPaymentConfirmRequest
import com.example.paymentservice.payment.adapter.`in`.web.response.ApiResponse
import com.example.paymentservice.payment.adapter.out.web.toss.executor.TossPaymentExecutor
import com.example.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import com.example.paymentservice.payment.domain.PaymentConfirmationResult
import com.example.paymentservice.payment.domain.PaymentExecutionResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@WebAdapter
@RestController
@RequestMapping("/v1/toss")
class TossPaymentController(private val tossPaymentExecutor: TossPaymentExecutor) {

    @PostMapping("/confirm")
    fun confirm(@RequestBody request: TossPaymentConfirmRequest): Mono<ResponseEntity<ApiResponse<PaymentExecutionResult>>> {
        return tossPaymentExecutor.execute(
            PaymentConfirmCommand(
                paymentKey = request.paymentKey,
                orderId = request.orderId,
                amount = request.amount.toLong()
            )
        ).map {
            ResponseEntity.ok().body(
                ApiResponse.with(HttpStatus.OK, "Ok", it)
            )
        }
    }
}