package app.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) {

/*
        String favicon = "https://scontent.fsof9-1.fna.fbcdn.net/v/t1.15752-9/82306255_279387793144802_43966634186869283_n.png?_nc_cat=104&_nc_sid=b96e70&_nc_ohc=_8odK-1Dg-cAX8hLhYL&_nc_ht=scontent.fsof9-1.fna&oh=356920c1478a0596a7ebaa0c9df5837c&oe=5F2B5CEF";
*/
        String favicon = "/images/favicon.png";
        if (modelAndView != null) {
            modelAndView.addObject("favicon", favicon);
        }
    }
}
