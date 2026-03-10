package com.lucky.luckyproject.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ?„ì—­ ?ˆì™¸ ì²˜ë¦¬ê¸?
 *
 * <p>? í”Œë¦¬ì??´ì…˜ ?„ì—­?ì„œ ë°œìƒ?˜ëŠ” ?ˆì™¸ë¥?ê°€ë¡œì±„??ê³µí†µ ?‘ë‹µ ê·œê²©({@link com.lucky.luckyproject.util.ApiResponse})?¼ë¡œ ë³€?˜í•¨.</p>
 *
 * @author 2025?„í˜• AI ê°€?´ë“œ
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ë¹„ì¦ˆ?ˆìŠ¤ ë¡œì§ ?ˆì™¸ ì²˜ë¦¬
     *
     * @param e ë¹„ì¦ˆ?ˆìŠ¤ ?ˆì™¸ ê°ì²´
     * @return 200 OK?€ ?¨ê»˜ ?ëŸ¬ ë©”ì‹œì§€ë¥??´ì? ApiResponse
     */
    @ExceptionHandler(BusinessException.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ë¹„ì¦ˆ?ˆìŠ¤ ë¡œì§ ?ëŸ¬ (?±ê³µ ?‘ë‹µ ???ëŸ¬ ë©”ì‹œì§€)",
            content = @Content(schema = @Schema(implementation = com.lucky.luckyproject.util.ApiResponse.class)))
    })
    public ResponseEntity<com.lucky.luckyproject.util.ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("[Business Error] Code: {}, Message: {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(com.lucky.luckyproject.util.ApiResponse.error(e.getMessage()));
    }

    /**
     * Bean Validation ? íš¨??ê²€???¤íŒ¨ ?ˆì™¸ ì²˜ë¦¬
     *
     * @param e ? íš¨??ê²€???ˆì™¸ ê°ì²´
     * @return 400 Bad Request?€ ì²?ë²ˆì§¸ ê²€???¤íŒ¨ ë©”ì‹œì§€
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "?˜ëª»???”ì²­ ?Œë¼ë¯¸í„°",
            content = @Content(schema = @Schema(implementation = com.lucky.luckyproject.util.ApiResponse.class)))
    })
    public ResponseEntity<com.lucky.luckyproject.util.ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("[Validation Error] {}", msg);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(com.lucky.luckyproject.util.ApiResponse.error(msg));
    }

    /**
     * ?œìŠ¤???°í????ˆì™¸ ë°?ê¸°í? ?ˆì™¸ ì²˜ë¦¬
     *
     * @param e ?œìŠ¤???ˆì™¸ ê°ì²´
     * @return 500 Internal Server Error?€ ?œì? ?ˆë‚´ ë©”ì‹œì§€
     */
    @ExceptionHandler(Exception.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "?œë²„ ?´ë? ?œìŠ¤???¤ë¥˜",
            content = @Content(schema = @Schema(implementation = com.lucky.luckyproject.util.ApiResponse.class)))
    })
    public ResponseEntity<com.lucky.luckyproject.util.ApiResponse<Void>> handleException(Exception e) {
        log.error("[System Error] ", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(com.lucky.luckyproject.util.ApiResponse.error("?œìŠ¤???´ë? ?¤ë¥˜ê°€ ë°œìƒ?ˆìŠµ?ˆë‹¤. ê´€ë¦¬ì?ê²Œ ë¬¸ì˜?˜ì„¸??"));
    }
}
