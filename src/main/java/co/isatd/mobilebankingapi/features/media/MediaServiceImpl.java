package co.isatd.mobilebankingapi.features.media;

import co.isatd.mobilebankingapi.features.media.dto.MediaResponse;
import co.isatd.mobilebankingapi.util.MediaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;


    @Override
    public MediaResponse uploadSingle(MultipartFile file ,String folderName ) {
        String newName = UUID.randomUUID().toString();
        // Extract extension from file upload
        int lastDotIndex = file.getOriginalFilename()
                .lastIndexOf(".");
        String extension = file.getOriginalFilename()
                .substring(lastDotIndex + 1);
        newName = newName + "." + extension;

        // Copy file to server
        Path path = Paths.get(serverPath + folderName + "/" + newName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getLocalizedMessage());
        }

        return MediaResponse.builder()
                .name(newName)
                .contentType(file.getContentType())
                .extension(extension)
                .size(file.getSize())
                .uri(String.format("%s%s/%s", baseUri, folderName, newName))
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {
        //Create empty array list , wait for adding uploaded file
        List<MediaResponse> mediaResponses = new ArrayList<>();
        //Use loop to upload each file
        files.forEach(file -> {
            MediaResponse mediaResponse = this.uploadSingle(file,folderName);
            mediaResponses.add(mediaResponse);
        });
        return mediaResponses;
    }

    @Override
    public MediaResponse loadFileByName(String mediaName, String folderName) {
        Path path = Paths.get(serverPath + folderName + "/" + mediaName);
        try {
            Resource resource  = new UrlResource(path.toUri());
            if (!resource.exists()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Media not found");
            }
            return MediaResponse.builder()
                    .name(mediaName)
                    .contentType(Files.probeContentType(path))
                    .extension(MediaUtil.extractExtension(mediaName))
                    .size(resource.contentLength())
                    .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                    .build();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public MediaResponse deleteMediaByName(String mediaName, String folderName) {

        // Create absolute path of media
        Path path = Paths.get(serverPath + folderName + "\\" + mediaName);

        try {
            long size = Files.size(path);
            if (Files.deleteIfExists(path)) {
                return MediaResponse.builder()
                        .name(mediaName)
                        .contentType(Files.probeContentType(path))
                        .extension(MediaUtil.extractExtension(mediaName))
                        .size(size)
                        .uri(String.format("%s%s/%s", baseUri, folderName, mediaName))
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Media has not been found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    String.format("Media path %s cannot be deleted!", e.getLocalizedMessage()));
        }
    }

    @Override
    public List<MediaResponse> loadAllMedias(String folderName) {

        List<MediaResponse> mediaResponseList = new ArrayList<>();

        Path path = Paths.get(serverPath + folderName);

        try (Stream<Path> paths = Files.list(path)) {
            paths.forEach(filePath -> {
                try {
                    String fileName = filePath.getFileName().toString();
                    Resource resource = new UrlResource(filePath.toUri());
                    MediaResponse mediaResponse = MediaResponse.builder()
                            .name(fileName)
                            .contentType(Files.probeContentType(filePath))
                            .extension(MediaUtil.extractExtension(fileName))
                            .size(resource.contentLength())
                            .uri(String.format("%s%s/%s", baseUri, folderName, filePath.getFileName()))
                            .build();
                    mediaResponseList.add(mediaResponse);
                } catch (IOException e) {
                    throw new RuntimeException("Error processing file: " + filePath, e);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return mediaResponseList;
    }

    @Override
    public ResponseEntity downloadMediaByName(String mediaName, String folderName) {
        Path path = Paths.get(serverPath + folderName + "/" + mediaName);
        try {
            Resource resource = new UrlResource(path.toUri());
            if(!resource.exists()){
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Media has not been found!!"
                );
            }
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + mediaName);
            headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .body(resource);
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getLocalizedMessage()
            );
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getLocalizedMessage()
            );
        }

    }


}
