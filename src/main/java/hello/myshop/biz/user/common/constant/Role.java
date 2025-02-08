package hello.myshop.biz.user.common.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN", "Administrator"),
    USER("USER", "Regular User"),
    ;

    private final String value;
    private final String desc;

    Role(String value, String desc) {
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
    public static Role fromValue(String value) {
        for (Role role : Role.values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown Role value: " + value);
    }

    @JsonCreator
    public static Role from(String value) {
        return Role.fromValue(value);
    }


}
