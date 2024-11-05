package nasuxjava.webnexus.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import nasuxjava.webnexus.entity.User;
import nasuxjava.webnexus.repository.UserFilterResposotory;

@Repository
public class UserFilterResposotoryImpl implements UserFilterResposotory {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<User> findFilteredUsers(String fullName, String email, String phone,
            Pageable pageable) {
        StringBuilder queryStr = new StringBuilder("SELECT u FROM users u WHERE 1=1");
        if (fullName != null && !fullName.isEmpty()) {
            queryStr.append(" AND u.fullName LIKE :fullName");
        }
        if (email != null && !email.isEmpty()) {
            queryStr.append(" AND u.email = :email");
        }
        if (phone != null && !phone.isEmpty()) {
            queryStr.append(" AND u.phone = :phone");
        }

        TypedQuery<User> query = entityManager.createQuery(queryStr.toString(),
                User.class);
        if (fullName != null && !fullName.isEmpty()) {
            query.setParameter("fullName", "%" + fullName + "%");
        }
        if (email != null && !email.isEmpty()) {
            query.setParameter("email", email);
        }
        if (phone != null && !phone.isEmpty()) {
            query.setParameter("phone", phone);
        }

        int totalRows = query.getResultList().size();
        List<User> userList = query.setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<>(userList, pageable, totalRows);
    }

}
