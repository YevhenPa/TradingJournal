package com.trading.journal.service;

import com.trading.journal.model.Deal;
import com.trading.journal.repositoty.DealRepository;
import com.trading.journal.security.model.User;
import com.trading.journal.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.trading.journal.util.DealUtils.*;

@Service
@AllArgsConstructor
@CrossOrigin
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    private final UserRepository userRepository;

    @Override
    public List<Deal> getAllDeals() {
        dealCounter();
        return dealRepository.findAllByUserId(currentUser().getId());
    }

    @Override
    public Deal getDeal(int id) {
        Deal deal = null;
        Optional<Deal> optional = dealRepository.findByIdAndUserId(id, currentUser().getId());

        if (optional.isPresent()) {deal = optional.get();}

        dealCounter();

        return deal;
    }

    @Override
    public Deal saveDeal(Deal deal) throws JSONException, IOException {
        profitDollarsCounter(deal);
        marginDollarsCounter(deal);
        profitPercentsCounter(deal);
        dealCounter();

        deal.setUser(currentUser());

        return dealRepository.save(deal);
    }

    @Override
    public void deleteDeal(int id) {
        dealRepository.deleteById(id);
        dealCounter();
    }

    @Override
    public void updateDealNoteById(int id, Deal deal) {
        Optional<Deal> currentDeal = dealRepository.findById(id);
        Deal copyDeal = null;

        if(currentDeal.isPresent()){
            copyDeal = currentDeal.get();
            copyDeal.setId(id);
            copyDeal.setNote(deal.getNote());
            dealRepository.save(copyDeal);
        }
    }

    public void dealCounter() {
        List<Deal> deals = dealRepository.findAllByUserId(currentUser().getId());
        for(int i = 0; i < deals.size(); i++) {
            deals.get(i).setDealNumber(i+1);
        }
    }

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName);
            return user;
        } else {
            return null;
        }
    }

}
