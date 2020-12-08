package com.dw.winter.mapstruct;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author duwen
 * @date 2020/12/8 13:22:58
 */
public class PersonMapperTest {

    @Test
    public void shouldMapPersonToDTO() {
        Person person = new Person();
        person.setName("杜稳");
        person.setAge(23);

        PersonDTO personDTO = PersonMapper.INSTANCE.personToPersonDTO(person);
        assertThat(personDTO.getAge().equals(person.getAge()));
        assertThat(personDTO.getUserName().equals(person.getName()));
    }
}
