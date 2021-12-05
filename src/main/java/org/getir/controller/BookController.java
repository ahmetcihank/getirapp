package org.getir.controller;

import org.getir.dao.BookRepository;
import org.getir.domain.dto.BookRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.service.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("book")
@Validated
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(@Qualifier("bookPostgreService") BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/insert")
    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER')")
    public ResponseEntity<ObjectCreateResponseDto> insertBook(@RequestBody @Valid BookRequestDto bookRequestDto) {
        ObjectCreateResponseDto objectCreateResponseDto = bookService.insertBook(bookRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(objectCreateResponseDto);
    }

    @PutMapping("/update-stock")
    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER')")
    public ResponseEntity<ObjectCreateResponseDto> updateStock(@RequestBody @Valid BookRequestDto bookRequestDto) {
        ObjectCreateResponseDto objectCreateResponseDto = bookService.updateBookStock(bookRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(objectCreateResponseDto);
    }
}
