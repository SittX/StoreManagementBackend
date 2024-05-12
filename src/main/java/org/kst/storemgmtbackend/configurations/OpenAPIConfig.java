package org.kst.storemgmtbackend.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

//@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "OpenAPI - Store Management System",
                description = "OpenAPI for Store Management System API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Kyaw Sitt Thway",
                        email = "thwaykyawsitt@yahoo.com",
                        url = "https://github.com/SittX"
                )
        ),
        servers = {
                @Server(
                        description = "DEV",
                        url = "http://localhost:8080"
                )
        }
)
@SecurityScheme(
        name = "JWT Bearer Token for Validation",
        description = "JWT Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfig {

//    @Bean
//    public OpenAPI developmentOpenAPIBean(){
//        Server server = new Server();
//        server.setUrl("http://localhost:8080");
//        server.setDescription("Development");
//
//        Contact contactInfo = new Contact();
//        contactInfo.setName("StoreManagementSystem");
//        contactInfo.setEmail("storemanagement@test.com");
//        contactInfo.setUrl("https://testing.com");
//
//        Info information = new Info()
//                .title("Online Store Management System API")
//                .version("1.0.0")
//                .description("API Specification of the Online Store Management System API")
//                .contact(contactInfo);
//        return new OpenAPI().info(information).servers(List.of(server));
//    }
}
