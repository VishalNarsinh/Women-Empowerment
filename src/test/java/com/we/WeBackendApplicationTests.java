package com.we;

import com.we.service.SubCategoryService;
import com.we.service.VideoService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WeBackendApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(WeBackendApplicationTests.class);
    @Autowired
	VideoService videoService;
    @Autowired
    private SubCategoryService subCategoryService;

    @Test
	void contextLoads() {
    }

}
