package com.hotel.repositories;

import com.hotel.entities.Feedback;
import com.hotel.entities.Hotel;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class HotelSearchManager {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void postConstruct() throws InterruptedException {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        fullTextEntityManager.createIndexer()
                .startAndWait();
    }

    @Transactional
    public List<Hotel> searchHotels(String text) {
        Query query = getQueryBuilder()
                .keyword()
                .onFields("country", "city", "name")
                .matching(text)
                .createQuery();

        return getJpaQuery(query).getResultList();
    }

    private FullTextQuery getJpaQuery(Query query) {
        FullTextEntityManager entityManager = Search.getFullTextEntityManager(this.entityManager);
        return entityManager.createFullTextQuery(query, Hotel.class);
    }

    private QueryBuilder getQueryBuilder() {
        FullTextEntityManager entityManager = Search.getFullTextEntityManager(this.entityManager);

        return entityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Hotel.class)
                .get();
    }
}
