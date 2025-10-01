package com.laundry.laundry_management_system.controller;

import com.laundry.laundry_management_system.dto.PaymentDto;
import com.laundry.laundry_management_system.model.Payment;
import com.laundry.laundry_management_system.service.PaymentService;
import jakarta.validation.Valid;
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

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ✅ Manual form submission
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

    // ✅ Card Payment Flow
    @GetMapping("/card")
    public String showCardPaymentPage(@RequestParam int orderId,
                                      @RequestParam BigDecimal amount,
                                      Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("amount", amount);
        return "card-payment";
    }

    @PostMapping("/pay/card")
    public String processCardDetails(@RequestParam String cardholderName,
                                     @RequestParam String cardNumber,
                                     @RequestParam String expiryDate,
                                     @RequestParam String cvv,
                                     @RequestParam int orderId,
                                     @RequestParam BigDecimal amount,
                                     Model model) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("cardholderName", cardholderName);
        model.addAttribute("amount", amount);
        return "otp-verification";
    }

    @PostMapping("/pay/card/otp")
    public String verifyOtp(@RequestParam String otp,
                            @RequestParam int orderId,
                            @RequestParam BigDecimal amount,
                            RedirectAttributes redirectAttributes) {

        // ✅ Record payment as Paid using Card
        paymentService.recordPayment(orderId, amount, "Card", "Paid");

        redirectAttributes.addAttribute("orderId", orderId);
        redirectAttributes.addAttribute("otp", otp);
        return "redirect:/payments/receipt";
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

    // ✅ COD Flow
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

        // ✅ Record payment as Pending using COD
        paymentService.recordPayment(orderId, amount, "CashOnDelivery", "Pending");

        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/payments/receipt";
    }

    // ✅ Dashboard
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
        return "dashboard";
    }

    // ✅ Cancel Payment
    @PostMapping("/cancel")
    public String cancelPayment(@RequestParam int orderId,
                                @RequestParam BigDecimal amount,
                                RedirectAttributes redirectAttributes) {

        paymentService.recordPayment(orderId, amount, "CashOnDelivery", "Cancelled");

        return "redirect:/payments/dashboard?orderId=" + orderId;
    }
}
