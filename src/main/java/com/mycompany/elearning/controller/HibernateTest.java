package git com.mycompany.elearning.controller;

import com.mycompany.elearning.entities.Utilisateurs.Student;
import com.mycompany.elearning.entities.Utilisateurs.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("elearningPU");
            em = emf.createEntityManager();

            System.out.println("✅ Connexion à la base de données réussie!");

            // Test: créer un user
            em.getTransaction().begin();

            User user = new Student("hassane","hassn@email.com", "123","hassna", "ok");
            em.persist(user);

            em.getTransaction().commit();
            System.out.println("✅ User créé avec ID: " + user.getId());

        } catch (Exception e) {
            System.out.println("❌ Erreur: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
            if (emf != null) emf.close();
        }
    }
}