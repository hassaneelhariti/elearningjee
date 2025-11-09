package com.mycompany.elearning.filters;

import com.mycompany.elearning.utils.JWTUtil;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JWTAuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private JWTUtil jwtUtil;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Exclure les endpoints publics
        String path = requestContext.getUriInfo().getPath();
        if (path.startsWith("auth/login") || path.equals("auth/register")) {
            return;
        }

        // Récupérer le token du header
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token manquant ou invalide")
                    .build());
            return;
        }

        String token = authorizationHeader.substring("Bearer ".length()).trim();

        try {
            // Valider le token
            if (jwtUtil.isTokenExpired(token)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Token expiré")
                        .build());
                return;
            }

            // Ajouter les infos utilisateur au contexte
            String username = jwtUtil.getUsernameFromToken(token);
            String role = jwtUtil.getRoleFromToken(token);
            Long userId = jwtUtil.getUserIdFromToken(token);

            requestContext.setProperty("username", username);
            requestContext.setProperty("role", role);
            requestContext.setProperty("userId", userId);

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Token invalide: " + e.getMessage())
                    .build());
        }
    }
}