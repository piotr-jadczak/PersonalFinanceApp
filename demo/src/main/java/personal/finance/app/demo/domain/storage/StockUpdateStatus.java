package personal.finance.app.demo.domain.storage;

import lombok.Getter;

public class StockUpdateStatus {

    @Getter
    private boolean isUpdated;

    public StockUpdateStatus() {
        this.isUpdated = false;
    }

    public void startUpdate() {
        this.isUpdated = true;
    }

    public void endUpdate() {
        this.isUpdated = false;
    }

}
