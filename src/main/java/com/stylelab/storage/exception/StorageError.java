package com.stylelab.storage.exception;

import com.stylelab.common.exception.CommonError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum StorageError implements CommonError {
    FILE_OBJECT_REQUIRE(HttpStatus.BAD_REQUEST, "80000", "업로드할 파일은 필수입니다."),
    FILE_SIZE_CANNOT_LESS_THEN_ZERO(HttpStatus.BAD_REQUEST, "80001", "파일 사이즈가 0보다 작을 수 없습니다."),
    FILE_ORIGIN_NAME_REQUIRE(HttpStatus.BAD_REQUEST, "80002", "파일 이름은 필수입니다."),
    INVALID_FORMAT_FILE(HttpStatus.BAD_REQUEST, "80003", "png, jpg, jpeg, webp 형식의 파일이 아닙니다."),
    EXCEED_MAX_IMAGE_COUNT(HttpStatus.BAD_REQUEST, "80004", "%s 타입은 최대 %s개의 이미지를 등록하실 수 있습니다."),
    IMAGE_TYPE_REQUIRE(HttpStatus.BAD_REQUEST, "80005", "이미지 타입은 필수입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
