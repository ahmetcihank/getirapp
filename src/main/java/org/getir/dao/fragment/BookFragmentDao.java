package org.getir.dao.fragment;

import org.getir.domain.dto.BookRequestDto;

public interface BookFragmentDao {

    public void updateBookStock(BookRequestDto bookRequestDto);

}
