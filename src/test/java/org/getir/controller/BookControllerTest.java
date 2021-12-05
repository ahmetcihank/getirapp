package org.getir.controller;

import org.getir.domain.dto.BookRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Test
    void shouldReturnSuccessfulInsertedResponseWhenCorrectObjectGiven() {
        BookRequestDto bookRequestDto = BookRequestDto.builder()
                                                      .stockCount(23)
                                                      .name("çakırcalı efe")
                                                      .author("yaşar kemal")
                                                      .price(new BigDecimal(23))
                                                      .build();

        String messageStr = String.format("book: %s has been created", bookRequestDto.getName());
        ObjectCreateResponseDto objectCreateResponseDto = ObjectCreateResponseDto
                                                                              .builder()
                                                                              .timestamp(new Date())
                                                                              .message(messageStr)
                                                                              .build();

        when(bookService.insertBook(bookRequestDto)).thenReturn(objectCreateResponseDto);
        ResponseEntity<ObjectCreateResponseDto> responseObj = bookController.insertBook(bookRequestDto);
        assertEquals(objectCreateResponseDto, responseObj.getBody());
        assertEquals(HttpStatus.CREATED, responseObj.getStatusCode());
    }

    @Test
    void shouldReturnSuccessfulUpdatedResponseWhenCorrectObjectGiven() {
        BookRequestDto bookRequestDto = BookRequestDto
                .builder()
                .price(new BigDecimal(23))
                .stockCount(23)
                .build();

       String msgStr = String.format("book stock has been updated");

        ObjectCreateResponseDto objectCreateResponseDto = ObjectCreateResponseDto
                .builder()
                .timestamp(new Date())
                .message(msgStr)
                .build();

        when(bookService.updateBookStock(bookRequestDto)).thenReturn(objectCreateResponseDto);
        ResponseEntity<ObjectCreateResponseDto> responseObj = bookController.updateStock(bookRequestDto);
        assertEquals(objectCreateResponseDto, responseObj.getBody());
        assertEquals(HttpStatus.OK, responseObj.getStatusCode());
    }
}