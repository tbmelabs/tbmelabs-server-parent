package ch.tbmelabs.tv.core.authorizationserver.test.service.signup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.stereotype.Service;

import ch.tbmelabs.tv.core.authorizationserver.domain.Role;
import ch.tbmelabs.tv.core.authorizationserver.domain.User;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.RoleCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.domain.repository.UserCRUDRepository;
import ch.tbmelabs.tv.core.authorizationserver.service.mail.UserMailService;
import ch.tbmelabs.tv.core.authorizationserver.service.signup.UserSignupService;
import ch.tbmelabs.tv.shared.constants.security.UserAuthority;

public class UserSignupServiceTest {
  private static final String SIGNUP_FAILED_ERROR_MESSAGE = "An error occured. Please check your details!";

  private final MockEnvironment mockEnvironment = new MockEnvironment();

  @Mock
  private ApplicationContext mockApplicationContext;

  @Mock
  private UserCRUDRepository mockUserRepository;

  @Mock
  private RoleCRUDRepository mockRoleRepository;

  @Spy
  @InjectMocks
  private UserSignupService fixture;

  @Before
  public void beforeTestSetUp() {
    initMocks(this);

    doReturn(mockEnvironment).when(mockApplicationContext).getEnvironment();
    doReturn(Mockito.mock(UserMailService.class)).when(mockApplicationContext).getBean(UserMailService.class);

    doAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocation) throws Throwable {
        User newUser = invocation.getArgument(0);
        newUser.setId(new Random().nextLong());
        return newUser;
      }
    }).when(mockUserRepository).save(Mockito.any(User.class));

    doReturn(Optional.of(new Role(UserAuthority.USER))).when(mockRoleRepository).findOneByName(UserAuthority.USER);

    doReturn(true).when(fixture).isUsernameUnique(Mockito.any(User.class));
    doReturn(true).when(fixture).doesUsernameMatchFormat(Mockito.any(User.class));
    doReturn(true).when(fixture).isEmailAddressUnique(Mockito.any(User.class));
    doReturn(true).when(fixture).isEmailAddress(Mockito.any(User.class));
    doReturn(true).when(fixture).doesPasswordMatchFormat(Mockito.any(User.class));
    doReturn(true).when(fixture).doPasswordsMatch(Mockito.any(User.class));
  }

  @Test
  public void userSignupServiceShouldBeAnnotated() {
    assertThat(UserSignupService.class).hasAnnotation(Service.class);
  }

  @Test
  public void userSignupServiceShouldHavePublicConstructor() {
    assertThat(new UserSignupService()).isNotNull();
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnUsernameNotUnique() {
    doReturn(false).when(fixture).isUsernameUnique(Mockito.any(User.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new User())).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnUsernameWrongFormat() {
    doReturn(false).when(fixture).doesUsernameMatchFormat(Mockito.any(User.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new User())).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnEmailNotUnique() {
    doReturn(false).when(fixture).isEmailAddressUnique(Mockito.any(User.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new User())).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnEmailWrongFormat() {
    doReturn(false).when(fixture).isEmailAddress(Mockito.any(User.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new User())).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnPasswordWrongFormat() {
    doReturn(false).when(fixture).doesPasswordMatchFormat(Mockito.any(User.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new User())).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldNotSaveUserOnPasswordsDoNotMatch() {
    doReturn(false).when(fixture).doPasswordsMatch(Mockito.any(User.class));

    assertThatThrownBy(() -> fixture.signUpNewUser(new User())).isInstanceOf(IllegalArgumentException.class)
        .hasMessage(SIGNUP_FAILED_ERROR_MESSAGE);
  }

  @Test
  public void userSignupServiceShouldSaveValidUser() {
    assertThat(fixture.signUpNewUser(new User()).getId()).isNotNull().isInstanceOf(Long.class);
  }
}