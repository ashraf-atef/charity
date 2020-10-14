package org.revo.charity.controller.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ErrorVM processDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cause = (ConstraintViolationException) ex.getCause();
            return new ErrorVM(cause.getCause().getMessage());
        }
        return new ErrorVM("ERROR");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ErrorVM processException(AccessDeniedException ex) {
        return new ErrorVM(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorVM processException(Exception ex) {
        return new ErrorVM(ex.getMessage());
    }
}
