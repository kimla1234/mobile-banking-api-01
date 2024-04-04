package co.isatd.mobilebankingapi.features.media;

import co.isatd.mobilebankingapi.features.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-single")
    MediaResponse uploadSingle(@RequestPart MultipartFile file ){
        return mediaService.uploadSingle(file,"IMAGE");

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/upload-multiple")
    List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files){
       return mediaService.uploadMultiple(files,"IMAGE");
    }

    @GetMapping("/{mediaName}")
    MediaResponse loadMediaByName (@PathVariable String mediaName ,String folderName){
        return mediaService.loadFileByName(mediaName , folderName);
    }

    @DeleteMapping("/{mediaName}")
    MediaResponse deleteMediaByName(@PathVariable String mediaName ,String folderName)  {
        return mediaService.deleteMediaByName(mediaName,folderName);
    }

    @GetMapping()
    List<MediaResponse> loadAllMedias(){
        return mediaService.loadAllMedias("IMAGE");
    }

    @GetMapping("/download/{name}")
    ResponseEntity downloadByName (@PathVariable String name) {
        return mediaService.downloadMediaByName(name , "IMAGE");
    }
}
