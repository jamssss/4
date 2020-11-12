package com.example.exp3.service;

import com.example.exp3.model.RegionStates;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvDataService implements InitializingBean {
    private static final String URL="https://gitee.com/dgut-sai/COVID-19/raw/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private final ArrayList<RegionStates> allRegionStates =new ArrayList<>();

    public List<RegionStates> getAllRegionstats(){
        return allRegionStates;
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void fetchCoronaVirusData()throws IOException {
        RequestEntity<Void> requestEntity=RequestEntity
                .get(URI.create(URL))
                .headers(httpHeaders -> httpHeaders.add("User-Agent", "Mozilla/5.0"))
                .build();
        ResponseEntity<Resource> exchange = new RestTemplate().exchange(requestEntity,Resource.class);
        Resource body=exchange.getBody();

        InputStream is=body.getInputStream();
        final Reader reader = new InputStreamReader(is);
        Iterable<CSVRecord> parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
        for (final CSVRecord record : parser) {
            RegionStates regionStates = new RegionStates();
            int today = Integer.parseInt(record.get(record.size() - 1));
            int yesterday = Integer.parseInt(record.get(record.size() - 2));
            regionStates.setState(record.get("Province/State"));
            regionStates.setCountry(record.get("Country/Region"));
            regionStates.setLatestTotalCases(today);
            regionStates.setDiffFromPrevDay(today - yesterday);
            allRegionStates.add(regionStates);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        fetchCoronaVirusData();
    }
}
