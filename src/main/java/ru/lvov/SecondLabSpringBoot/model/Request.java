package ru.lvov.SecondLabSpringBoot.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @NotBlank(message = "UID не может быть пустым")
    @Size (max = 32, message = "Символов не должно быть больше 32")
    private String uid;

    @NotBlank (message = "UID не может быть пустым")
    @Size (max = 32, message = "Символов не должно быть больше 32")
    private String operationUid;
    private Systems systemName;

    @NotBlank (message = "UID не может быть пустым")
    private String systemTime;
    private String source;

    @NotNull(message = "Communication ID не может быть пустым")
    @Min(value = 1, message = "Communication ID должен быть не менее 1")
    @Max(value = 100000, message = "Communication ID должен быть не более 100000")
    private int communicationID;
    private int templatedID;
    private int productCode;
    private int smsCode;
    @Override
    public String toString(){
        return "{" +
                "uid='" + uid + '\'' +
                ", operationUid'" + operationUid + '\'' +
                ", systemName'" + systemName +'\'' +
                ", systemTime'" + systemTime +'\'' +
                ", source'" + source + '\'' +
                ", communicationID'" + communicationID + '\'' +
                ", templatedID'" + templatedID + '\'' +
                ", productCode'" + productCode + '\'' +
                ", smsCode'" + smsCode +
                '}';
    }


    public enum Systems {
        ERP("Enterprise Resource Planning"),
        CRM("Customer Relationship Management"),
        WMS("Warehouse Management System");

        private final String description;

        Systems(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

}
