package ResponseabilitySegregation;
/*
POJO for weather
 */
public class WeatherPojo {
    public String location;
    public double temperature;
    public long timestamp;
    public int sunhour;

    public WeatherPojo(String location, double temperature, long timestamp, int sunhour) {
        this.location = location;
        this.temperature = temperature;
        this.timestamp = timestamp;
        this.sunhour = sunhour;
    }

    @Override
    public String toString() {
        return "WeatherPojo{" +
                "location='" + location + '\'' +
                ", temperature=" + temperature +
                ", timestamp=" + timestamp +
                ", sunhour=" + sunhour +
                '}';
    }
}
