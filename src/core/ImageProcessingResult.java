package core;

public class ImageProcessingResult {
    private DImage processedImage;
    private String metadata;

    public ImageProcessingResult(DImage processedImage, String metadata) {
        this.processedImage = processedImage;
        this.metadata = metadata;
    }

    public DImage getProcessedImage() {
        return processedImage;
    }

    public String getMetadata() {
        return metadata;
    }
}