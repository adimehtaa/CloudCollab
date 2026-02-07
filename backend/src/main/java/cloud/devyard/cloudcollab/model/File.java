package cloud.devyard.cloudcollab.model;

import cloud.devyard.cloudcollab.model.enums.FileStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "files", indexes = {

})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String storageKey;

    private String publicUrl;

    @Column(nullable = false)
    private String mimeType;

    @Column(nullable = false)
    private Long size;

    private String fileExtension;

    // File versioning
    private Integer version = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_file_id")
    private File parentFile; // For versioning

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User uploadedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    // File status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileStatus status = FileStatus.ACTIVE;

    // Metadata
    private String thumbnailUrl;

    @Column(columnDefinition = "TEXT")
    private String metadata; // JSON string for additional data

    // Sharing
    @Column(nullable = false)
    private Boolean isPublic = false;

    private String shareToken; // For public sharing

    // Timestamps
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime uploadedAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt; // Soft delete
}
