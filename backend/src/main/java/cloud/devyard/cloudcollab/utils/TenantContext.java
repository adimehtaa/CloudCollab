package cloud.devyard.cloudcollab.utils;

import java.util.Optional;
import java.util.function.Supplier;

public class TenantContext {

    private static final ScopedValue<Long> CURRENT_TENANT = ScopedValue.newInstance();

    public static <T> T runWithTenant(Long tenantId, Supplier<T> operation) {
        if (tenantId == null) {
            return operation.get();
        }
        return ScopedValue.where(CURRENT_TENANT, tenantId).call(operation::get);
    }

    public static void runWithTenant(Long tenantId, Runnable operation) {
        if (tenantId == null) {
            operation.run();
            return;
        }
        ScopedValue.where(CURRENT_TENANT, tenantId).run(operation);
    }

    public static Optional<Long> getCurrentTenant() {
        return Optional.ofNullable(CURRENT_TENANT.orElse(null));
    }

    public static Long getCurrentTenantOrThrow() {
        return CURRENT_TENANT.orElseThrow(() ->
                new IllegalStateException("Tenant context not set"));
    }

    public static boolean isTenantSet() {
        return CURRENT_TENANT.isBound();
    }
}
