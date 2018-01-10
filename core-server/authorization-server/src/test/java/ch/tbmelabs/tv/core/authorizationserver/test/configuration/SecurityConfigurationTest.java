package ch.tbmelabs.tv.core.authorizationserver.test.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ch.tbmelabs.tv.core.authorizationserver.configuration.OAuth2AuthorizationServerConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.configuration.SecurityConfiguration;
import ch.tbmelabs.tv.core.authorizationserver.test.AbstractOAuth2AuthorizationApplicationContextAware;

public class SecurityConfigurationTest extends AbstractOAuth2AuthorizationApplicationContextAware {
  @Test
  public void securityConfigurationShouldBeAnnotated() {
    assertThat(SecurityConfiguration.class).hasAnnotation(Configuration.class).withFailMessage(
        "Annotate %s with %s to make it scannable for the spring application!", SecurityConfiguration.class,
        Configuration.class);

    assertThat(SecurityConfiguration.class).hasAnnotation(EnableWebSecurity.class).withFailMessage(
        "Annotate %s with %s to affect the %s!", SecurityConfiguration.class, EnableWebSecurity.class,
        WebSecurityConfigurerAdapter.class);

    assertThat(SecurityConfiguration.class).hasAnnotation(EnableGlobalMethodSecurity.class).withFailMessage(
        "Annotate %s with %s to enable global spring method security!", SecurityConfiguration.class,
        EnableGlobalMethodSecurity.class);

    assertThat(SecurityConfiguration.class).hasAnnotation(Order.class).withFailMessage(
        "Annotate %s with %s to get along with %s!", SecurityConfiguration.class, Order.class,
        OAuth2AuthorizationServerConfiguration.class);
  }
}