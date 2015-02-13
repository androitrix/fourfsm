package nz.co.econz.fourfsm.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "nz.co.econz.fourfsm.configuration",
        "nz.co.econz.fourfsm.service", 
        "nz.co.econz.fourfsm.web"})
public class RootConfig {

}
