package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private final String id;
    private final Order order;
    private final String method;
    @Setter
    private String status;
    private final Map<String, String> paymentData;

    // CONSTANT VALUES
    private static final String METHOD_VOUCHER = "VOUCHER";
    private static final String METHOD_BANK_TRANSFER = "BANK_TRANSFER";
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = validatePaymentData() ? STATUS_SUCCESS : STATUS_REJECTED;
    }

    // Untuk fungsi setStatus() pakai lombok setter

    private boolean validatePaymentData() {
        if (paymentData == null || paymentData.isEmpty()) {
            return false;
        }

        // Dibuat seperti ini supaya lebih maintainable
        return switch (method) {
            case METHOD_VOUCHER -> validateVoucherCode(paymentData.get("voucherCode"));
            case METHOD_BANK_TRANSFER -> validateBankTransfer(paymentData.get("bankName"), paymentData.get("referenceCode"));
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