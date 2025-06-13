package org.mounanga.userservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.mounanga.userservice.configuration.ApplicationProperties;
import org.mounanga.userservice.repository.RoleMenuRepository;
import org.mounanga.userservice.service.RoleMenuService;
import org.mounanga.userservice.service.implementation.RoleMenuServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private final RoleMenuServiceImpl roleMenuServiceImpl;

	

	private static final String BEARER = "Bearer ";
	private static final String ROLES = "roles";
	private static final String AUTHORIZATION = "Authorization";

	private final ApplicationProperties properties;

	
	public JWTAuthorizationFilter(ApplicationProperties properties, RoleMenuServiceImpl roleMenuServiceImpl) {
		this.properties = properties;
		this.roleMenuServiceImpl = roleMenuServiceImpl;

	}

	@Override
	protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
			@NotNull FilterChain filterChain) throws ServletException, IOException {
		/*
		 * String jwt = request.getHeader(AUTHORIZATION); if(jwt == null ||
		 * !jwt.startsWith(BEARER)) { filterChain.doFilter(request, response); return; }
		 * 
		 * JWTVerifier verifier =
		 * JWT.require(Algorithm.HMAC256(properties.getJwtSecret())).build(); jwt=
		 * jwt.substring(7); DecodedJWT decodedJWT = verifier.verify(jwt); String
		 * username = decodedJWT.getSubject(); Collection<GrantedAuthority> authorities
		 * = new ArrayList<>(); String role =
		 * decodedJWT.getClaims().get(ROLES).toString(); authorities.add(new
		 * SimpleGrantedAuthority(role));
		 * 
		 * //List<String> roles =
		 * decodedJWT.getClaims().get(ROLES).asList(String.class);
		 * 
		 * 
		 * 
		 * for (String role : roles){ authorities.add(new SimpleGrantedAuthority(role));
		 * }
		 * 
		 * 
		 * UsernamePasswordAuthenticationToken user = new
		 * UsernamePasswordAuthenticationToken(username,null,authorities);
		 * SecurityContextHolder.getContext().setAuthentication(user);
		 * filterChain.doFilter(request, response);
		 */

		String jwt = request.getHeader("Authorization");

		if (jwt == null || !jwt.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(properties.getJwtSecret())).build();
			jwt = jwt.substring(7); // Remove "Bearer "
			DecodedJWT decodedJWT = verifier.verify(jwt);

			String username = decodedJWT.getSubject();
			String role = decodedJWT.getClaim("roles").asString(); // adjust this if it's an array

			Collection<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority(role));

			UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null,
					authorities);
			SecurityContextHolder.getContext().setAuthentication(user);

			// 🔐 Dynamic Role-Menu Authorization Check
			//String path = extractBasePath(request.getRequestURI(), 2);
			//String roleName = role.replace("ROLE_", "");
			//Uncomment the below line
			//boolean hasAccess = roleMenuServiceImpl.hasAccess(roleName, path);
			boolean hasAccess=true;
			if (!hasAccess) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().write("Don't have access");
				return;
			}

			filterChain.doFilter(request, response);

		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.getWriter().write("Invalid or expired token");
		}
	}
	
	private String extractBasePath(String fullPath, int level) {
	    String[] parts = fullPath.split("/");
	    StringBuilder basePath = new StringBuilder();
	    for (int i = 1; i <= level && i < parts.length; i++) {
	        basePath.append("/").append(parts[i]);
	    }
	    return basePath.toString();
	}
}
