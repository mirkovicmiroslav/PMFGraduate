package com.pmfgraduate.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:JWTSuperSecretKey}")
	private String jwtSecret;

	@Value("${security.jwt.token.expire-length:360000}")
	private long jwtExpirationInMs;

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	Date dateNow = new Date();
	Date expiryDate = new Date(dateNow.getTime() + jwtExpirationInMs);

	@Autowired
	private UserDetailsService userDetailsService;

	public String getEmailFromToken(String token) {
		String email;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			email = claims.getSubject();
		} catch (Exception e) {
			email = null;
		}
		return email;
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public String createToken(UserDetails userDetails) {
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
		claims.put("roles", userDetails.getAuthorities());

		return Jwts.builder().setClaims(claims).setIssuedAt(dateNow).setExpiration(expiryDate)
				.signWith(SIGNATURE_ALGORITHM, jwtSecret).compact();
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");

		if (bearerToken != null && bearerToken.startsWith("Bearer "))
			return bearerToken.substring(7);

		return null;
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmailFromToken(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return !claims.getBody().getExpiration().before(dateNow);
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

}