package com.br.loja.virtual.loja_virtual_spring.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.ws.rs.ForbiddenException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ControlException extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ExceptinLojaVirtual.class)
    public ResponseEntity<Object> handleExceptionCustm(ExceptinLojaVirtual ex){

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        objetoErroDTO.setErro(ex.getMessage());
        objetoErroDTO.setCodigo(HttpStatus.OK.toString());

       return new ResponseEntity<>(objetoErroDTO, HttpStatus.OK);
    }
    //captura excecao
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        StringBuilder msg = new StringBuilder();
        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            for (ObjectError objectError : list) {
                msg.append(objectError.getDefaultMessage()).append("\n");
            }
        } else if (ex instanceof HttpMessageNotReadableException) {
            msg.append("Não está sendo enviado dados para o body na requisicao");
        } else {
            msg.append(ex.getMessage());
        }

        objetoErroDTO.setErro(msg.toString());
        objetoErroDTO.setCodigo(status.value() + " ==> " + status.getReasonPhrase());

        return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ConstraintViolationException.class,
            DataIntegrityViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handeleExceptionDataIntegry(Exception ex){

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        StringBuilder msg;

        if (ex instanceof DataIntegrityViolationException) {
            msg =  new StringBuilder("Erro de integridade do banco" + ex.getCause().getCause().getMessage());
        } else if (ex instanceof ConstraintViolationException) {
            msg = new StringBuilder("Erro de chave estrangeira do banco" + ex.getCause().getCause().getMessage());
        } else if (ex instanceof SQLException) {
            msg = new StringBuilder("Erro de sql do banco" + ex.getCause().getCause().getMessage());
        }else {
            msg = new StringBuilder(ex.getMessage());
        }
        objetoErroDTO.setErro(msg.toString());
        objetoErroDTO.setCodigo(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleExceptionForbidden(AccessDeniedException ex) {

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        StringBuilder msg = null;

        if (ex != null) {
           msg = new StringBuilder("Usuário sem permissão" + ex.getMessage());

        }

        objetoErroDTO.setErro(msg.toString());
        objetoErroDTO.setCodigo(HttpStatus.FORBIDDEN.toString());

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
