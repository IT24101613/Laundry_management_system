package com.laundry.laundry_management_system.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CardDto {

    @NotBlank(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    @Pattern(regexp = "\\d{16}", message = "Card number must be numeric")
    private String cardNumber;

    @NotBlank(message = "CVV is required")
    @Size(min = 3, max = 3, message = "CVV must be 3 digits")
    @Pattern(regexp = "\\d{3}", message = "CVV must be numeric")
    private String cvv;

    @NotBlank(message = "Expiry date is required")
    @Pattern(
            regexp = "^(0[1-9]|1[0-2])/\\d{2,4}$",
            message = "Expiry date must be in MM/YY or MM/YYYY format"
    )
    private String expiryDate;

    @NotBlank(message = "Name on card is required")
    private String nameOnCard;

    @NotNull(message = "Order ID is required")
    private Integer orderId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;


    public CardDto() {}

    public CardDto(String cardNumber, String cvv, String expiryDate, String nameOnCard) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

