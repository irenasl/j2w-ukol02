package cz.czechitas.java2webapps.ukol2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
public class MainController {

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

    private final Random random = new Random();

    @GetMapping("/")
    public ModelAndView citaty() throws IOException {

        String pozadi;
        ModelAndView result = new ModelAndView("citaty");

        List<String> seznamTextu = readAllLines("citaty.txt");
        result.addObject("citat", seznamTextu.get(random.nextInt(8)));

        List<String> seznamKoduProObrazky = Arrays.asList("i6Wc5qZO5MQ","N0wr6vRpwCQ","jNYFmC5ZjoI","RFHFV7lVQBY","tzFVsg6nWtM","bAcR26qMPnY","71vAb1FXB6g","Q1p7bh3SHj8","k2zWqv_yfNM","ejEdpGYGgrA");
        pozadi = "background-image: url(https://source.unsplash.com/" + seznamKoduProObrazky.get(random.nextInt(10)) + "/1600x900";
        result.addObject("pozadi", pozadi);

        return result;
    }
}
