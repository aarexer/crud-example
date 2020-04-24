package aarexer.crud.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class Person {
    private Long id;
    private String name;
    private String phone;
}
