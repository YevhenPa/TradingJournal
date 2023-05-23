package com.trading.journal.repositoty;

import com.trading.journal.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    public List<Image> findAllByDeal_Id(int id);
    public void deleteById(int id);
}
