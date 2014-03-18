package sample.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SampleJettyApplication {
	
	@Value("${keystore.file}") private String keystoreFile;
	@Value("${keystore.pass}") private String keystorePass;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleJettyApplication.class, args);
	}
	
	@Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() throws Exception {
        final String absoluteKeystoreFile = ResourceUtils.getFile(keystoreFile).getAbsolutePath();
        
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer factory) {
                Assert.state(factory instanceof JettyEmbeddedServletContainerFactory, "Use Jetty for this server");
                JettyEmbeddedServletContainerFactory jettyFactory = (JettyEmbeddedServletContainerFactory) factory;
                jettyFactory.addServerCustomizers(new JettyServerCustomizer() {

                    @Override
                    public void customize(Server server) {
                        SslContextFactory sslContextFactory = new SslContextFactory();
                        sslContextFactory.setKeyStorePath(absoluteKeystoreFile);
                        sslContextFactory.setKeyStorePassword(keystorePass);
                        sslContextFactory.setKeyStoreType("PKCS12");

                        ServerConnector sslConnector = new ServerConnector(	server, sslContextFactory);
                        sslConnector.setPort(8443);
                        server.setConnectors(new Connector[] { sslConnector });
                    }
                });
            }
        };
    }

}
