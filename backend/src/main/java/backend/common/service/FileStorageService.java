package backend.common.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    public String saveFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {

            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName =
                    file.getOriginalFilename();

            String extension = "";

            if (originalFileName != null &&
                originalFileName.contains(".")) {

                extension =
                        originalFileName.substring(
                                originalFileName.lastIndexOf("."));
            }

            String fileName =
                    UUID.randomUUID().toString() + extension;

            Path filePath =
                    uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        }

        catch (IOException e) {

            throw new RuntimeException(
                    "Failed to store file.",
                    e);

        }

    }

}