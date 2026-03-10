package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private Order order;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = validate(method, paymentData) ? "SUCCESS" : "REJECTED";
    }

    private boolean validate(String method, Map<String, String> data) {
        if (method.equals("VOUCHER")) {
            String code = data.get("voucherCode");
            if (code == null || code.length() != 16 || !code.startsWith("ESHOP")) return false;
            int numCount = 0;
            for (char c : code.toCharArray()) if (Character.isDigit(c)) numCount++;
            return numCount == 8;
        } else if (method.equals("BANK_TRANSFER")) {
            String bank = data.get("bankName");
            String ref = data.get("referenceCode");
            return bank != null && !bank.isBlank() && ref != null && !ref.isBlank();
        }
        return false;
    }
}