package personal.finance.app.demo.domain.storage;

import lombok.Getter;

public class MarketDataUpdateStatus {

    @Getter
    private boolean isUpdated;

    public MarketDataUpdateStatus() {
        this.isUpdated = false;
    }

    public void startUpdate() {
        this.isUpdated = true;
    }

    public void endUpdate() {
        this.isUpdated = false;
    }

}
