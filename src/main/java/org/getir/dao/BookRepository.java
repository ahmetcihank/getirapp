package org.getir.dao;

import org.getir.dao.fragment.BookFragmentDao;
import org.getir.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book, Long> , BookFragmentDao {
    boolean existsByName(String name);
}
