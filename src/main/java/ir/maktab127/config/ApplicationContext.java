package ir.maktab127.config;

public class ApplicationContext {
    private static ApplicationContext applicationContext;

    private ApplicationContext() {

    }

    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }
    

}
