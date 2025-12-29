package cloud.devyard.cloudcollab.dto;

import cloud.devyard.cloudcollab.dto.response.PageMeta;
import cloud.devyard.cloudcollab.dto.response.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedResponse<T> {

    private Status status;
    private int code;
    private String message;
    private List<T> data;
    private PageMeta page;

    @Builder.Default
    private Instant timestamp = Instant.now();
}
