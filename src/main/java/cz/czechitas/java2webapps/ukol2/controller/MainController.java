package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private final List<String> seznamKoduProObrazky;
    private final List<String> seznamCitatu;
    private final Random random;

    public MainController() throws IOException {
        seznamKoduProObrazky = Arrays.asList("i6Wc5qZO5MQ","N0wr6vRpwCQ","jNYFmC5ZjoI","RFHFV7lVQBY","tzFVsg6nWtM",
                               "bAcR26qMPnY","71vAb1FXB6g","Q1p7bh3SHj8","k2zWqv_yfNM","ejEdpGYGgrA");
        seznamCitatu = readAllLines("citaty.txt");
        random = new Random();
    }

    @GetMapping("/")
    public ModelAndView citaty() {

        ModelAndView result = new ModelAndView("citaty");

        String pozadi =  seznamKoduProObrazky.get(random.nextInt(seznamKoduProObrazky.size()));
        result.addObject("pozadi", String.format("https://source.unsplash.com/%s/1600x900", pozadi));
        result.addObject("citat", seznamCitatu.get(random.nextInt(seznamCitatu.size())));

        return result;
    }

    private static List<String> readAllLines(String resource) throws IOException {
        //Soubory z resources se získávají pomocí classloaderu. Nejprve musíme získat aktuální classloader.
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();

        //Pomocí metody getResourceAsStream() získáme z classloaderu InpuStream, který čte z příslušného souboru.
        //Následně InputStream převedeme na BufferedRead, který čte text v kódování UTF-8
        try (InputStream inputStream=classLoader.getResourceAsStream(resource);
             BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            //Metoda lines() vrací stream řádků ze souboru. Pomocí kolektoru převedeme Stream<String> na List<String>.
            return reader
                    .lines()
                    .collect(Collectors.toList());
        }
    }
}
