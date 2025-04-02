package my.photomanager.photo;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

public class PhotoReposiorySpecification {

    public static Specification<Photo> findBy(String country, String city, Integer year,
            Month month) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (country != null) {
                predicates.add(criteriaBuilder.equal(root.get("country"), country));
            }

            if (city != null) {
                predicates.add(criteriaBuilder.equal(root.get("city"), city));
            }

            if (year != null) {
                var startDate = LocalDate.of(year, Month.JANUARY, 1);
                var endDate = LocalDate.of(year, Month.DECEMBER, 31);
                predicates
                        .add(criteriaBuilder.between(root.get("creationDate"), startDate, endDate));
            }

            if (month != null) {
                Expression<Integer> creationDateMonth =
                        criteriaBuilder.function("MONTH", Integer.class, root.get("creationDate"));

                predicates.add(criteriaBuilder.equal(creationDateMonth, month));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
