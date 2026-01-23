package com.user.utils;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = Utilities.extractTokenFromHeader(authHeader);

        if (token == null || !Utilities.checkIsJwtTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String uuid = JWTTokenProvider.getUuidFromToken(token);
            String role = JWTTokenProvider.getRoleFromToken(token);

            if (uuid == null || role == null) {
                filterChain.doFilter(request, response);
                return;
            }
            // Normalize role to Spring convention: always "ROLE_XXX"
            String authorityName = "";
            if(role == "USER" || role == "ROLE_USER" || role == "ADMIN" || role == "ROLE_ADMIN"){
                authorityName = role.toUpperCase();
            }else{
                role = role != "USER" && role != "ROLE_USER" && role != "ADMIN" && role != "ROLE_ADMIN" ? "USER" : "ROLE_USER";
                authorityName = role.toUpperCase();
            }
            GrantedAuthority authority = new SimpleGrantedAuthority(authorityName);
            List<GrantedAuthority> authorities = Collections.singletonList(authority);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(uuid, null, authorities);

            // Optional: add request details (IP, session, etc.)
            // authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Optional: expose to controllers via request attributes
            request.setAttribute("uuid", uuid);
            request.setAttribute("role", role);
            request.setAttribute("token",token);

        } catch (Exception e) {
            System.out.println("Cannot set user authentication: { "+ e.getMessage() + " } "+ e);
        }

        filterChain.doFilter(request, response);
    }
    public static void mainOne(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== JWT Secret Key Generator ===");
        System.out.println("1. Generate new secure key");
        System.out.println("2. Encode existing string to Base64");
        System.out.print("Choose option (1 or 2): ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            // Generate a new secure key
            SecretKey key = Jwts.SIG.HS256.key().build();
            String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

            System.out.println("\n✅ Generated Secure JWT Secret Key:");
            System.out.println("======================================");
            System.out.println(base64Key);
            System.out.println("======================================");
            System.out.println("\nCopy this key to your application.properties:");
            System.out.println("jwt.secret=" + base64Key);

        } else if (choice == 2) {
            // Encode existing string
            System.out.print("\nEnter your secret string: ");
            String secretString = scanner.nextLine();

            String base64Key = Base64.getEncoder().encodeToString(secretString.getBytes());

            System.out.println("\n✅ Base64 Encoded Key:");
            System.out.println("======================================");
            System.out.println(base64Key);
            System.out.println("======================================");
            System.out.println("\nCopy this key to your application.properties:");
            System.out.println("jwt.secret=" + base64Key);

            // Check if it's secure enough
            byte[] decoded = Base64.getDecoder().decode(base64Key);
            if (decoded.length < 32) { // 256 bits = 32 bytes
                System.out.println("\n⚠️  WARNING: Your key is less than 256 bits (" +
                        (decoded.length * 8) + " bits). Consider using a longer key!");
            }
        }

        scanner.close();
    }
}
