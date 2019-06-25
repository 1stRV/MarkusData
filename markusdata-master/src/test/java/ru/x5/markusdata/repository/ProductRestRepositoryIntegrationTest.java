package ru.x5.markusdata.repository;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import ru.x5.markusdata.entity.Product;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;


public class ProductRestRepositoryIntegrationTest extends AbstractRestRepositoryTest {

    @Autowired
    ProductRestRepository productRestRepository;

    private static final String SERVICE_URI = "http://localhost:8088/product";

    @Value("classpath:json/product.json")
    private Resource productInsertRequestResource;

    @Value("classpath:json/product-change.json")
    private Resource productUpdateRequestResource;


    private String productInsertRequest;

    private String productUpdateRequest;

    @Before
    public void init() throws IOException {
        productInsertRequest = IOUtils.toString(productInsertRequestResource.getInputStream(), "UTF-8");
        productUpdateRequest = IOUtils.toString(productUpdateRequestResource.getInputStream(), "UTF-8");
    }

    @Test
    public void productRepositoryInsertUpdateTest() throws Exception {
        Product productInsertExpected = objectMapper.readValue(productInsertRequest, Product.class);
        restTemplate.postForObject(new URI(SERVICE_URI), productInsertRequest, Object.class);
        Product productInsertActual = null;
        Optional<Product> optionalProduct = productRestRepository.findById(productInsertExpected.getId());
        if (optionalProduct.isPresent())
            productInsertActual = optionalProduct.get();

        Assert.assertEquals(productInsertExpected, productInsertActual);

        Product productUpdateExpected = objectMapper.readValue(productUpdateRequest, Product.class);
        restTemplate.postForObject(new URI(SERVICE_URI), productUpdateRequest, Object.class);

        Product productUpdateActual = null;
        optionalProduct = productRestRepository.findById(productInsertExpected.getId());
        if (optionalProduct.isPresent())
            productUpdateActual = optionalProduct.get();

        Assert.assertEquals(productUpdateExpected, productUpdateActual);

    }
}