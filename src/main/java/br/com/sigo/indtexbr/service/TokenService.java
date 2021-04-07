package br.com.sigo.indtexbr.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.com.sigo.indtexbr.model.dto.CustomUserDetails;
import br.com.sigo.indtexbr.utils.Constante;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {
	private static final String AUTHORITIES_KEY = "AUTHORITIES_KEY";

	@Value("${expiration.time.days}")
	private Long days;

	private static final String SECRET = "#petshopsecret#";
	private static final String TOKEN_PREFIX = "Bearer";

	public String addAuthentication(Authentication auth) {
		List<String> authorities = auth.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());

		Long codigoEmpresa = ((CustomUserDetails) auth.getPrincipal()).getId();
		Map<String, Object> claims = new HashMap<>();
		claims.put(Constante.AUTHORITIES_KEY, authorities);
		claims.put(Constante.USUARIO_KEY, codigoEmpresa);

		Long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(days);

		String JWT = Jwts.builder().setSubject(auth.getName()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).addClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET).compact();

		return TOKEN_PREFIX + " " + JWT;
	}

	public static Optional<Authentication> getAuthentication(String token) throws MalformedJwtException {

		if (token != null && !token.isEmpty()) {

			Claims claims = getBody(token);

			String user = claims.getSubject();

			log.debug(claims.toString());

			List<?> auths = (List<?>) claims.get(AUTHORITIES_KEY);
			List<SimpleGrantedAuthority> authorities = auths.stream().map(x -> new SimpleGrantedAuthority((String) x)).collect(Collectors.toList());

			if (user != null) {
				return Optional.ofNullable(new UsernamePasswordAuthenticationToken(user, null, authorities));
			}
		}
		return Optional.ofNullable(null);
	}

	private static Claims getBody(String token) {
		return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
	}

	public static Long getId(String token) {
		return Long.parseLong(getBody(token).get(Constante.USUARIO_KEY).toString());
	}

}
