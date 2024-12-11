package my.photomanager.model.label;

import jakarta.persistence.*;
import lombok.*;

@Builder(setterPrefix = "with", builderMethodName = "")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "label")
@EqualsAndHashCode(exclude = "ID")
@ToString
@Getter
public class Label implements ILabel {

    public static LabelBuilder builder(String text, LabelCategory category) {
        return new LabelBuilder().withText(text)
                .withCategory(category);
    }

    @Id
    @GeneratedValue
    @Column(updatable = false)
    private long ID;

    @NonNull
    private String text;

    @NonNull
    private LabelCategory category;

    @NonNull
    @Builder.Default
    private String additionalContent = "";
}
