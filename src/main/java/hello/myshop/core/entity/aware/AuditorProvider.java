package hello.myshop.core.entity.aware;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorProvider implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        // todo :: 추후 자동으로 Set 할 수 있게 변경
        return Optional.of(1L);
    }
}
