package com.example.librarymanagement.Service;

import com.example.librarymanagement.Entity.Book;
import com.example.librarymanagement.DTO.BookDTO;
import com.example.librarymanagement.Repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book Not Found"));
    }

    public Book addBook(BookDTO bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setISBN(bookDto.getISBN());
        book.setIsAvailable(bookDto.getIsAvailable());
        book.setQuantity(bookDto.getQuantity());

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDTO bookDto) {
        Book oldBook = bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Book Not Found"));
        oldBook.setTitle(bookDto.getTitle());
        oldBook.setAuthor(bookDto.getAuthor());
        oldBook.setISBN(bookDto.getISBN());
        oldBook.setIsAvailable(bookDto.getIsAvailable());
        oldBook.setQuantity(bookDto.getQuantity());
        return bookRepository.save(oldBook);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
