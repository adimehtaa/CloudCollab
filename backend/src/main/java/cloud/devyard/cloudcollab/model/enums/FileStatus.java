package cloud.devyard.cloudcollab.model.enums;

public enum FileStatus {
    ACTIVE,
    DELETED,
    ARCHIVED,
    PROCESSING, // For video transcoding, etc.
    FAILED
}