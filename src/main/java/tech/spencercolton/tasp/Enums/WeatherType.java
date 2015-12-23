package tech.spencercolton.tasp.Enums;

public enum WeatherType {

    STORM("storming"),
    SUN("sunny");

    private String name;

    WeatherType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public WeatherType getByName(String name) {
        for(WeatherType w : values()) {
            if(w.getName().equalsIgnoreCase(name))
                return w;
        }
        return null;
    }

}
