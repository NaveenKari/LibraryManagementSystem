package com.example.librarymanagement.Repositories;

import com.example.librarymanagement.Entity.IssueRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {
}
