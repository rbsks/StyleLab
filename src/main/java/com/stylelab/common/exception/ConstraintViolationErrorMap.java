package com.stylelab.common.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstraintViolationErrorMap {

    private Map<String, ConstraintViolationErrorType> errorHandlerMap;
}
