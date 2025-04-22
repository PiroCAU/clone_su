package clone.carrotMarket.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String uploadProfileDir = "/resources/static/image/profile";
    private final String uploadSellDir = "/resources/static/image/sell";
    private final String basicProfile = "resources/static/image/basicProfile.png";

    public String storeProfileImg(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + originalFilename;

            File resultFile = new File(uploadProfileDir, fileName);
            file.transferTo(resultFile);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }

    public String deleteProfileImg(String fileName) {
        File file = new File(uploadProfileDir, fileName);
        if (fileName != null && !fileName.isEmpty() && !fileName.equals(basicProfile)) {
            file.delete();
        }
        return basicProfile;
    }

    public String getBasicProfile() {
        return basicProfile;
    }

    public String storeSellImg(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + originalFilename;

            File resultFile = new File(uploadSellDir, fileName);
            file.transferTo(resultFile);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}
