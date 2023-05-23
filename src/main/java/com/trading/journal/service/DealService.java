package com.trading.journal.service;

import com.trading.journal.model.Deal;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;

public interface DealService {

    public Deal saveDeal (Deal deal) throws JSONException, IOException;

    public List<Deal> getAllDeals();

    public Deal getDeal(int id);

    public void deleteDeal(int id);

    public void updateDealNoteById(int id, Deal deal);
}
