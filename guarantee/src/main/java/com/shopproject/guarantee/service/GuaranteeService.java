package com.shopproject.guarantee.service;

import com.shopproject.guarantee.dto.GuaranteeDto;

import java.util.UUID;

public interface GuaranteeService {

    GuaranteeDto getGuarantee (UUID purchaseId);

    GuaranteeDto setGuarantee(UUID id, Integer validInMonth);

    GuaranteeDto stopGuarantee (UUID purchaseId);
}
