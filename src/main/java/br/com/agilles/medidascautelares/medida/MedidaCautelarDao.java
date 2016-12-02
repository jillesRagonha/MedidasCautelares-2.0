package br.com.agilles.medidascautelares.medida;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jille on 17/10/2016.
 */
public class MedidaCautelarDao {

    @Inject
    EntityManager manager;
    public boolean gravarMedida(MedidaCautelar medidaCautelar) {
       boolean gravado = false;
        try {
            manager.getTransaction().begin();
            manager.persist(medidaCautelar);
            manager.getTransaction().commit();
            gravado = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            manager.close();

        }
        return gravado;
    }

    public List<MedidaCautelar> listarTodasMedidas() {
        List<MedidaCautelar> medidas = new ArrayList<>();
        try {
            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<MedidaCautelar> q = builder.createQuery(MedidaCautelar.class);
            Root<MedidaCautelar> p = q.from(MedidaCautelar.class);
            q.select(p);
            TypedQuery<MedidaCautelar> query = manager.createQuery(q);
            medidas = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
        return medidas;

    }
}
