package org.rmj.guanzongroup.promotions.Etc;

public interface RaffleEntryCallback {

    void OnSendingEntry();
    void OnSuccessEntry();
    void OnFailedEntry(String message);
}
