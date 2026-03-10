package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final Order order;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePaymentData() ? "SUCCESS" : "REJECTED";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private boolean validatePaymentData() {
        if (paymentData == null || paymentData.isEmpty()) {
            return false;
        }

        return switch (method) {
            case "VOUCHER" -> validateVoucherCode(paymentData.get("voucherCode"));
            case "BANK_TRANSFER" -> validateBankTransfer(paymentData.get("bankName"), paymentData.get("referenceCode"));
            default -> false;
        };
    }

    private boolean validateVoucherCode(String code) {
        if (code == null || code.length() != 16 || !code.startsWith("ESHOP")) {
            return false;
        }

        long digitCount = code.chars().filter(Character::isDigit).count();
        return digitCount == 8;
    }

    private boolean validateBankTransfer(String bankName, String referenceCode) {
        return isNotNullOrEmpty(bankName) && isNotNullOrEmpty(referenceCode);
    }

    private boolean isNotNullOrEmpty(String str) {
        return str != null && !str.isBlank();
    }
}