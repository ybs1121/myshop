package hello.myshop.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // 공통 매핑 정보를 가진 부모 클래스
@EntityListeners(AuditingEntityListener.class) // Auditing 기능 활성화
public abstract class BaseEntity {

    @CreatedDate // 엔티티 생성 시 자동으로 시간 저장
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티 수정 시 자동으로 시간 저장
    private LocalDateTime updatedAt;

    @CreatedBy // 엔티티 생성자 저장 (AuditorAware 구현 필요)
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    @LastModifiedBy // 엔티티 수정자 저장 (AuditorAware 구현 필요)
    private String modifiedBy;
}
