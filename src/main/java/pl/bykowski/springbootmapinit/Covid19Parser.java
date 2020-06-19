package pl.bykowski.springbootmapinit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class Covid19Parser {

    private static final String urlConfirmed = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String urlRecovered = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
    private static final String urlDeaths = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
    private static final String currentDate = "6/18/20";

    public List<Point> getCovidData() throws IOException {
        List<Point> points = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String confirmedValues = restTemplate.getForObject(urlConfirmed, String.class);
        String deadValues = restTemplate.getForObject(urlDeaths, String.class);
        String recoveredValues = restTemplate.getForObject(urlRecovered, String.class);

        StringReader confirmedValuesReader = new StringReader(confirmedValues);
        StringReader deadValuesReader = new StringReader(deadValues);
        StringReader recoveredValuesReader = new StringReader(recoveredValues);
        CSVParser parseConfirmed = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(confirmedValuesReader);
        CSVParser parseDead = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(deadValuesReader);
        CSVParser parseRecovered = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(recoveredValuesReader);

        for (CSVRecord strings : parseConfirmed) {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String text = strings.get(currentDate);
            Point point = new Point(lat, lon);
            point.setText("Confirmed: " + text + "\n\r");
            points.add(point);
        }
        for (CSVRecord strings : parseDead) {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String text = strings.get(currentDate);
            Point newPoint = new Point(lat, lon);
            for (int i = 0; i < points.size(); i++) {
                Point testPoint = points.get(i);
                if (testPoint.equals(newPoint)) {
                    newPoint.setText(testPoint.getText() + "Dead: " + text + "\n\r");
                    points.set(i, newPoint);
                }
            }
        }
        for (CSVRecord strings : parseRecovered) {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String text = strings.get(currentDate);
            Point newPoint = new Point(lat, lon);
            for (int i = 0; i < points.size(); i++) {
                Point testPoint = points.get(i);
                if (testPoint.equals(newPoint)) {
                    newPoint.setText(testPoint.getText() + "Recovered: " + text + "\n\r");
                    points.set(i, newPoint);
                }
            }
        }

        return points;
    }

    public List<Point> getConfirmedCovidData() throws IOException {
        List<Point> points = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String values = restTemplate.getForObject(urlConfirmed, String.class);

        StringReader stringReader = new StringReader(values);
        CSVParser parse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        for (CSVRecord strings : parse) {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String text = strings.get(currentDate);
            Point point = new Point(lat, lon);
            point.setText("Date: " + currentDate + "\n\r" + text);
            points.add(point);
        }
        return points;
    }

    public List<Point> getRecoveredCovidData() throws IOException {
        List<Point> points = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String values = restTemplate.getForObject(urlRecovered, String.class);

        StringReader stringReader = new StringReader(values);
        CSVParser parse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        for (CSVRecord strings : parse) {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String text = strings.get(currentDate);
            Point point = new Point(lat, lon);
            point.setText("Date: " + currentDate + "\n\r" + text);
            points.add(point);
        }
        return points;
    }

    public List<Point> getDeadCovidData() throws IOException {
        List<Point> points = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String values = restTemplate.getForObject(urlDeaths, String.class);

        StringReader stringReader = new StringReader(values);
        CSVParser parse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        for (CSVRecord strings : parse) {
            double lat = Double.parseDouble(strings.get("Lat"));
            double lon = Double.parseDouble(strings.get("Long"));
            String text = strings.get(currentDate);
            Point point = new Point(lat, lon);
            point.setText("Date: " + currentDate + "\n\r" + text);
            points.add(point);
        }
        return points;
    }
}
