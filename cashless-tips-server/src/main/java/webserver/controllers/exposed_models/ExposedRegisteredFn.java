package webserver.controllers.exposed_models;

public class ExposedRegisteredFn {
    final private String inn;
    final private String fn;

    public ExposedRegisteredFn(String inn, String fn) {
        this.inn = inn;
        this.fn = fn;
    }

    public String getInn() {
        return inn;
    }

    public String getFn() {
        return fn;
    }
}
