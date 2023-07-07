
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain; // 필터를 요청해 체인
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = "/*")
// 모든걸 처리
public class EncoderFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		// 한글도 번역해줘라
		chain.doFilter(request, response);

	}

}
