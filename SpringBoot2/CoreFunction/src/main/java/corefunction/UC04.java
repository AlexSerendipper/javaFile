package corefunction;

/**
 * 【自动配置源码分析】
 *  配置类 @SpringBootApplication 是一个合成注解， 由以下三个注解合成
 * （1）@SpringBootConfiguration：底层就是@configuration，即代表当前类是一个配置类
 * （2）@ComponentScan：包扫描位置
 * （3）@EnableAutoConfiguration：组件注册
 *      @EnableAutoConfiguration ==> @AutoConfigurationPackage ==> Registrar ==>  public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry)
 *       指定默认注册规则： 利用Registrar，为容器批量注册组件，拿到注解的元信息metadata，为 MainApplication所在包及其子包注册组件
 *      @EnableAutoConfiguration ==> @Import({AutoConfigurationImportSelector.class}) ==> getAutoConfigurationEntry(annotationMetadata)
 *        1、利用getAutoConfigurationEntry(annotationMetadata);给容器中批量导入一些组件
 *        2、调用List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes)；获取到所有需要导入到容器中的配置类
 *        3、利用工厂加载 Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader)；得到所有的组件
 *        4、从META-INF/spring.factories位置来加载一个文件。默认扫描我们当前系统里面所有META-INF/spring.factories位置的文件（External Libraries）
 *           ✔✔✔而spring-boot-autoconfigure-2.3.4.RELEASE.jar包 刚好有META-INF/spring.factories，其中写死了那127个固定要导入的场景文件
 *           虽然我们127个 场景 默认全部加载。但实际上会按照条件装配规则（@Conditional）按需配置。
 *           如使用 @ConditionalOnMissingBean。即SpringBoot默认会在底层配好所有的组件。但是如果用户自己配置了以用户的优先
 *
 * 【总结]
 * （1）SpringBoot先加载所有的自动配置类。xxxxxAutoConfiguration
 * （2）每个自动配置类按照条件进行生效，默认都会绑定配置文件指定的值。即xxxProperties和配置文件进行了绑定
 * （3）生效的配置类就会给容器中装配很多组件。只要容器中有这些组件，相当于这些功能就有了
 * （4）定制化配置（以用户配置的组件优先）
 *     方式一（不推荐）：用户直接自己@Bean替换底层的组件
 *     方式二：用户去看这个组件是获取的配置文件什么值就去修改。
 *     ✔✔✔如：我们想要修改 缓存相关的设置。可以到 External Libraries ==> Maven:org.springframework.boot:spring-boot-autoconfigure:2.3.4 ==> spring-boot-autoconfigure-2.3.4.RELEASE.jar 目录下
 *      法1：org.springframework.boot.autoconfigure ==> cache ==> CacheAutoConfiguration ==> @EnableConfigurationProperties({CacheProperties.class}) ==> prefix = "spring.cache"
 *      法2：META-INF/spring.factories ==> CacheAutoConfiguration ==> @EnableConfigurationProperties({CacheProperties.class}) ==> prefix = "spring.cache"
 *     得到前缀后，到application.properties输入后会有相应提示
 *
 * 【springboot自动/手动配置使用流程梗概】
 * （1）引入相关场景依赖  https://docs.spring.io/spring-boot/docs/current/reference/html/using-spring-boot.html#using-boot-starter
 * （2）查看自动配置了哪些（选做）
 *      自己分析， 到 spring-boot-autoconfigure-2.3.4.RELEASE.jar 中逐行分析
 *      到配置文件中 添加 debug=true 开启自动配置报告。Negative（不生效）\Positive（生效）
 * （3）定制化配置
 *      参照文档修改配置项 https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#common-application-properties
 *      自己分析。通过上述总结处方式二得到前缀，然后到核心配置文件中修改
 * （4）自定义加入或者替换组件，见UW03.java
 *     法1：@Bean、@Component...
 *     法2：implements WebMvcConfigurer
 * （5）自定义器：XXXXXCustomizer
 *
 @author Alex
 @create 2023-03-19-10:29
 */
public class UC04 {
}
