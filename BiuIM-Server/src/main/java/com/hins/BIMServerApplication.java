package com.hins;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author hins
 */
@SpringBootApplication
public class BIMServerApplication
{
    private final static Logger LOGGER = LoggerFactory.getLogger(BIMServerApplication.class);


    public static void main( String[] args )
    {
        SpringApplication.run(BIMServerApplication.class);
        LOGGER.info("启动 Server 成功");
    }
}
