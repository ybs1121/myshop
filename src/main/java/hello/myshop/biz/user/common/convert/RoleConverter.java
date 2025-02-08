package hello.myshop.biz.user.common.convert;

import hello.myshop.biz.user.common.constant.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue(); // Enum의 코드값을 DB에 저장
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return Role.fromValue(dbData); // DB 값으로 Enum 복원
    }
}
