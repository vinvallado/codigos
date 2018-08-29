package br.mil.fab.consigext.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.fab.mil.loginunico.login.filters.AuthenticateFilter;
import br.fab.mil.loginunico.login.servlets.SAML2ConsumerServlet;
import org.springframework.context.annotation.Profile;

@Configuration
public class SAMLAuthFilter {

	@Bean
	@Profile("dev")
	public FilterRegistrationBean authenticateFilterBeanDev() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setName("AuthenticationFilter");
		filter.addUrlPatterns("/*");
		filter.setFilter(new AuthenticateFilter());
		return filter;
	}

	@Bean
	@Profile("dev")
	public ServletRegistrationBean authenticateServletBeanDev() {
		ServletRegistrationBean servlet = new ServletRegistrationBean();
		servlet.addUrlMappings("/consumer");
		servlet.addInitParameter("rootReturn", "/consigext/index/");
		servlet.addInitParameter("urlPermissao", "http://servicos.homolog.ccarj.intraer/AcessoRWS/api/acesso/permissoesBy/");
		servlet.addInitParameter("modulo", "33");
		servlet.addInitParameter("Issuer", "loginunico");
		servlet.addInitParameter("IdpUrl", "https://is.dev.ccarj.intraer:9445/samlsso");
		servlet.addInitParameter("AttributeConsumingServiceIndex", "862451132");
		servlet.addInitParameter("ConsumerUrl", "http://10.52.141.161:8080/consigext/consumer");
		servlet.setServlet(new SAML2ConsumerServlet());

		return servlet;
	}

	@Bean
	@Profile("homol")
	public FilterRegistrationBean authenticateFilterBeanHomol() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setName("AuthenticationFilter");
		filter.addUrlPatterns("/*");
		filter.setFilter(new AuthenticateFilter());
		return filter;
	}

	@Bean
	@Profile("homol")
	public ServletRegistrationBean authenticateServletBeanHomol() {
		ServletRegistrationBean servlet = new ServletRegistrationBean();
		servlet.addUrlMappings("/consumer");
		servlet.addInitParameter("rootReturn", "/consigext/index/");
		servlet.addInitParameter("urlPermissao", "http://wildfly.homolog.ccarj.intraer/AcessoRWS/api/acesso/permissoesBy/");
		servlet.addInitParameter("modulo", "33");
		servlet.addInitParameter("Issuer", "loginunico");
		servlet.addInitParameter("IdpUrl", "https://is.dev.ccarj.intraer:9445/samlsso");
		servlet.addInitParameter("AttributeConsumingServiceIndex", "862451132");
		servlet.addInitParameter("ConsumerUrl", "http://wildfly.homolog.ccarj.intraer/consigext/consumer");

		servlet.setServlet(new SAML2ConsumerServlet());

		return servlet;
	}

	@Bean
	@Profile("prod")
	public FilterRegistrationBean authenticateFilterBeanPrd() {
		FilterRegistrationBean filter = new FilterRegistrationBean();
		filter.setName("AuthenticationFilter");
		filter.addUrlPatterns("/*");
		filter.setFilter(new AuthenticateFilter());
		return filter;
	}

	@Bean
	@Profile("prod")
	public ServletRegistrationBean authenticateServletBeanPrd() {
		ServletRegistrationBean servlet = new ServletRegistrationBean();
		servlet.addUrlMappings("/consumer");
		servlet.addInitParameter("rootReturn", "/consigext/index/");
		servlet.addInitParameter("urlPermissao", "http://servicos.ccarj.intraer/AcessoRWS/api/acesso/permissoesBy/");
		servlet.addInitParameter("modulo", "33");
		servlet.addInitParameter("Issuer", "loginunico");
		servlet.addInitParameter("IdpUrl", "https://is.producao.ccarj.intraer/samlsso");
		servlet.addInitParameter("AttributeConsumingServiceIndex", "2102071755");
		servlet.addInitParameter("ConsumerUrl", "http://servicos.ccarj.intraer/consigext/consumer");

		servlet.setServlet(new SAML2ConsumerServlet());

		return servlet;
	}
}