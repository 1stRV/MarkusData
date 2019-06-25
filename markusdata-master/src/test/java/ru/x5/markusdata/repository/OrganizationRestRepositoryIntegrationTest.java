package ru.x5.markusdata.repository;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import ru.x5.markusdata.entity.Organization;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;


public class OrganizationRestRepositoryIntegrationTest extends AbstractRestRepositoryTest {

    @Autowired
    OrganizationRestRepository organizationRestRepository;

    private static final String SERVICE_URI = "http://localhost:8088/organization";

    @Value("classpath:json/organization.json")
    private Resource organizationInsertRequestResource;

    @Value("classpath:json/organization-change.json")
    private Resource organizationUpdateRequestResource;


    private String organizationInsertRequest;

    private String organizationUpdateRequest;

    @Before
    public void init() throws IOException {
        organizationInsertRequest = IOUtils.toString(organizationInsertRequestResource.getInputStream(), "UTF-8");
        organizationUpdateRequest = IOUtils.toString(organizationUpdateRequestResource.getInputStream(), "UTF-8");
    }

    @Test
    public void organizationRepositoryInsertUpdateTest() throws Exception {
        Organization organizationInsertExpected = objectMapper.readValue(organizationInsertRequest, Organization.class);
        restTemplate.postForObject(new URI(SERVICE_URI), organizationInsertRequest, Object.class);
        Organization organizationInsertActual = null;
        Optional<Organization> optionalOrganization = organizationRestRepository.findById(organizationInsertExpected.getId());
        if (optionalOrganization.isPresent())
            organizationInsertActual = optionalOrganization.get();

        Assert.assertEquals(organizationInsertExpected, organizationInsertActual);

        Organization organizationUpdateExpected = objectMapper.readValue(organizationUpdateRequest, Organization.class);
        restTemplate.postForObject(new URI(SERVICE_URI), organizationUpdateRequest, Object.class);

        Organization organizationUpdateActual = null;
        optionalOrganization = organizationRestRepository.findById(organizationInsertExpected.getId());
        if (optionalOrganization.isPresent())
            organizationUpdateActual = optionalOrganization.get();

        Assert.assertEquals(organizationUpdateExpected, organizationUpdateActual);

    }
}