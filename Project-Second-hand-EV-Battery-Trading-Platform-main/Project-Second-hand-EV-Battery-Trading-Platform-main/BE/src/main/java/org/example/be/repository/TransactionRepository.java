package org.example.be.repository;

import org.example.be.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByBuyer_MemberId(Integer buyerId);

    List<Transaction> findByPost_Seller_MemberId(Integer sellerId);

    // Lấy tất cả transaction theo trạng thái, sắp xếp mới nhất lên đầu(Tân)
    @org.springframework.data.jpa.repository.Query("SELECT t FROM Transaction t WHERE t.status = :status ORDER BY t.createdAt DESC")
    List<Transaction> findAllByStatusOrderByCreatedAtDesc(@org.springframework.data.repository.query.Param("status") String status);

    // Lấy tất cả transaction theo nhiều trạng thái (yêu cầu, chấp nhận, đã chuyển tiền cho admin, đã giao, hoàn thành...)(Tân)
    @org.springframework.data.jpa.repository.Query("SELECT t FROM Transaction t WHERE t.status IN :statuses ORDER BY t.createdAt DESC")
    List<Transaction> findAllByStatusInOrderByCreatedAtDesc(@org.springframework.data.repository.query.Param("statuses") List<String> statuses);
}