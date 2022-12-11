package com.example.solution.schedule;

import com.example.solution.service.AdmitadHttpClient;
import com.example.solution.service.ProgramService;
import com.example.solution.service.ShopService;
import com.example.solution.service.XMLService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final AdmitadHttpClient httpClient;
    private final ProgramService programService;
    private final ShopService shopService;
    private final XMLService xmlService;


    @Scheduled(cron = "${solution.schedule.program-init-cron}")
    public void initPrograms() {
        var json = httpClient.parsePrograms();
        programService.save(json);
    }

    @Scheduled(cron = "${solution.schedule.offers-init-cron}")
    public void initOffers() {
        var programs = programService.getAllPrograms();
        for (var program : programs) {
            var catalog = xmlService.parseCatalog(program.getProductsXmlLink());
            shopService.save(catalog.getShop());
        }
    }
}
