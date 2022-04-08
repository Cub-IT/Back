package com.cub.project.domain.converter;

import com.cub.project.domain.models.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role attribute) {
        return attribute.getRoleId();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData) {
        return Role.getById(dbData);
    }
}
