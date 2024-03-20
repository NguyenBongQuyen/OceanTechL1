package com.globits.da.validation;

import com.globits.da.dto.CommuneDto;
import com.globits.da.exception.DuplicateException;
import com.globits.da.exception.NotFoundException;
import com.globits.da.exception.NotNullException;
import com.globits.da.exception.OctException;
import com.globits.da.repository.CommuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
public class CommuneValidation {
    private final CommuneRepository communeRepository;
    private final DistrictValidation districtValidation;

    @Autowired
    public CommuneValidation(CommuneRepository communeRepository,
                             DistrictValidation districtValidation) {
        this.communeRepository = communeRepository;
        this.districtValidation = districtValidation;
    }

    public void checkExist(UUID id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new NotNullException(ErrorValidation.COMMUNE_ID_NOTNULL);
        }
        if (!communeRepository.existsById(id)) {
            throw new NotFoundException(ErrorValidation.COMMUNE_ID_NOT_FOUND);
        }
    }

    public void checkName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new NotNullException(ErrorValidation.COMMUNE_NAME_NOTNULL);
        }
        if (communeRepository.existByName(name)) {
            throw new DuplicateException((ErrorValidation.COMMUNE_NAME_DUPLICATE));
        }
    }

    public void checkCommuneInDistrict(UUID id, UUID districtId) {
        boolean isExistCommuneInDistrict = communeRepository.existCommuneInDistrict(id, districtId);
        if (!isExistCommuneInDistrict) {
            throw new OctException(ErrorValidation.COMMUNE_NOT_IN_DISTRICT);
        }
    }

    public void checkCommune(CommuneDto communeDto) {
        checkName(communeDto.getName());
        districtValidation.checkExist(communeDto.getDistrictId());
    }
}
