package com.letras_vivas.letras_vidas.bookcrud.repository;

import com.letras_vivas.letras_vidas.bookcrud.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
