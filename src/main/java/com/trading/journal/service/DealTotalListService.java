package com.trading.journal.service;

import com.trading.journal.model.Deal;
import com.trading.journal.model.DealTotalList;
import com.trading.journal.repositoty.DealRepository;
import com.trading.journal.security.model.User;
import com.trading.journal.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealTotalListService {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private UserRepository userRepository;

    public DealTotalList totalDealsMenuCounter(){
//        List<Deal> allDeals = dealRepository.findAll();
        List<Deal> allDeals = dealRepository.findAllByUserId(currentUser().getId());
        DealTotalList totalDeals = new DealTotalList();
        totalDeals.setTotalDeals(allDeals.size());

        for (int i = 0; i < allDeals.size(); i++) {
            Deal currentDeal = allDeals.get(i);

            // Winning
            if(currentDeal.getProfitDollars() > 0) {
                totalDeals.setTotalWinDeals(totalDeals.getTotalWinDeals() + 1);
            } else if (currentDeal.getProfitDollars() < 0) {
                totalDeals.setTotalWasteDeals(totalDeals.getTotalWasteDeals() + 1);
            }

            // Profits
            totalDeals.setTotalProfitDollars(totalDeals.getTotalProfitDollars() + currentDeal.getProfitDollars());
            totalDeals.setTotalProfitPercents(totalDeals.getTotalProfitPercents() + currentDeal.getProfitPercents());

            // Format profits to 2 numbers after point (.00<)
            double formatedProfitDollars = Double.parseDouble(String.format("%.2f", totalDeals.getTotalProfitDollars()));
            totalDeals.setTotalProfitDollars(formatedProfitDollars);
            double formatedProfitPercents = Double.parseDouble(String.format("%.2f", totalDeals.getTotalProfitPercents()));
            totalDeals.setTotalProfitPercents(formatedProfitPercents);

        }

        return totalDeals;

    }

    //TODO: make it static
    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            User user = userRepository.findByEmail(currentUserName);
            return user;
        } else {
            //TODO: make it null
//            return userRepository.findByEmail("jeka@gmail.com");
            return null;
        }
    }
}
