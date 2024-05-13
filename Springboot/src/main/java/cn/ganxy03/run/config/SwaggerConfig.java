package cn.ganxy03.run.config;

import cn.ganxy03.run.inteceptor.VerifyCodeInterceptor;
import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
@EnableSwaggerBootstrapUI
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new VerifyCodeInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public Docket webApiConfig(){
        ArrayList<Parameter> pars = new ArrayList<>();
        ParameterBuilder tokenPar = new ParameterBuilder();
        tokenPar.name("verifyCode")
                .description("校验码")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ganxy03.run"))
                .build()

                .globalOperationParameters(pars);
//
//
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("晨跑公告系统-API文档")
                .description("互联网信息协会-晨跑公告系统接口文档（2024年5月）")
                .version("1.0")
                .contact(new Contact("Ganxy","http://ganxy03.cn","2192767718@qq.com"))
                .build();
    }
}
