package co.isatd.mobilebankingapi.features.media;

import co.isatd.mobilebankingapi.features.media.dto.MediaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MediaService {
    MediaResponse uploadSingle(MultipartFile file , String folderName);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files , String folderName);

    MediaResponse loadFileByName(String mediaName ,String folderName);

    MediaResponse deleteMediaByName(String mediaName ,String folderName) ;
    List<MediaResponse> loadAllMedias(String folderName);
    ResponseEntity downloadMediaByName(String mediaName, String folderName);
}
