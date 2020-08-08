package app.web.interceptors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class FaviconInterceptorTest {

    private FaviconInterceptor interceptorToTest;

    @Mock
    private ModelAndView modelAndView;

    @Test
    public void postHandleShouldAddCorrectAttributes() {
        // Arrange
        this.interceptorToTest = new FaviconInterceptor();

        // Act
        this.interceptorToTest.postHandle(Mockito.mock(HttpServletRequest.class), Mockito.mock(HttpServletResponse.class),
                Mockito.mock(Object.class), modelAndView);
        String favicon = "/images/favicon.png";
        // Assert
        verify(modelAndView, times(1)).addObject("favicon", favicon);

    }
}