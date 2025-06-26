package dev.eliezer.lojaonline.modules.client.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClientDTO {
    @NotBlank(message = "fullname not provided")
    @Schema(example = "Jose da Silva", requiredMode = Schema.RequiredMode.REQUIRED, description = "client full name")
    private String fullname;

    @NotBlank (message = "email not provided")
    @Email(message = "invalid email")
    @Schema(example = "jose@email.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "client email")
    private String email;

    @NotBlank (message = "document not provided")
    @Schema(example = "123456789123", requiredMode = Schema.RequiredMode.REQUIRED, description = "client document")
    private String document;

    @NotBlank(message = "gender not provided")
    @jakarta.validation.constraints.Pattern(regexp = "^(male|female)$", message = "gender must be either 'male' or 'female'")
    @Schema(example = "male/female", requiredMode = Schema.RequiredMode.REQUIRED, description = "client gender")
    private String gender;

    @NotNull (message = "birthDate not provided")
    @Schema(example = "1990-01-01", requiredMode = Schema.RequiredMode.REQUIRED, description = "client gender")
    private LocalDate birthDate;

    @NotEmpty(message = "phone not provided")
    @Valid
    private List<PhoneDTO> phone;

    @NotNull(message = "address not provided")
    @Valid
    private AddressDTO address;

    @Data
    public static class PhoneDTO {

        @Schema(example = "+55", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "client country code")
        private String countryCode = "+55";

        @Schema(example = "19", requiredMode = Schema.RequiredMode.REQUIRED, description = "client area code")
        @Column(nullable = false)
        private String areaCode;

        @Schema(example = "987654321", requiredMode = Schema.RequiredMode.REQUIRED, description = "client phone number")
        private String number;

    }

    @Data
    public static class AddressDTO {

        @Schema(example = "BR", requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "country name")
        @jakarta.validation.constraints.Pattern(regexp = "^[A-Z]{2}$", message = "country must be a valid two-letter code")
        private String country = "BR";

        @Schema(example = "SP", requiredMode = Schema.RequiredMode.REQUIRED, description = "state name")
        @NotBlank(message = "state not provided")
        @jakarta.validation.constraints.Pattern(regexp = "^[A-Z]{2}$", message = "state must be a valid two-letter code")
        private String state;

        @Schema(example = "Campinas", requiredMode = Schema.RequiredMode.REQUIRED, description = "city name")
        @NotBlank(message = "city not provided")
        private String city;

        @Schema(example = "13083-970", requiredMode = Schema.RequiredMode.REQUIRED, description = "postal zip code")
        @NotBlank(message = "zip code not provided")
        @jakarta.validation.constraints.Pattern(regexp = "^[0-9]{5}-[0-9]{3}$", message = "zip code must follow the format XXXXX-XXX")
        private String zipCode;

        @Schema(example = "Centro", requiredMode = Schema.RequiredMode.REQUIRED, description = "neighborhood description")
        @NotBlank(message = "neighborhood not provided")
        private String neighborhood;

        @Schema(example = "Av. Andrade Neves", requiredMode = Schema.RequiredMode.REQUIRED, description = "street name")
        @NotBlank(message = "street not provided")
        private String street;

        @Schema(example = "123", requiredMode = Schema.RequiredMode.REQUIRED, description = "address number")
        @NotBlank(message = "number not provided")
        private String number;

        @Schema(example = "Apartment 45B", description = "optional complement information")
        private String complement;

    }

}
