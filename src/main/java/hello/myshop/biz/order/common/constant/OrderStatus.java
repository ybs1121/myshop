package hello.myshop.biz.order.common.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("PENDING", "대기"),

    COMPLETED("COMPLETED", "완료"),

    CANCELLED("CANCELLED", "취소"),
    ;

    private final String value;
    private final String desc;

    OrderStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }

    // 코드값으로 Enum 찾기
    @JsonCreator
    public static OrderStatus from(String value) {
        for (OrderStatus role : OrderStatus.values()) {
            if (role.value.equalsIgnoreCase(value) || role.desc.equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown Role value: " + value);
    }
}
