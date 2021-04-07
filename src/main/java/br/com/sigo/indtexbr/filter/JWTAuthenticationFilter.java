package br.com.sigo.indtexbr.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.sigo.indtexbr.service.TokenService;
import br.com.sigo.indtexbr.utils.Constante;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

public class JWTAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

		try {
			String token = ((HttpServletRequest) request).getHeader(Constante.AUTHORIZATION_HEADER);

			Optional<Authentication> authentication = TokenService.getAuthentication(token);

			if (authentication.isPresent()) {
				logger.debug(authentication.get().toString());
				SecurityContextHolder.getContext().setAuthentication(authentication.get());
			}

		} catch (MalformedJwtException | ExpiredJwtException e) {
			SecurityContextHolder.clearContext();
			OutputStream out = ((HttpServletResponse) response).getOutputStream();

			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			mapper.writeValue(out, ResponseEntity.status(HttpStatus.FORBIDDEN).build());
			out.flush();

			return;
		} catch (AuthenticationException e) {
			SecurityContextHolder.clearContext();
			OutputStream out = ((HttpServletResponse) response).getOutputStream();

			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
			mapper.writeValue(out, ResponseEntity.status(HttpStatus.FORBIDDEN).build());
			out.flush();

			return;
		}

		filterChain.doFilter(request, response);
	}

}