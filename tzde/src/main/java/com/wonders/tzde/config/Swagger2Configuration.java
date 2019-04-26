package com.wonders.tzde.config;

import com.google.common.base.Predicates;
import com.wonders.tzde.TzdeApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Configuration{

    private String basePackage = TzdeApplication.class.getPackage().getName();

    @Bean
    public Docket createRestApi() {
//        List<Parameter> pars = new ArrayList<>();
//        ParameterBuilder clientPar = new ParameterBuilder();
//        clientPar.name(Constants.CLIENT_ID_KEY).description("客户端ID").modelRef(new ModelRef("string")).parameterType("header").required(false).order(1);
//        pars.add(clientPar.build());
        return new Docket(DocumentationType.SWAGGER_2).groupName("deadShare").select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
//                .apis(Predicates.and(RequestHandlerSelectors.basePackage(basePackage),
//                        RequestHandlerSelectors.withClassAnnotation(OpenRest.class),
//                        Predicates.not(RequestHandlerSelectors.withMethodAnnotation(AuthRest.class))))
                .build()
//                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("死亡共享接口").description("死亡共享接口")
                .termsOfServiceUrl("http://www.wondersgroup.com/")
                .contact(new Contact("郭茂", "", "guomao@wondersgroup.com"))
                .version("1.0").build();
    }

}