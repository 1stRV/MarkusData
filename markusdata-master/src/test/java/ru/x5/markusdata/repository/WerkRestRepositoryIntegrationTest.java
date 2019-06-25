package ru.x5.markusdata.repository;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import ru.x5.markusdata.entity.Werk;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;


public class WerkRestRepositoryIntegrationTest extends AbstractRestRepositoryTest {

    @Autowired
    WerkRestRepository werkRestRepository;

    private static final String SERVICE_URI = "http://localhost:8088/werk";

    @Value("classpath:json/werk.json")
    private Resource werkInsertRequestResource;

    @Value("classpath:json/werk-change.json")
    private Resource werkUpdateRequestResource;


    private String werkInsertRequest;

    private String werkUpdateRequest;

    @Before
    public void init() throws IOException {
        werkInsertRequest = IOUtils.toString(werkInsertRequestResource.getInputStream(), "UTF-8");
        werkUpdateRequest = IOUtils.toString(werkUpdateRequestResource.getInputStream(), "UTF-8");
    }

    @Test
    public void werkRepositoryInsertUpdateTest() throws Exception {
        Werk werkInsertExpected = objectMapper.readValue(werkInsertRequest, Werk.class);
        restTemplate.postForObject(new URI(SERVICE_URI), werkInsertRequest, Object.class);
        Werk werkInsertActual = null;
        Optional<Werk> optionalWerk = werkRestRepository.findById(werkInsertExpected.getId());
        if (optionalWerk.isPresent())
            werkInsertActual = optionalWerk.get();

        Assert.assertEquals(werkInsertExpected, werkInsertActual);

        Werk werkUpdateExpected = objectMapper.readValue(werkUpdateRequest, Werk.class);
        restTemplate.postForObject(new URI(SERVICE_URI), werkUpdateRequest, Object.class);

        Werk werkUpdateActual = null;
        optionalWerk = werkRestRepository.findById(werkInsertExpected.getId());
        if (optionalWerk.isPresent())
            werkUpdateActual = optionalWerk.get();

        Assert.assertEquals(werkUpdateExpected, werkUpdateActual);

    }
}