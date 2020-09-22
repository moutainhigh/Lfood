package com.xdsty.userservice.serializer;

import basecommon.serializer.AbstractProtobufConvertManager;
import com.xdsty.userclient.serializer.UserIntegralMessageProto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author 张富华
 * @date 2020/8/6 17:45
 */
@Configuration
public class ProtobufConvertorManager extends AbstractProtobufConvertManager implements ApplicationRunner {

    @Override
    public void initProtobufMapInfo() {
        putParseClassMap(UserIntegralMessageProto.UserIntegralMessage.class.getSimpleName(), UserIntegralMessageProto.UserIntegralMessage.class.getName());
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initProtobufMapInfo();
    }
}
