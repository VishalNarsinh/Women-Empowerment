package com.lms;

import com.lms.service.SubCategoryService;
import com.lms.service.VideoService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LMSBackendApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(LMSBackendApplicationTests.class);
    @Autowired
	VideoService videoService;
    @Autowired
    private SubCategoryService subCategoryService;

    @Test
	void contextLoads() {
    }

}
