package org.getir.service.book;

import org.getir.domain.dto.BookRequestDto;
import org.getir.domain.dto.ObjectCreateResponseDto;

public interface BookService {

    public ObjectCreateResponseDto insertBook(BookRequestDto bookRequestDto);
    public ObjectCreateResponseDto updateBookStock(BookRequestDto bookRequestDto);

}
