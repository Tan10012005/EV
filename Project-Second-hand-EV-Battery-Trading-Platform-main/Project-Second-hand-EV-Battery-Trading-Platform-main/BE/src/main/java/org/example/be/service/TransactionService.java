package org.example.be.service;

import org.example.be.entity.Transaction;
import org.example.be.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(Integer id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction updateTransaction(Integer id, Transaction updatedTransaction) {
        Optional<Transaction> existingTransaction = transactionRepository.findById(id);
        if (existingTransaction.isPresent()) {
            Transaction transaction = existingTransaction.get();
            transaction.setBuyer(updatedTransaction.getBuyer());
            transaction.setPost(updatedTransaction.getPost());
            transaction.setStatus(updatedTransaction.getStatus());
            transaction.setCreatedAt(updatedTransaction.getCreatedAt());
            return transactionRepository.save(transaction);
        }
        return null;
    }

    public boolean deleteTransaction(Integer id) {
        Optional<Transaction> existing = transactionRepository.findById(id);
        if (existing.isPresent()) {
            transactionRepository.delete(existing.get());
            return true;
        } else {
            return false;
        }
    }



    public List<Transaction> getAllBuyTransactions(Integer buyerId) {
        return transactionRepository.findByBuyer_MemberId(buyerId);
    }
    public List<Transaction> getAllSellTransactions(Integer sellerId) {
        return transactionRepository.findByPost_Seller_MemberId(sellerId);
    }

    // Lấy tất cả transaction theo trạng thái (yêu cầu giao dịch, chấp nhận, chuyển tiền cho admin, giao, hoàn thành)(Tân)
    public List<Transaction> getAllTransactionsByStatus(String status) {
        return transactionRepository.findAllByStatusOrderByCreatedAtDesc(status);
    }

    // Lấy tất cả transaction theo nhiều trạng thái (truyền danh sách)(Tân)
    public List<Transaction> getAllTransactionsByStatuses(List<String> statuses) {
        return transactionRepository.findAllByStatusInOrderByCreatedAtDesc(statuses);
    }
}