package my.photomanager.photo;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;


public class PhotoRepositorySpecification {

    public static Specification<Photo> findBy(String country, String city, Integer year,
            Month month, Orientation orientation) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(findByCountry(country).toPredicate(root, query, criteriaBuilder));
            predicates.add(findByCity(city).toPredicate(root, query, criteriaBuilder));
            predicates.add(findByCreationYear(year).toPredicate(root, query, criteriaBuilder));
            predicates.add(findByCreationMonth(month).toPredicate(root, query, criteriaBuilder));
            predicates
                    .add(findByOrientation(orientation).toPredicate(root, query, criteriaBuilder));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Photo> findByCountry(String country) {
        return (root, query, criteriaBuilder) -> {
            if (country == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("country"), country);
        };
    }


    public static Specification<Photo> findByCity(String city) {
        return (root, query, criteriaBuilder) -> {
            if (city == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("city"), city);
        };
    }

    public static Specification<Photo> findByCreationYear(Integer year) {
        return (root, query, criteriaBuilder) -> {
            if (year == null) {
                return criteriaBuilder.conjunction();
            }

            Expression<Integer> creationYear =
                    criteriaBuilder.function("YEAR", Integer.class, root.get("creationDate"));
            return criteriaBuilder.equal(creationYear, year);
        };
    }

    public static Specification<Photo> findByCreationMonth(Month month) {
        return (root, query, criteriaBuilder) -> {
            if (month == null) {
                return criteriaBuilder.conjunction();
            }

            Expression<Integer> creationMonth =
                    criteriaBuilder.function("MONTH", Integer.class, root.get("creationDate"));
            return criteriaBuilder.equal(creationMonth, month.getValue());
        };
    }

    public static Specification<Photo> findByOrientation(Orientation orientation) {
        return (root, query, criteriaBuilder) -> {
            if (orientation == null) {
                return criteriaBuilder.conjunction();
            }

            Expression<Integer> width = root.get("width");
            Expression<Integer> height = root.get("height");

            switch (orientation) {
                case LANDSCAPE:
                    return criteriaBuilder.greaterThan(width, height);

                case PORTRAIT:
                    return criteriaBuilder.greaterThan(height, width);

                case SQUARE:
                    return criteriaBuilder.equal(height, width);

                default:
                    return criteriaBuilder.conjunction();
            }
        };
    }
}
