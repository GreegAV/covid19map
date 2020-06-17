package pl.bykowski.springbootmapinit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class MapController {


    private Covid19Parser covid19Parser;

    public MapController(Covid19Parser covid19Parser) {
        this.covid19Parser = covid19Parser;
    }

    @GetMapping("/confirmed")
    public String getConfirmed(Model model) throws IOException {
        model.addAttribute("points", covid19Parser.getConfirmedCovidData());
        return "map";

    }

    @GetMapping("/recovered")
    public String getRecovered(Model model) throws IOException {
        model.addAttribute("points", covid19Parser.getRecoveredCovidData());
        return "map";
    }

    @GetMapping("/dead")
    public String getDead(Model model) throws IOException {
        model.addAttribute("points", covid19Parser.getDeadCovidData());
        return "map";
    }
}
