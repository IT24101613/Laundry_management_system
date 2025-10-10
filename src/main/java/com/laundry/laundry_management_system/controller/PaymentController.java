package com.laundry.laundry_management_system.controller;

import com.laundry.laundry_management_system.dto.CardDto;
import com.laundry.laundry_management_system.dto.PaymentDto;
import com.laundry.laundry_management_system.model.Payment;
import com.laundry.laundry_management_system.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("paymentDto", new PaymentDto());
        return "payment-form";
    }

    @PostMapping("/submit")
    public String submitPayment(@Valid @ModelAttribute PaymentDto paymentDto,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            return "payment-form";
        }
        paymentService.processPayment(paymentDto);
        return "redirect:/payments/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "payment-success";
    }

    @GetMapping("/list")
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payment-list";
    }

    @GetMapping("/card")
    public String showCardPaymentPage(@RequestParam int orderId,
                                      @RequestParam BigDecimal amount,
                                      Model model) {
        CardDto cardDto = new CardDto();
        cardDto.setOrderId(orderId);
        cardDto.setAmount(amount);

        model.addAttribute("cardDto", cardDto);
        return "card-payment";
    }

    @PostMapping("/pay/card")
    public String processCard(@Valid @ModelAttribute("cardDto") CardDto cardDto,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "card-payment";
        }

        String otp = paymentService.generateOtp();
        paymentService.sendOtpEmail("binaliassalaarachchi@gmail.com", otp); // Replace with actual email
        paymentService.storeOtp(cardDto.getOrderId(), otp);

        model.addAttribute("orderId", cardDto.getOrderId());
        model.addAttribute("amount", cardDto.getAmount());
        return "otp-verification";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam int orderId,
                            @RequestParam String enteredOtp,
                            @RequestParam BigDecimal amount,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        boolean isValid = paymentService.verifyOtp(orderId, enteredOtp);
        if (isValid) {
            paymentService.recordPayment(orderId, amount, "Card", "Paid");

            redirectAttributes.addAttribute("orderId", orderId);
            redirectAttributes.addAttribute("otp", enteredOtp);

            return "redirect:/payments/receipt";
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            model.addAttribute("orderId", orderId);
            model.addAttribute("amount", amount);
            return "otp-verification";
        }
    }


    @GetMapping("/receipt")
    public String showReceipt(@RequestParam int orderId,
                              @RequestParam(required = false) String otp,
                              Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("otp", otp);
        return "receipt";
    }

    @PostMapping("/download")
    public ResponseEntity<String> downloadReceipt(@RequestParam int orderId) {
        Payment payment = paymentService.getPaymentByOrderId(orderId);
        String receipt = "Receipt for Order ID: " + orderId +
                "\nStatus: " + payment.getPaymentStatus() +
                "\nAmount: LKR " + payment.getAmount();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=receipt_" + orderId + ".txt")
                .contentType(MediaType.TEXT_PLAIN)
                .body(receipt);
    }

    @GetMapping("/cod")
    public String showCODPage(@RequestParam int orderId,
                              @RequestParam BigDecimal amount,
                              Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        return "cod-payment";
    }

    @PostMapping("/pay/cod")
    public String confirmCOD(@RequestParam int orderId,
                             @RequestParam BigDecimal amount,
                             RedirectAttributes redirectAttributes) {

        paymentService.recordPayment(orderId, amount, "CashOnDelivery", "Pending");

        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/payments/dashboard?orderId=" + orderId;
    }

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(required = false) Integer orderId, Model model) {
        Payment payment;

        if (orderId != null) {
            payment = paymentService.getPaymentByOrderId(orderId);
        } else {
            payment = new Payment();
            payment.setOrderId(1001);
            payment.setAmount(new BigDecimal("250.00"));
            payment.setPaymentStatus("Pending");
            payment.setPaymentId(1);
        }

        model.addAttribute("payment", payment);
        model.addAttribute("cardDto", new CardDto());
        return "dashboard";
    }

    @PostMapping("/cancel")
    public String cancelPayment(@RequestParam int orderId,
                                @RequestParam BigDecimal amount,
                                RedirectAttributes redirectAttributes) {

        paymentService.recordPayment(orderId, amount, "CashOnDelivery", "Cancelled");

        return "redirect:/payments/dashboard?orderId=" + orderId;
    }
}
