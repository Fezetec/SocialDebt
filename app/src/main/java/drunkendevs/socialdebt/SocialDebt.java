package drunkendevs.socialdebt;

import android.app.Application;

public class SocialDebt extends Application {
    private static SocialDebt application = null;
    private int totalDebt;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static SocialDebt getInstance() {
        return application;
    }

    public int getTotalDebt() {
        return totalDebt;
    }

    public void addDebt(int debt) {
        totalDebt -= debt;
    }

    public void payOffDebt(int debt) {
        totalDebt += debt;
    }
}
