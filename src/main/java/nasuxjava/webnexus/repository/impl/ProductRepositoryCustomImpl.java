package nasuxjava.webnexus.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import nasuxjava.webnexus.entity.Category;
import nasuxjava.webnexus.entity.Product;
import nasuxjava.webnexus.repository.custom.ProductRepositoryCustom;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findProductsByCriteria(String name, BigDecimal minPrice, BigDecimal maxPrice,
            List<Long> categories, Long distributor, Boolean status) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(product.get("name"), "%" + name + "%"));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(product.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(product.get("price"), maxPrice));
        }
        if (categories != null && !categories.isEmpty()) {
            Join<Product, Category> join = product.join("categories");
            predicates.add(join.get("id").in(categories));
        }
        if (distributor != null) {
            predicates.add(cb.equal(product.get("distributor"), distributor));
        }
        if (status != null) {
            predicates.add(cb.equal(product.get("status"), status));
        }

        query.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(query).getResultList();
    }
}
