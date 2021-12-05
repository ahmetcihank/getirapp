package org.getir.service.book.serviceimplementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.getir.dao.BookRepository;
import org.getir.domain.book.Book;
import org.getir.domain.dto.BookRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;
import org.getir.service.book.BookService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookPostgreService implements BookService {

    private final BookRepository bookRepository;

    @Override
    public ObjectCreateResponseDto insertBook(BookRequestDto bookRequestDto) {
        Book book = Book.builder()
                        .name(bookRequestDto.getName())
                        .author(bookRequestDto.getAuthor())
                        .stock(bookRequestDto.getStockCount())
                        .price(bookRequestDto.getPrice())
                        .build();

        bookRepository.save(book);
        book = null;

        log.info("book has been inserted:{}", bookRequestDto.getName());

        String messageStr = String.format("book: %s has been created", bookRequestDto.getName());

        return ObjectCreateResponseDto.builder()
                .message(messageStr)
                .timestamp(new Date())
                .build();
    }

    @Override
    public ObjectCreateResponseDto updateBookStock(BookRequestDto bookRequestDto) {
        bookRepository.updateBookStock(bookRequestDto);

        String messageStr = String.format("book stock has been updated");

        return ObjectCreateResponseDto.builder()
                .message(messageStr)
                .timestamp(new Date())
                .build();
    }
}
