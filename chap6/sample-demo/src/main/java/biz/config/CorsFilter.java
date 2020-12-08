package biz.config;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;  
  
/** 
 *  
 *  跨域过滤器 
 * @author meng 
 * @version  
 * @since 2016年6月19日 
 */  
@Component  
@WebFilter(urlPatterns="/", filterName="cors")
public class CorsFilter implements Filter {  
  
    final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CorsFilter.class);  
  
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    	/*HttpServletRequest request = (HttpServletRequest) req;
    	String body = null;  
    	StringBuffer uri = request.getRequestURL();
    	Enumeration<String> d = request.getHeaderNames();
    	String md = request.getMethod();
        StringBuilder stringBuilder = new StringBuilder();  
        BufferedReader bufferedReader = null;  
        try {  
            InputStream inputStream = request.getInputStream();  
            if (inputStream != null) {  
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));  
                char[] charBuffer = new char[128];  
                int bytesRead = -1;  
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {  
                    stringBuilder.append(charBuffer, 0, bytesRead);  
                }  
            } else {  
                stringBuilder.append("");  
            }  
        } catch (IOException ex) {  
            throw ex;  
        } finally {  
            if (bufferedReader != null) {  
                try {  
                    bufferedReader.close();  
                } catch (IOException ex) {  
                    throw ex;  
                }  
            }  
        }  
      
        body = stringBuilder.toString();*/
    	
        HttpServletResponse response = (HttpServletResponse) res;  
//        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3333");
        response.setHeader("Access-Control-Allow-Origin", "*");
        HttpServletRequest request = (HttpServletRequest) req;
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");  
        response.setHeader("Access-Control-Allow-Headers", "*");  
        response.setHeader("Access-Control-Max-Age", "3600");
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        request.getSession().setMaxInactiveInterval(30);
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");  
//        System.out.println("*********************************过滤器被使用**************************");  
//        System.out.println(request.getRequestURI());
        if ("OPTIONS".equals(request.getMethod())){
        	response.setStatus(204);
//            resp.setStatus(HttpStatus.SC_NO_CONTENT);
        	return;
        }
        chain.doFilter(req, res);  
    }  
    public void init(FilterConfig filterConfig) {}  
    public void destroy() {}  
}  