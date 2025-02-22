package com.example.librarymanagement.Service;

import com.example.librarymanagement.Entity.Book;
import com.example.librarymanagement.Entity.IssueRecord;
import com.example.librarymanagement.Entity.User;
import com.example.librarymanagement.Repositories.BookRepository;
import com.example.librarymanagement.Repositories.IssueRecordRepository;
import com.example.librarymanagement.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IssueRecordService {

    @Autowired
    private IssueRecordRepository issueRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public IssueRecord issueTheBook(Long bookId){

        Book book = bookRepository.findById(bookId)
                .orElseThrow(()->new RuntimeException("Book not found"));

        if(book.getQuantity()<=0 || !book.getIsAvailable()){
            throw new RuntimeException("Book is not available");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));

        IssueRecord issueRecord = new IssueRecord();
        issueRecord.setIssueDate(LocalDate.now());
        issueRecord.setDueDate(LocalDate.now().plusDays(14));
        issueRecord.setIsReturned(false);
        issueRecord.setUser(user);
        issueRecord.setBook(book);

        book.setQuantity(book.getQuantity()-1);

        if(book.getQuantity()<=0){
            book.setIsAvailable(false);
        }

        bookRepository.save(book);
        return issueRecordRepository.save(issueRecord);
    }

    public IssueRecord returnTheBook(Long issueRecordId){
        IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId)
                .orElseThrow(()->new RuntimeException("IssueRecord not found"));

        if(issueRecord.getIsReturned()){
            throw new RuntimeException("Book is returned");
        }
        else {

            Book book = issueRecord.getBook();

            book.setQuantity(book.getQuantity() + 1);
            book.setIsAvailable(true);

            bookRepository.save(book);

            issueRecord.setReturnDate(LocalDate.now());
            issueRecord.setIsReturned(true);

            return issueRecordRepository.save(issueRecord);
        }

    }


}
