package com.globits.da.validation;

import com.globits.da.dto.ProvinceDto;
import com.globits.da.exception.DuplicateException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.NotNullException;
import com.globits.da.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class ProvinceValidation {
    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceValidation(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    public void checkExist(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new NotNullException(ErrorValidation.PROVINCE_ID_NOTNULL);
        }
        if (!provinceRepository.existsById(id)) {
            throw new NotFoundException(ErrorValidation.PROVINCE_ID_NOT_FOUND);
        }
    }

    public void checkName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NotNullException(ErrorValidation.PROVINCE_NAME_NOTNULL);
        }
        if (provinceRepository.existByName(name)) {
            throw new DuplicateException(ErrorValidation.PROVINCE_NAME_DUPLICATE);
        }
    }

    public void checkProvince(ProvinceDto provinceDto) {
        checkName(provinceDto.getName());
    }
}
