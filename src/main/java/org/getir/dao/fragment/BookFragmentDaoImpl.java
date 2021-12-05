package org.getir.dao.fragment;

import org.getir.domain.book.Book;
import org.getir.domain.dto.BookRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

@Service
@Transactional
public class BookFragmentDaoImpl implements BookFragmentDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Lock(LockModeType.OPTIMISTIC)
    public void updateBookStock(BookRequestDto bookRequestDto) {
      String hql = "update Book b set stock=:stockCount where b.name=:name and b.author=:author";

        entityManager.createQuery(hql)
                .setParameter("stockCount",bookRequestDto.getStockCount())
                .setParameter("name",bookRequestDto.getName())
                .setParameter("author",bookRequestDto.getAuthor()).executeUpdate();

    }

}
