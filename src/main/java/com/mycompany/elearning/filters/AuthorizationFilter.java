package com.mycompany.elearning.filters;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Vérifier les annotations de rôle sur les méthodes
        String role = (String) requestContext.getProperty("role");
        String path = requestContext.getUriInfo().getPath();

        // Logique d'autorisation basée sur les rôles
        if (path.startsWith("admin/") && !"ADMIN".equals(role)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Accès réservé aux administrateurs")
                    .build());
        } else if (path.startsWith("teacher/") && !"TEACHER".equals(role) && !"ADMIN".equals(role)) {
            requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .entity("Accès réservé aux enseignants")
                    .build());
        }
        // Les étudiants peuvent accéder à leurs propres endpoints
    }
}

