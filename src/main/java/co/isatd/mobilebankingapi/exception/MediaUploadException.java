package co.isatd.mobilebankingapi.exception;

import co.isatd.mobilebankingapi.base.BaseError;
import co.isatd.mobilebankingapi.base.BaseErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class MediaUploadException {

//    @Value("${spring.servlet.multipart.max-file-size}")
//    private String maxsize;
//
//    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    BaseErrorResponse handleUploadSizeExceededException(MaxUploadSizeExceededException maxUploadSizeExceededException){
//        BaseError<String> baseError= BaseError.<String>builder()
//                .code(HttpStatus.PAYLOAD_TOO_LARGE.getReasonPhrase())
//                .description("Media thom pek lers 20MB")
//                .build()
//    }
}
