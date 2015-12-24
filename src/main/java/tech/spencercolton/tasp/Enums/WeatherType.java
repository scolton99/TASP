package tech.spencercolton.tasp.Enums;

public enum WeatherType {

    STORM("storming"),
    SUN("sunny");

    private final String name;

    WeatherType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
