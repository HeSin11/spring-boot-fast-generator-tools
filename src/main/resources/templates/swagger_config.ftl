package ${instance.packageName};

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /**
    * 创建API应用
    * apiInfo() 增加API相关信息
    * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
    * 本例采用指定扫描的包路径来定义指定要建立API的目录。
    *
    * @return
    */


    //再定义一个Docket
    @Bean
    public Docket desertsApi2() {
    return new Docket(DocumentationType.OAS_30)
            .apiInfo(apiInfo("Swagger3.0", "1.0"))
            .select()
            .apis(RequestHandlerSelectors.basePackage("${instance.scanSwaggerPackage}"))
            .paths(PathSelectors.any())
            .build()
            .groupName("api信息")
            .enable(true);
    }

    /**
    * 创建该API的基本信息（这些基本信息会展现在文档页面中）
    * 访问地址：http://ip:port/swagger-ui.html
    *
    * @return
    */
    private ApiInfo apiInfo(String title, String version) {
    return new ApiInfoBuilder()
            .title(title)
            .description("api信息")
            .contact(new Contact("GMS", "", ""))
            .termsOfServiceUrl("")
            .version(version)
            .build();
    }

    @Bean
    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return new BeanPostProcessor() {
                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
    }

    private<T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
            List<T> copy = mappings.stream()
                .filter(mapping -> mapping.getPatternParser() == null)
                .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
                }

                @SuppressWarnings("unchecked")
                private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                    try {
                        Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                        field.setAccessible(true);
                        return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }
            };
    }
}