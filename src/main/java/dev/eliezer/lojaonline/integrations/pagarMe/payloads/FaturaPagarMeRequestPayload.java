package dev.eliezer.lojaonline.integrations.pagarMe.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class FaturaPagarMeRequestPayload {

    private Customer customer = new Customer();

    private Shipping shipping = new Shipping();

    private List<Item> items;

    private Antifraud antifraud = new Antifraud();

    private List<Payment> payments = new ArrayList<>(List.of(new Payment()));

    @Data
    public static class Customer {
        private String name = "Eliezer Ramos Palhão";

        private String email = "eliezer.ramosp@gmail.com";

        private String document = "42828799808";

        private String type = "individual";

        private Phones phones = new Phones();

        @Data
        public static class Phones {
            @JsonProperty("mobile_phone")
            private MobilePhone mobilePhone = new MobilePhone();

            @Data
            public static class MobilePhone {
                @JsonProperty("country_code")
                private String countryCode = "55";

                @JsonProperty("area_code")
                private String areaCode = "19";

                private String number = "992591066";
            }
        }
    }

    @Data
    public static class Shipping {
        private Address address = new Address();

        private Integer amount = 0;

        private String description = "Home";

        @JsonProperty("recipient_name")
        private String recipientName = "Eliezer";

        @JsonProperty("recipient_phone")
        private String recipientPhone = "5519992591066";

        @Data
        public static class Address {
            private String line1 = "124, Avenida Pascoal Piconi, Jardim São Manoel ";

            @JsonProperty("zip_code")
            private String zipCode = "13386036";

            private String city = "Nova Odessa";

            private String state = "SP";

            private String country = "BR";

            @JsonProperty("line_1")
            public void setLine1(String line1) {
                this.line1 = line1;
            }
        }
    }

    @Data
    public static class Item {
        private Integer amount;

        private String description;

        private Integer quantity;

        private String code;
    }

    @Data
    public static class Antifraud {
        private String type = "clearsale";

        private Clearsale clearsale = new Clearsale();

        @Data
        public static class Clearsale {
            @JsonProperty("custom_sla")
            private String customSla = "90";
        }
    }


    @Data
    public static class Payment {
        @JsonProperty("payment_method")
        private String paymentMethod = "checkout";

        private Checkout checkout = new Checkout();

        @Data
        public static class Checkout {
            @JsonProperty("default_payment_method")
            private String defaultPaymentMethod = "credit_card";

            @JsonProperty("accepted_payment_methods")
            private List<String> acceptedPaymentMethods = List.of("credit_card");

            @JsonProperty("success_url")
            private String successUrl = "http://localhost:3000";
        }
    }
}